const mongoose = require('mongoose')
const createError = require('http-errors');
const Blood = require('../model/Blood')
const {bloodValidation, scheduleValidation} = require("../validation");
const Schedule = require('../model/schedule');
const notif = require('../notification/notification') 

exports.blood_all_req = async (req, res, next)=>{
    try{
        location=req.query.location
        blood = req.query.blood
        const mblood = await Blood.find({location, blood})
        res.status(200).send(mblood);
    }catch(error){
        next(error)
        return
    }
}

exports.blood_one_req = async (req, res, next)=>{
    try{
        const {id} = req.params
        const blood = await Blood.findOne({_id: id});
        if(blood){
            res.status(200).send(blood)
        }else{
            throw createError(404, 'Not found')
        }
    }catch(error){
        next(error)
        return;
    }
}

exports.blood_save_req = async (req, res, next)=>{
    const {valid, error} = bloodValidation(req.body)

    if(!valid){
        next(createError(400, error))
        return
    }
    try{
        const blood = new Blood(req.body);
        await blood.save();

        res.status(200).send({
            status: 200,
            message: 'Saved'
        })
    }catch(error){
        next(error)
        return
    }
}

exports.blood_schedule = async (req, res, next)=>{
    const {valid, error} = scheduleValidation(req.body)
    if(!valid){
        next(createError(400, error))
        return;
    }

    try{

        const{name, user, bank, date, time} = req.body;

        const token = "cJ4l2E5jSOiiQDPxtnx912:APA91bE6CmXSynk5zWrRx_abzTy-bOAstiEL9S59c4Vj4p23ScNj5lm0lJn31RX8TKQCTdHAqRrjXQi-wYtO6wPFfOQLYH9FfcTPGupTR3eaRhgtLM4zKCdc5K9VMNxQlebeY3CzarHI";
        const schedule = new Schedule(req.body)
        await schedule.save();

        //notif.send_notificaiton(token, "Appointment Scheduled", "Your blood can save someone's life")

        sendVerificationEmail(name, user, "anantshukla1402@gmail.com", date, time, bank)

        res.status(200).send({
            status: 200,
            message: 'Saved'
        })
    }catch(error){
        next(error)
        return
    }

}

const sendVerificationEmail = ({ name, _id, email, date, time, bank}, res, next) => {
    //url to be used in the email
  
    const uniqueString = uuidv4() + _id;
    const curUrl = "http://localhost:3000/" + "api/user/verify/" + uniqueString;
  
    var mailOptions = {
      from: process.env.AUTH_EMAIL,
      to: email,
      subject: "Email verification Hemo Dev334",
      html: pug.renderFile('F:\Software Projects\Hemo\Hemo backend\views\schedule.pug', { name: name, user_id: _id, date: date, time: time, bank: bank})
    };
    transporter.sendMail(mailOptions, async function (error, response) {
      if (error) {
        next(error);
        return;
      } else {
        res.status = 200;
        res.send({
          status: 200,
          message: "Email sent",
        });
      }
    });
  };