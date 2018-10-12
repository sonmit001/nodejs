var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var app = express();

// DB settings
mongoose.connect(process.env.MONGO_DB, { useNewUrlParser: true});
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

//Toutes
app.use("/", require("./routes/home"));
app.use("/board", require("./routes/contacts"));

app.listen(3000, function(){
 console.log('Server On!');
});
