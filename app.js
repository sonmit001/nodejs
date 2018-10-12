var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var app = express();

// DB settings
mongoose.connect(process.env.MONGO_DB);
var db = mongoose.connection;

db.once("open",function(){
  console.log("DB connected");
});

db.on("error",function(){
  console.log("DB error" ,err);
});

// ejs
app.set("view engine","ejs");
//static 위치
app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));
app.use(methodOverride("_method"));

// DB schema
var boardschema_v2 = mongoose.Schema({
  title : {type: String},
  context : {type: String },
  writer : {type: String }
});
var board = mongoose.model("board_v2",boardschema_v2);

// ejs
app.get("/", function(req,res){
 res.redirect("/board");
});

app.get("/board",function(req,res){
  board.find({},function(err,boards){
    if(err) return res.json(err);
    res.render("board/index",{boards: boards});
  });
});

//글 생성 페이지
app.get("/board/new",function(req,res){
  res.render("board/new");
});
//
//글 저장하기
app.post("/board",function(req,res){
      board.create(req.body,function(err, data){
        if(err) return res.json(err);
        res.redirect('/board');
      });
});

//글 상세 페이지
app.get("/board/:id",function(req,res){
  board.findOne({_id:req.params.id},function(err,board){
    if(err) return res.json(err);
    res.render("board/detail",{board:board});
  });
});

//수정하기 화면 가기
app.get("/board/edit/:id",function(req,res){
  board.findOne({_id:req.params.id},function(err,board){
    if(err) return res.json(err);
    res.render("board/edit",{board:board});
  });
});
//수정 완료
app.put("/board/edit/:id",function(req,res){
  board.findOneAndUpdate({_id: req.params.id}, req.body,function(err,board){
    if(err) return res.json(err);
    res.redirect("/board/"+req.params.id);
  });
});
//삭제하기
app.delete("/board/:id",function(req,res){
  board.remove({_id: req.params.id},function(err,board){
    if(err) return res.json(err);
    res.redirect("/board");
  });
});


app.listen(3000, function(){
 console.log('Server On!');
});
