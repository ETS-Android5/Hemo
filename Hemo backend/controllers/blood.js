const mongoose = require('mongoose')
const createError = require('http-errors');
const Blood = require('../model/Blood')
const {bloodValidation, scheduleValidation} = require("../validation");
const Schedule = require('../model/schedule');
const User = require('../model/user')



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

    console.log("schedule called")

    const {valid, error} = scheduleValidation(req.body)
    if(!valid){
        next(createError(400, error))
        return;
    }

    try{

        const{user, bank, date, time} = req.body;

        const muser= await User.findOne({_id:user})

        const schedule = new Schedule(req.body)
        await schedule.save();

        
        res.status(200).send({
          status: 200,
          message: "approval updated"
        })

        //const token = "cJ4l2E5jSOiiQDPxtnx912:APA91bE6CmXSynk5zWrRx_abzTy-bOAstiEL9S59c4Vj4p23ScNj5lm0lJn31RX8TKQCTdHAqRrjXQi-wYtO6wPFfOQLYH9FfcTPGupTR3eaRhgtLM4zKCdc5K9VMNxQlebeY3CzarHI";
        

    }catch(error){
        next(error)
        return
    }

}