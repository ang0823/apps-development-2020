const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Usuario extends Model{}
Usuario.init({
    nombre: {
        type: DataTypes.STRING(100),
        allowNull: false,
        validate: {
            isAlpha: true
        }        
    },
    apellidos: {
        type: DataTypes.STRING(100),
        allowNull: false,
        validate: {
            isAlpha: true
        }
    },
    email: {
        type: DataTypes.STRING(150),
        allowNull: false,
        unique: true,
        validate: {
            isEmail: true
        }
    },
    contrasena: {
        type: DataTypes.STRING(50),
        validate: {
            min: 8
        }
    }
}, {
    sequelize,
    modelName: "usuario"
});

module.exports = Usuario;