package com.pokemon.servet

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger

@RestController
class PokemonController {

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
    fun requestPokemonTipo(@PathVariable tipo: String): ListaPokemon{
        return listaPokemon.buscarPokemonPorTipo(tipo)
    }

    @GetMapping("pokemonMasPequeno")
    fun requestPokemonMasPequeno(): Pokemon {
        return listaPokemon.buscarPokemonMasPequeno()
    }

    @GetMapping("pokemonMasAlto")
    fun requestPokemonMasAlto(): Pokemon {
        return listaPokemon.buscarPokemonMasAlto()
    }

    @GetMapping("pokemonMasGordo")
    fun requestPokemonMasGordo(): Pokemon {
        return listaPokemon.buscarPokemonMasGordo()
    }

    @GetMapping("pokemonMasGordo/{peso}")
    fun requestPokemonMasGordoPorPeso(@PathVariable peso: Int): ListaPokemon {
        return listaPokemon.buscarPokemonMasGordoPorPeso(peso)
    }

    @GetMapping("pokemonMasGordoPorTipo/{tipo}")
    fun requestPokemonMasGordoPorPeso(@PathVariable tipo: String): ListaPokemon {
        return listaPokemon.buscarPokemonMasGordoPorTipo(tipo)
    }
}