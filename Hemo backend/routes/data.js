const router = require('express').Router();
const { verify } = require('jsonwebtoken');
const Blood = require('../model/Blood');
const User=require('../model/User');

router.get('/user/:id',verify, async (req, res)=>{
    console.log("User req received")
    const {id} = req.params
    const user=await User.findOne({_id: id})
    if(user){
        res.send(user);
    }else{
        res.send("User not found");
    }
})

router.get('/users', async (req, res)=>{
    const users=await User.find()
    res.send(users)
})

router.get('/blood/req', async (req, res)=>{
    console.log("blood request recieved")
    const{city, blood} = req.body;
    const mblood = await Blood.find({city, blood})
    res.send(mblood);
})

router.post('/blood/req', verify, async(req, res)=>{
    const blood = new Blood(req.body);
    await blood.save((err, doc)=>{
        if(err){
            res.send(err);
        }
        res.send(blood)
    })
})

module.exports=router