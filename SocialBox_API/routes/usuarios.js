const express = require("express");
const router = express.Router();
const Usuario = require("../database/models/Usuario")

// EndPonit para obtener cuentas por nombre
router.get('/:nombre', (req, res) => {
    Usuario.findAll({
        attributes: ['nombre', 'apellidos', 'email'],
        where: {
            nombre: req.params.nombre
        },
        raw: false
    }).then(users => {
        res.json({
            usuarios: users
        })
    })
})

// EndPonit para obtener una cuenta por email

// EndPonit para registrar una cuenta
router.post('/', (req, res) => {
    Usuario.create({
        nombre: req.body.nombre,
        apellidos: req.body.apellidos,
        email: req.body.email,
        contrasena: req.body.contrasena
    }).then(() => {
        res.json({
            code: 201
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