const Usuario = require('../database/models/Usuario')
const {Op, where} = require('sequelize')



module.exports = {
    findByName: function (name, callback) {
        Usuario.findAll({
            attributes: ['nombre', 'apellidos', 'email', 'profilePic', 'status'],
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
            attributes: ['nombre', 'apellidos', 'email', 'profilePc', 'status'],
            where: {
                email: email
            }
        }).then(user => {
            callback(null, user)
        }).catch(error => {
            callback(error)
        });
    },

    create: function(user, callback) {
        Usuario.create({
            nombre: user.nombre,
            apellidos: user.apellidos,
            email: user.email,
            contrasena: user.contrasena
        }).then(user => {
            callback(null, `${user.nombre} ${user.apellidos} ${user.email}`)
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