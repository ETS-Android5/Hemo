const router = require('express').Router();
const { verify } = require('jsonwebtoken');
const Blood = require('../model/Blood');
const User=require('../model/User');
const createError =  require('http-errors')

router.get('/user/:id',verify, async (req, res, next)=>{
    console.log("User req received")
    const {id} = req.params
    try{
        const user=await User.findOne({_id: id})
        if(user){
            res.send(user);
        }else{
            throw createError(404, 'User does not exist')
        }
    }catch(error){
        next(error)
    }
})

router.get('/users', async (req, res, next)=>{
    try{
        const users=await User.find()
    }catch(error){
        next(error)
        return
    }
    res.send(users)
})

router.get('/blood/req', verify, async (req, res,next)=>{
    console.log("blood request recieved")
    const{city, blood} = req.body;

    try{
        const mblood = await Blood.find({city, blood})
    }catch(error){
        next(error)
        return
    }
    res.send(mblood);
})

router.post('/blood/req', verify, async(req, re, next)=>{
    try{
        const blood = new Blood(req.body);
    }catch(error){
        next(error)
        return
    }

    await blood.save((err, doc)=>{
        if(err){
            next(err)
            return;
        }
        res.send(blood)
    })
})

module.exports=router