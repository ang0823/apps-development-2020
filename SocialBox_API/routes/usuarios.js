const express = require("express");
const fs = require('fs')
const router = express.Router();
const Usuario = require("../database/models/Usuario")
const { Op } = require('sequelize')
const UsuarioController = require('../controllers/UsuarioController');
const FriendshipsController = require("../controllers/FriendshipsController");
const ImageController = require("../controllers/ImageController");
var imgCount = 1000;

// EndPonit para inciar sesión
router.post('/login', (req, res) => {
    Usuario.findAll({
        attributes: { exclude: ['contrasena', 'createdAt', 'updatedAt'] },
        where: {
            [Op.and]: [
                { username: req.body.username },
                { contrasena: req.body.contrasena }
            ]
        }
    }).then(usuario => {
        if (usuario.length > 0) {
            res.json({
                authenticated: true,
            })
        } else {
            res.json({
                authenticated: false
            });
        }
    }).catch(error => {
        res.status(500).json({
            error
        })
    })
});

// EndPonit para obtener cuentas por nombre
router.post('/nombre', (req, res) => {
    UsuarioController.findByName(req.body.nombre, function (error, usuarios) {
        if (error) {
            res.status(500).json({
                mensaje: "Error en el servidor"
            })
            return;
        }
        let users = usuarios.map(x => {
            return {
                id: x.id,
                nombre: x.nombre,
                apellidos: x.apellidos,
                username: x.username,
                profilePic: x.profilePic.replace("./images/profile/", "http://192.168.1.70:8080/api/users/image/"),
                status: x.status
            }
        })

        res.json({
            usuarios: users
        })
    });
})

// Endpoint que se manda a llamar para recuperar la foto de perfil del usuario
router.get("/image/:name", (req, res) => {
    let image = req.params.name;
    let currentDir = __dirname.split("\\");
    currentDir.pop();
    currentDir = currentDir.join("/")
    let imagePath = currentDir + "/images/profile/" + image;
    res.sendFile(imagePath);
})

router.get("/uploads/:name", (req, res) => {
    let image = req.params.name;
    let currentDir = __dirname.split("\\");
    currentDir.pop();
    currentDir = currentDir.join("/")
    let imagePath = currentDir + "/images/uploads/" + image;
    res.sendFile(imagePath);
})

// EndPonit para obtener una cuenta por nombre de usuario
router.post('/username', (req, res) => {
    UsuarioController.findByUsername(req.body.username, function (error, usuario) {
        if (error) {
            res.status(500).json({
                mensaje: "Error en el servidor"
            })
        } else {
            
            let user = {
                id: usuario.id,
                nombre: usuario.nombre,
                apellidos: usuario.apellidos,
                username: usuario.username,
                profilePic: usuario.profilePic.replace("./images/profile/", "http://192.168.1.70:8080/api/users/image/"),
                status: usuario.status,
                pictures: usuario.pictures
            }
            
            res.json(user)
        }
    });
})

// EndPonit para registrar una cuenta
router.post('/', (req, res) => {
    UsuarioController.create(req.body, function (error, usuario) {
        if (error) {
            res.status(500).json({
                mensaje: "Ocurrió un error al guardar la cuenta."
            })
        } else {
            res.status(200).json(usuario);
        }
    })
})

// Endpoint para editar foto de perfil
router.put('/imagen', (req, res) => {
    fs.writeFile("./images/profile/" + req.body.username + ".jpg", req.body.image, { encoding: 'base64' }, function (error) {
        if (error) {
            res.status(500).json({
                mensaje: error.message
            })
        } else {
            var datos = {
                imageName: req.body.username,
                username: req.body.username
            }
            UsuarioController.updateProfPic(datos, function(error) {
                if(error) {
                    res.status(500).json({
                        mensaje: error
                    });
                } else {
                    res.status(200).json({
                        mensaje: "Foto de perfil actualizada"
                    })
                }
            })
        }
    });

})

// EndPonit para editar el nombre, apellidos o status de una cuenta
router.put('/', (req, res) => {
    UsuarioController.update(req.body, function (error, usuario) {
        if (error) {
            res.status(500).json({
                mensaje: "Error en el servior",
                error
            })
        } else {
            res.status(200).json({ usuario });
        }
    })
})

// EndPoint para agregar amigo
router.post('/agregaramigo', (req, res) => {
    FriendshipsController.addFriend(req.body, function (error, response) {
        if (!error) {
            res.send(response);
        } else {
            res.send(error);
        }
    })
})

// Endpoint para agregar una imagen
router.post('/newpost', (req, res) => {
    var imageName = req.body.username + "-" + imgCount + ".jpg";
    fs.writeFile("./images/uploads/" + imageName, req.body.image, 
    {encoding: "base64"}, function(error) {
        if(error) {
            res.status(500).json({
                imagen: null
            })
        } else {
            var imagen = {
                sender: req.body.username,
                name: imageName,
                description: req.body.description
            }

            ImageController.StoreNewPicture(imagen, function (error, imagen) {
                if(error) {
                    res.status(500).json({
                        imagen: null
                    })
                } else {
                    imgCount = imgCount - 1;
                    res.status(200).json({
                        imagen
                    })
                }
            })
        }
    })
})

module.exports = router;