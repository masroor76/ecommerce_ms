'use strict';

const app = require('./src/app');
const { connectToEureka } = require('./src/eureka/eurekaClient');
const { port } = require('./src/config/server.config');

app.listen(port, () => {
    console.log(`Order service running on port ${port}`);
    connectToEureka();
});