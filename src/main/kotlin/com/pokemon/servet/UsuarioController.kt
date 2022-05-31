package com.pokemon.servet

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsuarioController (private  val usuarioRepository: UsuarioRepository) {

    @PostMapping("registro")
    @Synchronized
    fun requestRegistroUsuario(@RequestBody usuario: Usuario): Any {

        //COMPROBAR SI EL USUARIO EXISTE
        val userOpcional = usuarioRepository.findById(usuario.nombre)

        //SI EL USUARIO EXISTE PASAR A COMPROBAR LA CONTRASEÑA
        return if (userOpcional.isPresent) {

            //OBTENEMOS EL USUARIO ENCONTRADO
            val user = userOpcional.get()

            //COMPROBAMOS LA CONTRASEÑA
            if (user.pass == usuario.pass) {
                user
            } else {
                "Contraseña incorrecta"
            }

        } else {
            //SI NO EXISTE EL USUARIO CREARLO Y MSOTRARLO
            usuarioRepository.save(usuario)
            usuario
        }

    }

    @PostMapping("pokemonFavorito/{token}/{pokemonId}")
    fun requestPokemonFavorito(@PathVariable token: String, @PathVariable pokemonId: Int): String {

        usuarioRepository.findAll().forEach { user ->

            if (user.token == token) {
                user.pokemonFavoritoId = pokemonId
                usuarioRepository.save(user)
                return "El usuario ${user.nombre} tiene un nuevo Pokemon favorito"
            }

        }

        return "TOKEN NO ENCONTRADO"
    }

    @GetMapping("mostrarPokemonFavorito/{token}")
    fun requestmostrarPokemonFavorito(@PathVariable token: String): Any {

        usuarioRepository.findAll().forEach { user ->
            if (user.token == token) {

                listaPokemon.listaPokemon.forEach {

                    if (user.pokemonFavoritoId == it.id) {
                        return it
                    }

                }
                return "El usuario no tiene pokemon favorito"

            }
        }
        return "Token no encontrado"
    }

    @PostMapping("PokemonCapturados/{token}/{pokemonId}")
    fun requestPokemonCapturados(@PathVariable token: String, @PathVariable pokemonId: Int) : Any {

        //Busco al usuario
        usuarioRepository.findAll().forEach { user ->

            //Compruebo que coincida el token del usuario con el otro token
            if (user.token == token) {

                //Si coincide añado a pokemonCapturas el id del pokemon
                user.pokeCapturas.add(pokemonId)
                //Y lo guardo
                usuarioRepository.save(user)

                //Hacel el return
                return "El usuario ${user.nombre} tiene ${user.pokeCapturas} pokemons capturados"
            }
        }
        //Si no cumple la condición de arriba sale este return
        return "No se ha encontrado el token"
    }

    @GetMapping("mostrarPokemonCapturados/{token}")
    fun requestmostrarPokemonCapturados(@PathVariable token: String) : Any {

        //Creo una lista de strings
        val listacapturadosinfo = mutableListOf<String>()

        //Busco el usuario
        usuarioRepository.findAll().forEach { user ->

            //Compruebo que coincida el token del usuario
            if (user.token == token){

                //Busco dentro de pokemonCapturas
                user.pokeCapturas.forEach {

                    //También busco dentro de las listaPokemon
                    listaPokemon.listaPokemon.forEach {encontrado ->

                        //Compruebo que coincida el id del pokemon encontrado se encuntre en la lista
                        if (encontrado.id == it){
                            //La añado a la lista de strings
                            listacapturadosinfo.add(encontrado.name)
                        }
                    }
                }
                //Si la lista esta vacía dice que el usuario no tien pokemons
                return if(listacapturadosinfo.isEmpty())
                    "El usuario no tiene pokemons"
                else
                    listacapturadosinfo
            }
        }
        return "Token no encontrado"
    }

    @PostMapping("intercambiarPokemon/{tokenUsuario1}/{tokenUsuario2}/{pokemonId1}/{pokemonId2}")
    fun requestintercambiarPokemon(@PathVariable tokenUsuario1: String,@PathVariable tokenUsuario2: String, @PathVariable pokemonId1: Int, @PathVariable pokemonId2: Int) :Any {

        usuarioRepository.findAll().forEach {usuario1->
            if (usuario1.token == tokenUsuario1){

                usuarioRepository.findAll().forEach { usuario2->
                    if (usuario2.token == tokenUsuario2){

                        usuario1.pokeCapturas.forEach {id1->
                            if (id1 == pokemonId1){

                                usuario2.pokeCapturas.forEach { id2->
                                    if (id2 == pokemonId2){

                                        usuario1.pokeCapturas.remove(id1)
                                        usuario1.pokeCapturas.add(id2)
                                        usuarioRepository.save(usuario1)

                                        usuario2.pokeCapturas.remove(id2)
                                        usuario2.pokeCapturas.add(id1)
                                        usuarioRepository.save(usuario2)

                                        return "Intercambio realizado"
                                    }
                                }
                                return "El usuario2 no tiene este pokemon2"
                            }
                        }
                        return "El usuario1 no tiene este pokemon1"
                    }
                }
                return "El token del usuario2 no existe"
            }
        }
        return "El token del usuario1 no existe"
    }
}


