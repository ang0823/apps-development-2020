var assert = require('assert');
var request = require('supertest');
const usuarioController = require('../controllers/UsuarioController')

const baseUrl = request("http://localhost:8080")

describe('Obtener usuarios', function() {
        it('Should retrive all users called Angel', function(done){            
            usuarioController.find('Angel', function(error, usuarios) {
                if(error) {
                    done(error)
                } else {
                    console.log(usuarios)
                    done()
                }
            });
        });
});