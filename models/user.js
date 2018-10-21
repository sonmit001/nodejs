//models/user.js
var mongoose = require("mongoose");

//Schema
var userSchema = mongoose.Schema({
  username : {type:String , require:[true,"이름을 작성해 주세요"]},
  id : {type:String , require:[true,"아이디를 작성해 주세요"], unique:true},
  pwd : {type:String,  require:[true,"비밀번호 작성해 주세요"],select:false},
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

// update user // 3-4
 if(!user.isNew){
  if(!user.currentPassword){
   user.invalidate("currentPassword", "Current Password is required!");
  }
  if(user.currentPassword && user.currentPassword != user.originalPassword){
   user.invalidate("currentPassword", "Current Password is invalid!");
  }
  if(user.newPassword !== user.passwordConfirmation) {
   user.invalidate("passwordConfirmation", "Password Confirmation does not matched!");
  }
 }
});

// model & export
var User = mongoose.model("user",userSchema);
module.exports = User;
