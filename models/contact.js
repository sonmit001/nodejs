var mongoose = require("mongoose");

// DB schema
var boardschema_v2 = mongoose.Schema({
  title : {type: String},
  context : {type: String },
  writer : {type: String }
});
var board = mongoose.model("board_v2",boardschema_v2);

module.exports = board;
