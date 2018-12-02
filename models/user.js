//models/user.js
var mongoose = require("mongoose");
var bcrypt = require("bcrypt-nodejs");

//Schema
var userSchema = mongoose.Schema({
  username : {
    type:String ,
    require:[true,"name is required!"],
    match :[/^.{2,8}$/,"confirm your insert"],
    trim: true
  },
  id : {
    type:String ,
    require:[true,"아이디를 작성해 주세요"],
    match : [/^.{4,12}$/,"should be 4-12 characters"],
    trim: true,
    unique:true
  },
  pwd : {
    type:String,
    require:[true,"비밀번호 작성해 주세요"],
    select:false},
  email:{type:String}
},{
  toObject:{virtuals:true}
});

//virtuals
userSchema.virtual("passwordConfirmation")
.get(function(){ return this._passwordConfirmation; })
.set(function(value){ this._passwordConfirmation=value; });

userSchema.virtual("originalPassword")
.get(function(){ return this._originalPassword; })
.set(function(value){ this._originalPassword=value; });

userSchema.virtual("currentPassword")
.get(function(){ return this._currentPassword; })
.set(function(value){ this._currentPassword=value; });

userSchema.virtual("newPassword")
.get(function(){ return this._newPassword; })
.set(function(value){ this._newPassword=value; });

userSchema.path("pwd").validate(function(v){
  var user = this;

  // create user // 3-3
  if(user.isNew){ // 3-2
   if(!user.passwordConfirmation){
    user.invalidate("passwordConfirmation", "Password Confirmation is required!");
   }
   if(user.pwd !== user.passwordConfirmation) {
    user.invalidate("passwordConfirmation", "Password Confirmation does not matched!");
   }
  }
/*
--동기식
var hash = bcrypt.hashSync("bacon");

bcrypt.compareSync("bacon", hash); // true
bcrypt.compareSync("veggies", hash); //false

--비동기식
bcrypt.hash("bacon",null,null,function(err,hash){
  //store hash in your password DB
});

//load hash from your password
bcrypt.compare("bacon",hash,function(err,res){
  //res == true
});
bcrypt.compare("veggies",hash,function(err,res){
  //res ==false
})
*/
  // update user // 3-4
   if(!user.isNew){
    if(!user.currentPassword){
     user.invalidate("currentPassword", "Current Password is required!");
   }
    if(user.currentPassword && bcrypt.compareSync(user.currentPassword, user.originalPassword)){
     user.invalidate("currentPassword", "Current Password is invalid!");
    }
    if(user.newPassword !== user.passwordConfirmation) {
     user.invalidate("passwordConfirmation", "Password Confirmation does not matched!");
    }
   }
});

//hash password db에 저장하기 이전에 catch
userSchema.pre("save",function(){
  var user = this;
  if(!user.isModified("pwd")){
    return next();
  }else {
    user.pwd = bcrypt.hashSync(user.pwd);
    return next();
  }
});

// model methods
userSchema.methods.authenicate = function(pwd){
  var user = this;
  return bcrypt.compareSync(pwd, user.pwd);
};


// model & export
var User = mongoose.model("user",userSchema);
module.exports = User;
