var mysql = require('mysql');

let HOST = '127.0.0.1';
let schema = 'socialbox';
let username = 'apps_dev';
let password = 'PROJECTS_desapp@lis2020';

var connection = mysql.createConnection({
    host: 'localhost',
    database: 'socialbox',
    user: 'apps_dev',
    password: 'PROJECTS_desapp@lis2020',
});

module.exports = connection;