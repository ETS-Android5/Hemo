const mongoose = require('mongoose')
const createError = require('http-errors');
const Schedule = require('../model/schedule');

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
        res.status(200).send("approval updated")
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
        res.status(200).send("status updated")
    }catch(error){
        next(error)
        return
    }
}