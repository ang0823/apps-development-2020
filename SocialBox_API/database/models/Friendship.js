const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Friendship extends Model{}
Friendship.init({
    solicitudVista: {
        type: DataTypes.BOOLEAN,
        defaultValue: false
    },
    solicitudAceptada: {
        type: DataTypes.BOOLEAN,
        defaultValue: false
    }
},
{
    sequelize,
    modelName: 'friendship'
});

module.exports = Friendship;