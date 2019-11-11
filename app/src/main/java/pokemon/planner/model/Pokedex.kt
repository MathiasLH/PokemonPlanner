package pokemon.planner.model

import android.graphics.Bitmap


object Pokedex{
    var pokedexSize = 151
    var pokedex = arrayListOf<Pokemon>()
    var smallImages = Array<Bitmap?>(pokedexSize) {null}

    var largeImages = Array<Bitmap?>(pokedexSize) {null}

    fun addPokemonToPokedex(pokemon: Pokemon){

        this.pokedex.add(pokemon)
    }




}