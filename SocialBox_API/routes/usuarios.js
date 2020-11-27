const express = require("express");
const router = express.Router();
const Usuario = require("../database/models/Usuario")

// EndPonit para obtener cuentas por nombre

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

// EndPonit para editar una cuenta

// EndPonit para eliminar una cuenta

module.exports = router;