const router = require('express').Router();
const UserController = require('../controllers/users');
const verify = require('../middleware/verify-token')


//register new user
router.post('/register', UserController.user_register);

//login user
router.post('/login', UserController.user_login);

//verify user email
router.post('/verify/:us', UserController.user_verify);

//finding one user
router.get('/users/:id', verify, UserController.user_find_one)

//getting all user
router.get('/users/', verify, UserController.user_find_all)


module.exports=router