const mongoose = require("mongoose");

const scheduleSchema = new mongoose.Schema({
  user: {
    type: String,
    required: true,
  },
  status: {
    type: Boolean,
    default: true,
  },
  created: {
    type: Date,
    default: Date.now(),
  },
  date: {
    type: String,
    required: true,
  },
  time: {
    type: String,
    required: true,
  },
  bank: {
    type: String,
    required: true,
  },
  bank_id: {
    type: String,
    required: true,
  }
});

const Schedule = mongoose.model("Schedule", scheduleSchema);
module.exports = Schedule;
