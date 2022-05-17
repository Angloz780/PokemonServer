package com.pokemon.servet

import java.io.File

class ListaPokemon (var listaPokemon : MutableList<Pokemon> = mutableListOf()){

    companion object{
        const val filePath = "pokemons.json"

        fun fileExist() : Boolean {
            return File("hola.txt").exists()
        }

        fun cargarListaPokemonDeFichero() : ListaPokemon {
            val lista = gson.fromJson(File(filePath).readText(), ListaPokemon::class.java)
            return lista
        }

    }

    fun agregar(pokemon: Pokemon) {
        listaPokemon.add(pokemon)
    }

    fun imprimirPokemons(){
        if (listaPokemon.isEmpty()) {
            println("No se ha encontrado a ese Pokémon")
        } else {
            listaPokemon.forEach {
                println(it.decirNombreYTipo())
            }
        }
    }

    fun buscarPokemonPorNombre(nombreBuscado : String) : ListaPokemon {

        val listaFiltrada = listaPokemon.filter {
            it.name.contains(nombreBuscado)
        }

        return ListaPokemon(listaFiltrada.toMutableList())
    }

    fun buscarPokemonPorTipo(tipoBuscado : String) : ListaPokemon {

        val listaFiltrada = listaPokemon.filter { pokemon ->
            var encontrado = false
            pokemon.types.forEach {  tipo ->
                if (tipo.type.name == tipoBuscado)
                    encontrado = true
            }
            encontrado
        }

        return ListaPokemon(listaFiltrada.toMutableList())
    }

    fun buscarPokemonMasPequeno() : Pokemon {
        val listaFiltrada = listaPokemon.sortedBy {
            it.height
        }
        return listaFiltrada.first()
    }

    fun buscarPokemonMasGrande() : Pokemon {
        val listaFiltrada = listaPokemon.sortedBy {
            it.height
        }
        return listaFiltrada.last()
    }

    fun buscarPokemonMasGordo() : Pokemon {
        val listaFiltrada = listaPokemon.sortedBy {
            it.weight
        }
        return listaFiltrada.last()
    }

    fun buscarPokemonMasGordoDe(peso: Int) : ListaPokemon {
        val listaFiltrada = listaPokemon.filter {
            it.weight > peso
        }
        return ListaPokemon(listaFiltrada.toMutableList())
    }

    fun buscarPokemonMasGordoPorTipo(tipoBuscado: String): ListaPokemon{
        val listaFiltrada = listaPokemon.filter { pokemon ->
            var encontrado = false
            pokemon.types.forEach {  tipo ->
                if (tipo.type.name == tipoBuscado)
                    encontrado = true
            }
            encontrado
        }
        val lista = listaFiltrada.sortedBy {
            it.weight
        }
        return ListaPokemon(listaFiltrada.toMutableList())
    }

    fun buscarPokemonMasGordoPorTipoYPeso(tipoBuscado: String, peso : Int): ListaPokemon{
        val listaFiltrada = listaPokemon.filter { pokemon ->
            var tipoEncontrado = false
            val pesoAdecuado = pokemon.weight > peso

            pokemon.types.forEach {tipo->
                if (tipo.type.name == tipoBuscado)
                    tipoEncontrado = true
            }

            tipoEncontrado && pesoAdecuado
        }

        return ListaPokemon(listaFiltrada.toMutableList())
    }

    fun buscarPokemonPorAtaque(): ListaPokemon{
        val listaFiltrada = listaPokemon.filter {

            val buscarAtaque = "cut"
            var encontrado = false

            it.moves.forEach { ataque ->
                if (ataque.move.name == buscarAtaque)
                    encontrado = true
            }

            encontrado

        }

        return ListaPokemon(listaFiltrada.toMutableList())
    }

    fun buscarPokemonPorId(id: Int): Any {
        listaPokemon.forEach { pokemon: Pokemon ->
            if (pokemon.id == id) return pokemon
        }
        return "No encontrado"
    }

    fun borrarPokemonPorId(id: Int): Boolean {
        val p = buscarPokemonPorId(id)
        return if (p == "No encontrado") {
            false
        }else {
            listaPokemon.remove(p)
            true
        }
    }

    fun guardarEnFichero(){
        val file = File(filePath)
        file.writeText(gson.toJson(this))
    }

}