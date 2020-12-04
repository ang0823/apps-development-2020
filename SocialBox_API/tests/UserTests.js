var assert = require('assert');
var request = require('supertest');
const usuarioController = require('../controllers/UsuarioController')

const baseUrl = request("http://localhost:8080")

describe('Obtener usuarios por nombre', function() {
        it('Should retrive all users called Angel', function(done){            
            usuarioController.findByName('Angel', function(error, usuarios) {
                if(error) {
                    done(error)
                } else {
                    console.log(usuarios)
                    done()
                }
            });
        });
});

describe('Obtener un usuario por email', function() {
    it('Should retrieve a single user', function(done) {
        usuarioController.findByEmail('angello93@gmail.com', function(error, usuario) {
            if(error) {
                done(error)
            } else {
                console.log(usuario)
                done()
            }
        });
    });
});