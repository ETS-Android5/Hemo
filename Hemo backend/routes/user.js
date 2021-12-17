const router = require('express').Router();
const UserController = require('../controllers/users');
const verify = require('../middleware/verify-token')



//register new user
router.post('/register', UserController.user_register);

//login user
router.post('/login', UserController.user_login);

//verify user email
router.get('/verify/:us', UserController.user_verify);

//finding one user
router.get('/users/:id', verify, UserController.user_find_one)

//getting all user
router.get('/users/', verify, UserController.user_find_all)

//create profile
router.post('/create', verify, UserController.user_create_profile)

//delete profile
router.post('/delete/:id', verify, UserController.user_delete)


module.exports=router