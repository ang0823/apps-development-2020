const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Picture extends Model{}
Picture.init({
    src : {
        type: DataTypes.STRING,
        allowNull: false
    },
    descripcion: DataTypes.TEXT
}, {
    sequelize,
    modelName: "picture"
});

module.exports = Picture;