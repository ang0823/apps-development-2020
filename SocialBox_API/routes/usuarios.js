const express = require("express");
const router = express.Router();
const Usuario = require("../database/models/Usuario")
const {Op} = require('sequelize')
const UsuarioController = require('../controllers/UsuarioController')

// EndPonit para inciar sesión
router.post('login', (req, res) => {
    Usuario.findOne({
        attributes: ['contrasena'],
        where: {
            email: req.body.email
        }
    }).then(sentPassword => {
        if(req.body.contrasena == sentPassword) {
            const payload = {
                check: true
            };
            const token  = jwt.sign(payload, app.get('llave'), {
                expiresIn: 1440
            });
            res.json({
                response: 'Authenticated succcessfuly',
                token: token
            });
        } else {
            res.json({
                response: 'Correo y/o contraseña incorrectos.'
            });
        }
    });
});

// EndPonit para obtener cuentas por nombre
router.post('/nombre', (req, res) => {
    UsuarioController.findByName(req.body.nombre, function(error, usuarios) {
        if(error) {
            res.status(500).json({
                mensaje: "Error en el servidor"
            })
            return;
        }
        let users = usuarios.map(x => {
            return {
                nombre: x.nombre,
                apellidos: x.apellidos,
                username: x.username,
                profilePic: x.profilePic.replace("./images/profile/", "http://127.0.0.1:8080/api/users/image/"),
                status: x.status
            }
        })

        res.json(users)
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

// EndPonit para obtener una cuenta por nombre de usuario
router.post('/username', (req, res) => {
    UsuarioController.findByUsername(req.body.username, function(error, usuario) {
        if(error) {
            res.status(500).json({
                mensaje: "Error en el servidor"
            })
        } else {
            let user = {
                nombre: usuario.nombre,
                apellidos: usuario.apellidos,
                username: usuario.username,
                profilePic: usuario.profilePic.replace("./images/profile/", "http://127.0.0.1:8080/api/users/image/"),
                status: usuario.status
            }
            res.json(user)
        }
    });
})

// EndPonit para registrar una cuenta
router.post('/', (req, res) => {
    UsuarioController.create(req.body, function(error, usuario) {
        if(error) {
            res.status(500).json({
                mensaje: "Error en el servidor"
            })
        } else {
            res.json(usuario)
        }
    })
})

// EndPonit para editar el nombre, apellidos, email o status de una cuenta
router.put('/', (req, res) => {
    UsuarioController.update(req.body, function(error, usuario) {
        if(error) {
            res.status(500).json({
                mensaje: "Error en el servior",
                error
            })
        } else {
            res.json(usuario)
        }
    })
})

// Endpoint para editar foto de perfil
router.put('/imagen', (req, res) => {
    if(!req.files.profilePic) {
        res.status(400).json({
            mensaje: "Falta imagen para guardar"
        })
    }

    var data = {
        imageName: req.body.username + "_" +req.files.profilePic.name,
        username: req.body.username
    }
    req.files.profilePic.mv("./images/profile/" + data.imageName)

    UsuarioController.updateProfPic(data, function(error){
        if(!error) {
            res.status(500).json({
                mensaje: "Error en el servidor",
            })
        } else {
            res.json({
                mensaje: "Foto actualizada"
            })
        }
    })
})

// EndPonit para eliminar una cuenta

module.exports = router;