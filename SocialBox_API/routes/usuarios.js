const express = require("express");
const router = express.Router();
const Usuario = require("../database/models/Usuario")
const {Op} = require('sequelize')

// EndPonit para obtener cuentas por nombre
router.post('/nombre', (req, res) => {
    Usuario.findAll({
        attributes: ['nombre', 'apellidos', 'email'],
        where: {
            nombre: {
                [Op.like]: '%' + req.body.nombre + '%'
            }
        }
    }).then(users => {
        if(users.length > 0) {
            res.json(users);
        } else {
            res.json({
                response: 'No se encontraron resultados'
            })
        }
    }).catch(error => {
        console.log(error)
        res.json({
            response: 'Ocurrio un error, intente más tarde.'
        })
    })
})

// EndPonit para obtener una cuenta por email
router.post('/correo', (req, res) => {
    Usuario.findAll({
        attributes: ['nombre', 'apellidos', 'email'],
        where: {
            email: req.body.email
        }
    }).then(user => {
        res.json({
            user
        })
    }).catch(error => {
        console.log(error)
        res.json({
            response: 'Ocurrio un error, intente más tarde.'
        })
    })
})

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