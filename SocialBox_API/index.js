const express = require('express');
const sequelize = require('./database/dbConnection');
require('./database/asociaciones')
const app = express();
const fileUpload = require('express-fileupload');
var PORT = 8080;

// Líneas usuadas para llenar el 'req.body'
app.use(express.json({limit: '50mb'}));
app.use(express.urlencoded({limit: '50mb', extended: true}));
app.use(fileUpload({
    createParentPath: true
}))

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