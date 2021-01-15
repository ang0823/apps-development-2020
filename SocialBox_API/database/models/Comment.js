const { Model, DataTypes } = require("sequelize");
const sequelize = require("../dbConnection.js");

class Comment extends Model{}
Comment.init({
    contenido: DataTypes.TEXT
}, {
    sequelize,
    modelName: 'comment'
});

module.exports = Comment;