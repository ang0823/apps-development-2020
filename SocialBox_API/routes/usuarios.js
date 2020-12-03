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
    console.log(req.body)
    Usuario.create({
        nombre: req.body.nombre,
        apellidos: req.body.apellidos,
        email: req.body.email,
        contrasena: req.body.contrasena
    }).then(result => {
        console.log.result
        res.json({
            nombre: 'nombre',
            apellidos: 'surname',
            email: result
        })
    }).catch(() => {
        res.json({
            code: 400
        });
    })
})

// EndPonit para editar el nombre, apellidos o email de una cuenta
router.put('/:email', (req, res) => {
    Usuario.update({
        nombre: req.body.nombre,
        apellidos: req.body.apellidos,
        email: req.body.email
    }, {
        where: {
            email: req.params.email
        }
    }).then(() => {
        res.json({
            code: 200
        })
    }).catch(() => {
        res.json({
            code: 400
        })
    })
})

// EndPonit para eliminar una cuenta

module.exports = router;