const router = require('express').Router();
const UserController = require('../controllers/users');


//register new user
router.post('/register', UserController.user_register);

//login user
router.post('/login', UserController.user_login);

//verify user email
router.get('/verify/:us', UserController.user_verify);


module.exports=router