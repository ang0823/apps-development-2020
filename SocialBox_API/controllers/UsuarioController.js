const Usuario = require('../database/models/Usuario')
const {Op} = require('sequelize')

module.exports = {
    find: function (name, callback) {
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
    }
}