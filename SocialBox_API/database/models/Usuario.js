const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Usuario extends Model{}
Usuario.init({
    nombre: DataTypes.STRING,
    apellidos: DataTypes.STRING,
    email: DataTypes.STRING,
    contrasena: DataTypes.STRING
}, {
    sequelize,
    modelName: "usuario"
});

module.exports = Usuario;