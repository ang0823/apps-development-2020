const Picture = require('../database/models/Picture');
const Usuario = require('../database/models/Usuario');
const UsuarioController = require('./UsuarioController');

module.exports = {
    StoreNewPicture: function (image, callback) {
        UsuarioController.findByUsername(image.sender, function (error, user) {
            if(!error){
                Picture.create({
                    src: "./images/uploads/" + image.name + ".jpg",
                    descripcion: image.description,
                    usuarioId: user.id
                }).then(image => {
                    callback(null, image);
                }).catch(error => {
                    callback(error);
                })
            } else {
                console.log(error);
            }
        })
    }
}