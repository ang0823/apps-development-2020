const express = require('express');
const sequelize = require('./database/dbConnection');
const Usuario = require('./database/models/Usuario');
const app = express();
var PORT = 8080;

app.get('/', (req, res) => {
    
});

app.post('api/account', (req, res) => {
    Usuario.create({
        nombre: "Dora Andrea",
        apellidos: "Sanchez Martínez",
        email: "zs12011694@estudiantes.uv.mx",
        contrasena: "123ABC"
    }).then(usuario => {
        res.json("Usuario guardado con éxito");
    });
});

app.listen(PORT, () => {
    sequelize.sync({force: false})
        .then(() => {
            console.log("Base de datos autenticada y conectada");
            console.log(`Servidor iniciado en el puerto ${PORT}`);
        }).catch(error => {
            console.log("No se pudo conectar a la base de datos. Causa: ", error);
        })
});