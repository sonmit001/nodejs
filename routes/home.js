//routes/home.js
var express = require("express");
var router = express.Router();

//home
router.get("/", function(req,res){
 res.redirect("/board");
});

module.exports = router;
