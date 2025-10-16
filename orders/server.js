'use strict';

const Eureka = require('eureka-js-client').Eureka;

const express = require('express')
const app = express()
const port = 8083

const instanceConfig = {
    app: 'order-node-service',
    hostName: 'localhost',
    ipAddr: '127.0.0.1',
    port: {
        '$': port,
        '@enabled': 'true',
    },
    vipAddress: 'my.node.service.com',
    statusPageUrl: `http://localhost:${port}/api/v1/order`,
    dataCenterInfo: {
        '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
        name: 'MyOwn',
    },
};


const eurekaServerConfig = {
    host: 'localhost',
    port: 8090,
    servicePath: '/eureka/apps/',
};


const client = new Eureka({
    instance: instanceConfig,
    eureka: eurekaServerConfig,
});


function connectToEureka() {
    client.start((error) => {
        if (error) {
            console.error('Eureka registration failed:', error);
        } else {
            console.log('Eureka client registered successfully.');
        }
    });
}
connectToEureka();

process.on('SIGINT', () => {
    console.log('Shutting down Eureka client...');
    client.stop(() => {
        console.log('Eureka client unregistered.');
        process.exit();
    });
});



app.get('/api/v1/order', (req, res) => {
    res.send('Hello World!')
})

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})