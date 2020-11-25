const { Sequelize } = require('sequelize');

const database = 'socialbox';
const username = 'apps_dev';
const password = 'PROJECTS_desapp@lis2020'

const connection = new Sequelize(database, username, password, {
    host: 'localhost',
    dialect: 'mysql'
});

module.exports = {
    connection
}