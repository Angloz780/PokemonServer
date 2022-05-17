package com.pokemon.servet

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger

@RestController
class PokemonController(private val usuarioRepository: UsuarioRepository) {

    var numRequestRecibidas = AtomicInteger(0)

    @GetMapping("hola")
    fun requestHola(): String {
        val numRequestRecibidasLocal = numRequestRecibidas.getAndIncrement()
        println("Me han dicho Hola $numRequestRecibidasLocal veces")
        Thread.sleep(4000)
        println("Voy a decir Buenas por vez $numRequestRecibidasLocal")
        return "Buenas! $numRequestRecibidasLocal"
    }

    @GetMapping("pokemon")
    fun requestPokemon(): ListaPokemon {
        return listaPokemon
    }

    @GetMapping("pokemonPorNombre/{nombre}")
    fun requestPokemonNombre(@PathVariable nombre: String): ListaPokemon {
        return listaPokemon.buscarPokemonPorNombre(nombre)
    }

    @GetMapping("pokemonPorTipo/{tipo}")
    fun requestPokemonTipo(@PathVariable tipo: String): ListaPokemon {
        return listaPokemon.buscarPokemonPorTipo(tipo)
    }

    @GetMapping("pokemonMasPequeno")
    fun requestPokemonMasPequeno(): Pokemon {
        return listaPokemon.buscarPokemonMasPequeno()
    }

    @GetMapping("pokemonMasAlto")
    fun requestPokemonMasAlto(): Pokemon {
        return listaPokemon.buscarPokemonMasGrande()
    }

    @GetMapping("pokemonMasGordo")
    fun requestPokemonMasGordo(): Pokemon {
        return listaPokemon.buscarPokemonMasGordo()
    }

    @GetMapping("pokemonMasGordo/{peso}")
    fun requestPokemonMasGordoPorPeso(@PathVariable peso: Int): ListaPokemon {
        return listaPokemon.buscarPokemonMasGordoDe(peso)
    }

    @GetMapping("pokemonMasGordoPorTipo/{tipo}")
    fun requestPokemonMasGordoPorPeso(@PathVariable tipo: String): ListaPokemon {
        return listaPokemon.buscarPokemonMasGordoPorTipo(tipo)
    }

    @GetMapping("pokemonMasGordoPorTipoYPeso/{tipo}/{peso}")
    fun requestPokemonMasGordoPorTipoYPeso(@PathVariable tipo: String, @PathVariable peso: Int): ListaPokemon {
        return listaPokemon.buscarPokemonMasGordoPorTipoYPeso(tipo, peso)
    }

    @GetMapping("buscarPokemonPorAtaque")
    fun requestPokemonPorAtaque(): ListaPokemon {
        return listaPokemon.buscarPokemonPorAtaque()
    }

    @GetMapping("pokemon/{id}")
    fun requestPokemonPorId(@PathVariable id: Int) : Any {
        return listaPokemon.buscarPokemonPorId(id)
    }

    @DeleteMapping("pokemon/{id}/{token}")
    fun requestDeletePokemonPorId(@PathVariable id : Int, @PathVariable token : String) :Any {
        usuarioRepository.findAll().forEach {
            if (it.token == token)
                return listaPokemon.borrarPokemonPorId(id)
        }
        return "Usuario no valido"
    }


}