const express=require('express')
const app=express();
require('dotenv').config();
const mongoose=require('mongoose')
const authRoute = require('./routes/auth');

mongoose.connect(process.env.DB_CONNECT,{useNewUrlParser: true}, ()=>{
    console.log('Mongo DB running')
})

app.use(express.json())
app.use('/api/user', authRoute);

app.listen(3000, ()=>{
    console.log('Server up and running')
})