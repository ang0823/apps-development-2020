const express = require('express');
const sequelize = require('./database/dbConnection');
const app = express();
var PORT = 8080;

// Líneas usuadas para llenar el 'req.body'
app.use(express.json());
app.use(express.urlencoded({extended: false}));

app.use('/api/users', require('./routes/usuarios'));



app.listen(PORT, () => {
    sequelize.sync({force: false})
        .then(() => {
            console.log("Base de datos autenticada y conectada");
            console.log(`Servidor iniciado en el puerto ${PORT}`);
        }).catch(error => {
            console.log("No se pudo conectar a la base de datos. Causa: ", error);
        })
});