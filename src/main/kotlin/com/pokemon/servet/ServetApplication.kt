package com.pokemon.servet

import com.google.gson.Gson
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServetApplication

var gson =  Gson()
var listaPokemon = ListaPokemon()

fun main(args: Array<String>) {
	runApplication<ServetApplication>(*args)

	listaPokemon = if(ListaPokemon.fileExist()){
		ListaPokemon.cargarListaPokemonDeFichero()
	}else{
		ObtenerPokemonRequest.get()
	}
	listaPokemon.guardarEnFichero()

	println(listaPokemon)
}
