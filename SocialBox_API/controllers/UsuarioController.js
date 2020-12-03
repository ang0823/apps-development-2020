const Usuario = require('../database/models/Usuario')
const {Op} = require('sequelize')



module.exports = {
    findByName: function (name, callback) {
        Usuario.findAll({
            attributes: ['nombre', 'apellidos', 'email'],
            where: {
                nombre: {
                    [Op.like]: '%' + name + '%'
                }
            }
        }).then(users => {
            callback(null, users)
        }).catch(error => {
            callback(error)
        })
    },

    findByEmail: function (email, callback) {
        Usuario.findOne({
            attributes: ['nombre', 'apellidos', 'email'],
            where: {
                email: email
            }
        }).then(user => {
            callback(null, user)
        }).catch(error => {
            callback(error)
        });
    }
}