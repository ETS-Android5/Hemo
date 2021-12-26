const mongoose = require('mongoose')
const createError = require('http-errors');
const Blood = require('../model/Blood')
const {bloodValidation, scheduleValidation} = require("../validation");
const Schedule = require('../model/schedule');
const User = require('../model/user')



exports.blood_all_req = async (req, res, next)=>{
    try{
        var location=req.query.location
        var blood = req.query.blood

        if(blood==="A+"){
            const mblood = await Blood.find({location, blood: {$in: ['A+','AB+']}})
            res.status(200).send(mblood);
        }else if (blood==="A-"){
            const mblood = await Blood.find({location, blood: {$in: ['A-','A+','AB+','AB-']}})
            res.status(200).send(mblood);
        }else if(blood==="B+"){
            const mblood = await Blood.find({location, blood: {$in: ['B+','AB+']}})
            res.status(200).send(mblood);
        }else if(blood==="B-"){
            const mblood = await Blood.find({location, blood: {$in: ['B-','B+','AB+', 'AB-']}})
            res.status(200).send(mblood);
        }else if(blood==="O+"){
            const mblood = await Blood.find({location, blood: {$in: ['O+','A+','AB+']}})
            res.status(200).send(mblood);
        }else if(blood==="O-"){
            const mblood = await Blood.find({location})
            res.status(200).send(mblood);
        }else if(blood==="AB+"){
            const mblood = await Blood.find({location, blood: {$in: ['AB+']}})
            res.status(200).send(mblood);
        }else if(blood==="AB-"){
            const mblood = await Blood.find({location, blood: {$in: ['AB+','AB-']}})
            res.status(200).send(mblood);
        }else{
            next(createError(400, "Undefined blood type"))
        }
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
        const user_id = req.body.user;

        const user = await User.findOne({_id: user_id})

        if(!user){
            next(createError(404, "User not found"))
            return;
        }

        const blood = new Blood(req.body);
        blood.name = user.name;
        blood.phone=user.phone;
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

        const user_id = req.body.user;

        const muser = await User.findOne({_id: user_id})

        if(!muser){
            next(createError(404, "User not found"))
            return;
        }

        const schedule = new Schedule(req.body)
        schedule.name=muser.name;
        schedule.phone=muser.phone;
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