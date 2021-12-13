const mongoose = require('mongoose')

const bloodSchema = new mongoose.Schema({
    city:{
        type: String,
        required: true
    },
    blood: {
        type: String,
        required: true
    },
    phone:{
        type: String,
        required: true
    }
})

const Blood = mongoose.model('Blood', bloodSchema);
module.exports=Blood;