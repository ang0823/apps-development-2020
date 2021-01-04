const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Picture extends Model{}
Picture.init({
    src : DataTypes.STRING,
    descripcion: DataTypes.TEXT,
    fechaSubida: {
        type: DataTypes.DATE,
        allowNull: false,
        defaultValue: Date.now
    }
}, {
    sequelize,
    modelName: "picture"
});

module.exports = Picture;