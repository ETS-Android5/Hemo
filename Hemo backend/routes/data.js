const router = require('express').Router();
const verify = require('../middleware/verify-token')
const Blood = require('../model/blood');
const User=require('../model/user');
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

router.get('/users', verify, async (req, res, next)=>{
    try{
        const users=await User.find()
        res.send(users)
    }catch(error){
        next(error)
        return
    }
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