const express = require('express');
const orderRoutes = require('./routes/orderRoutes');

const app = express();
app.use(orderRoutes);

module.exports = app;