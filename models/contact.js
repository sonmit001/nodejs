var mongoose = require("mongoose");

// DB schema
var ms_board = mongoose.Schema({
  title : {type: String},
  context : {type: String },
  writer : {type: String },
  createdAt : {type: Date, default:Date.now},
  updatedAt : {type: Date},
},{
  toObject : {virtuals:true}//가상 항목 함수
});

ms_board.virtual("createdDate")
.get(function(){
  return getDate(this.createdAt);
});

ms_board.virtual("createdTime")
.get(function(){
  return getTime(this.createdAt);
});

ms_board.virtual("updatedDate")
.get(function(){
  return getDate(this.updatedAt);
});

ms_board.virtual("updatedTime")
.get(function(){
  return getTime(this.updatedAt);
});

var board = mongoose.model("ms_board",ms_board);
module.exports = board;

//function
function getDate(dateObj){
  if(dateObj instanceof Date){
    return dateObj.getFullYear() + '-' +  get2digits(dateObj.getMonth() +1) +'-' + get2digits(dateObj.getDate());
  }
}
function getTime(dateObj){
  if(dateObj instanceof Date){
    return get2digits(dateObj.getHours()) + ":" + get2digits(dateObj.getMinutes());
  }
}

function get2digits(num){
  return ("0" +num).slice(-2);
}
