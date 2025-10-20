const { Eureka } = require('eureka-js-client');
const eurekaConfig = require('../config/eureka.config');

const client = new Eureka({
    instance: eurekaConfig.instance,
    eureka: eurekaConfig.eureka,
});

function connectToEureka() {
    client.start(error => {
        if (error) {
            console.error('Eureka registration failed:', error);
        } else {
            console.log('Eureka client registered successfully.');
        }
    });
}

function disconnectFromEureka() {
    console.log('Shutting down Eureka client...');
    client.stop(() => {
        console.log('Eureka client unregistered.');
        process.exit();
    });
}

process.on('SIGINT', disconnectFromEureka);

module.exports = { client, connectToEureka };