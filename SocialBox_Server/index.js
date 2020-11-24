const express = require('express');
const app = express();
var PORT = 8080;

app.get('/', (req, res) => {
    res.send('Servidor listo y a la espera de peticiones');
});

app.listen(PORT, () => {
    console.log(`Servidor iniciado en el puerto ${PORT}`)
});