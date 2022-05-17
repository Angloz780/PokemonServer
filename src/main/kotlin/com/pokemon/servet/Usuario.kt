package com.pokemon.servet

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Usuario (@Id var nombre : String, var pass : String){

    var token = nombre + pass
    var pokemonFavoritoId : Int? = null

    @ElementCollection
    var pokeCapturas = mutableListOf<Int>()
}