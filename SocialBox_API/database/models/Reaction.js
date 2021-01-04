const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Reaction extends Model {}
Reaction.init({

}, {
    sequelize,
    modelName: 'reaction'
});

module.exports = Reaction;