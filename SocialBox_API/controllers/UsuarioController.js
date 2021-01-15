const Usuario = require('../database/models/Usuario')
const { Op, where, DataTypes } = require('sequelize')
const Picture = require('../database/models/Picture')



module.exports = {
    findByName: function (name, callback) {
        Usuario.findAll({
            attributes: ['id', 'nombre', 'apellidos', 'username', 'profilePic', 'status'],
            where: {
                nombre: {
                    [Op.like]: name + '%'
                }
            }
        }).then(users => {
            callback(null, users)
        }).catch(error => {
            callback(error)
        })
    },

    findByUsername: function (nombre_usuario, callback) {
        Usuario.findOne({
            attributes: ['id', 'nombre', 'apellidos', 'username', 'profilePic', 'status'],
            where: {
                username: nombre_usuario
            },
            include: {model: Picture}
        }).then(user => {
            callback(null, user)
        }).catch(error => {
            callback(error)
        });
    },

    create: function (user, callback) {
        var defaultProfilePic = "./images/profile/default.png"
        Usuario.create({
            nombre: user.nombre,
            apellidos: user.apellidos,
            username: user.username,
            contrasena: user.contrasena,
            profilePic: defaultProfilePic
        }).then(user => {
            callback(null, user)
        }).catch(error => {
            callback("Nombre de usuario en uso")
        });
    },

    update: function (user, callback) {
        Usuario.update({
            nombre: user.nombre,
            apellidos: user.apellidos,
            status: user.status
        }, {
            where: {
                username: user.username
            }
        }).then(result => {
            callback(null, result)
        }).catch(error => {
            callback(error)
        });
    },

    updateProfPic: function (datos, callback) {
        Usuario.update({
            profilePic: "./images/profile/" + datos.imageName + ".jpg"
        },
            {
                where: {
                    username: datos.username
                }
            }).then(result => {
                callback(null)
            }).catch(error => {
                callback(error)
            })
    }
}