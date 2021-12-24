const mongoose = require('mongoose')
const createError = require('http-errors');
const Schedule = require('../model/schedule');
const Blood = require('../model/Blood');

exports.get_all_schedule = async (req, res, next)=>{
    try{
        const bank_id = req.query.bank_id
        const pending = !!parseInt(req.query.pending)
        const schedules = await Schedule.find({bank_id, pending})
        res.status(200).send(schedules);
    }catch(error){
        next(error)
        return
    }
}

exports.approve_schedule = async (req, res, next)=>{
    try{
        const id = req.query.id
        const approval = !!parseInt(req.query.approval)
        await Schedule.updateOne({_id: id},{approval, pending: false})
        res.status(200).send({
            status: 200,
            message: "approval updated"
        })
    }catch(error){
        next(error)
        return
    }
}

exports.status_schedule = async (req, res, next)=>{
    try{
        const id = req.query.id
        const status = !!parseInt(req.query.status)
        await Schedule.updateOne({_id: id},{status, close:true})
        res.status(200).send({
            status: 200,
            message: "status updated"
        })
    }catch(error){
        next(error)
        return
    }
}

exports.get_all_requests = async(req, res, next)=>{
    try{
        const location =  req.query.location
        const bloods = await Blood.find({location, verified: false})
        res.status(200).send(bloods);

    }catch(error){
        next(error)
        return;
    }
}

exports.verify_request = async(req, res, next)=>{
    try{
        const _id = req.query.id;
        const verified = !!parseInt(req.query.id)
        if(!verified){
            await Blood.updateOne({_id}, {verified, status: false})
        }else{
            await Blood.updateOne({_id}, {verified})
        }
        res.status(200).send({
            status: 200,
            message: "verify updated"
        })
    }catch(error){
        next(error)
        return
    }
}