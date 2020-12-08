const Usuario = require('../database/models/Usuario')
const {Op, where} = require('sequelize')



module.exports = {
    findByName: function (name, callback) {
        Usuario.findAll({
            attributes: ['nombre', 'apellidos', 'username', 'profilePic', 'status'],
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

    findByUsername: function (nombre_usuario, callback) {
        Usuario.findOne({
            attributes: ['nombre', 'apellidos', 'username', 'profilePic', 'status'],
            where: {
                username: nombre_usuario
            }
        }).then(user => {
            callback(null, user)
        }).catch(error => {
            callback(error)
        });
    },

    create: function(user, callback) {
        var defaultProfilePic = "./images/profile/default.png"
        Usuario.create({
            nombre: user.nombre,
            apellidos: user.apellidos,
            username: user.username,
            contrasena: user.contrasena,
            profilePic: defaultProfilePic
        }).then(user => {
            callback(null, `${user.nombre} ${user.apellidos} ${user.username}`)
        }).catch(error => {
            callback(error)
        });
    },

    update: function(user, callback) {
            Usuario.update({           
                nombre: user.nombre,
                apellidos: user.apellidos,
                status: user.status}, {
                    where: {
                        email: user.email
                    }
                }).then(result => {
                callback(null, result)
            }).catch(error => {
                callback(error)
            });
    },

    updateProfPic: function(datos, callback) {
        Usuario.update({
            profilePic: "./images/profile/" + datos.imageName}, {
                where: {
                    email: datos.userEmail
                }
            }).then(result => {
                callback(result)
            }).catch(error => {
                callback(error)
            })
    }
}