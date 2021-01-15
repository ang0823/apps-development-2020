const Usuario = require('../database/models/Usuario')
const {Op, where, DataTypes} = require('sequelize')
const Friendship = require('../database/models/Friendship')
const UsuarioController = require('./UsuarioController')

module.exports = {
    addFriend: function(users, callback) {
        UsuarioController.findByUsername(users.sender, function(error, user){
            if(!error) {
                Friendship.create({
                    senderId: user.id,
                    receptorId: users.receptorId
                }).then(res => {
                    console.log("Solicitud de amistad registrada");
                    callback(null, "Solicitud de amistad registrada")
                }).catch(error => {
                    console.log("Error al registrar amistad:\n".concat(error));
                })
            } else {
                consol.log(error)
            }
        });
        
    }
}