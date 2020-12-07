const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Usuario extends Model{}
Usuario.init({
    nombre: {
        type: DataTypes.STRING(100),
        allowNull: false    
    },
    apellidos: {
        type: DataTypes.STRING(100),
        allowNull: false
    },
    username: {
        type: DataTypes.STRING(25),
        allowNull: false,
        unique: true
    },
    contrasena: DataTypes.STRING(50),
    profilePic: DataTypes.STRING,
    status: DataTypes.STRING
}, {
    sequelize,
    modelName: "usuario"
});

module.exports = Usuario;