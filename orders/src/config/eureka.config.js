const { port } = require('./server.config');

module.exports = {
    instance: {
        app: 'order-service',
        hostName: 'hpvictus-15',
        ipAddr: '127.0.0.1',
        port: {
            '$': port,
            '@enabled': 'true',
        },
        vipAddress: 'ORDER-SERVICE',
        statusPageUrl: `http://localhost:${port}/actuator/health`,
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
    },
    eureka: {
        host: 'hpvictus-15',
        port: 4646,
        servicePath: '/eureka/apps/',
    },
};