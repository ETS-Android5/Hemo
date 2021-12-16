const router = require('express').Router();
const verify = require('../middleware/verify-token')

const BloodController = require('../controllers/blood')

router.get('/req', verify, BloodController.blood_all_req)

router.post('/req', verify, BloodController.blood_save_req)

router.get('/req/:id', verify, BloodController.blood_one_req)

module.exports=router