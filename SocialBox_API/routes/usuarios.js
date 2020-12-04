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
        console.log(sentPassword);
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
        } else {
            res.json(usuarios)
        }
    });
})

// EndPonit para obtener una cuenta por email
router.post('/correo', (req, res) => {
    UsuarioController.findByEmail(req.body.email, function(error, usuario) {
        if(error) {
            res.status(500).json({
                mensaje: "Error en el servidor"
            })
        } else {
            res.json(usuario)
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

    var datos = {
        imageName: req.files.profilePic.name,
        userEmail: req.body.email
    }
    req.files.profilePic.mv("./images/profile/" + datos.imageName)

    UsuarioController.updateProfPic(datos, function(error){
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