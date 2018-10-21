var express = require("express");
var router = express.Router();
var User = require("../models/user");

//INDEX
router.get("/",function(req,res){
  User.find({})
  .sort({name:1})
  .exec(function(err,users){
    if(err) return res.json(err);
    res.render("user/index",{users:users});
  });
});

//new
router.get("/new",function(req,res){
  res.render("user/new");
});

//created
router.post("/",function(req,res){
  User.create(req.body,function(err, user){
    if(err) return res.json(err);
    res.redirect("/user");
  });
});

// show
router.get("/:id", function(req, res){
 User.findOne({id:req.params.id}, function(err, user){
  if(err) return res.json(err);
  res.render("user/show", {user:user});
 });
});

// edit
router.get("/:id/edit", function(req, res){
 User.findOne({id:req.params.id}, function(err, user){
  if(err) return res.json(err);
  res.render("user/edit", {user:user});
 });
});

// update // 2
router.put("/:id",function(req, res, next){
 User.findOne({id:req.params.id}) // 2-1
 .select("pwd") // 2-2
 .exec(function(err, user){
  if(err) return res.json(err);

  // update user object
  user.originalPassword = user.pwd;
  user.pwd = req.body.newPassword? req.body.newPassword : user.pwd; // 2-3
  for(var p in req.body){ // 2-4
   user[p] = req.body[p];
  }

  // save updated user
  user.save(function(err, user){
   if(err) return res.json(err);
   res.redirect("/user/"+req.params.id);
  });
 });
});

module.exports = router;
