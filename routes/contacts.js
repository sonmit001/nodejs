//routes/contacts.js
var express = require("express");
var router = express.Router();
var board = require("../models/Contact");

// Index app
router.get("/",function(req,res){
  board.find({})
  .sort("-createdAt")
  .exec(function(err,boards){
    if(err) return res.json(err);
    res.render("board/index",{boards: boards});
  });
});

//글 생성 페이지
router.get("/new",function(req,res){
  res.render("board/new");
});
//
//글 저장하기
router.post("/",function(req,res){
      board.create(req.body,function(err, data){
        if(err) return res.json(err);
        res.redirect('/board');
      });
});

//글 상세 페이지
router.get("/:id",function(req,res){
  board.findOne({_id:req.params.id},function(err,board){
    if(err) return res.json(err);
    res.render("board/detail",{board:board});
  });
});

//수정하기 화면 가기
router.get("/edit/:id",function(req,res){
  board.findOne({_id:req.params.id},function(err,board){
    if(err) return res.json(err);
    res.render("board/edit",{board:board});
  });
});
//수정 완료
router.put("/edit/:id",function(req,res){
  board.findOneAndUpdate({_id: req.params.id}, req.body,function(err,board){
    if(err) return res.json(err);
    res.redirect("/board/"+req.params.id);
  });
});
//삭제하기
router.delete("/:id",function(req,res){
  board.remove({_id: req.params.id},function(err,board){
    if(err) return res.json(err);
    res.redirect("/board");
  });
});

module.exports = router;
