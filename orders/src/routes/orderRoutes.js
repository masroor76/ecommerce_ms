const express = require('express');
const router = express.Router();
const orderController = require('../controllers/orderController');

router.get('/api/v1/order', orderController.getOrder);

module.exports = router;