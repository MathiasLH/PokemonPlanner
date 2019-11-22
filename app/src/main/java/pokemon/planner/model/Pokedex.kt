package pokemon.planner.model

import android.graphics.Bitmap


object Pokedex{
    var pokedexSize = 649
    var pokedex = arrayListOf<Pokemon>()
    var smallImages = Array<Bitmap?>(pokedexSize) {null}

    var largeImages = Array<Bitmap?>(pokedexSize) {null}

    //Create two dimensional array. The first index represents each pokemon in the pokedex.
    //The inner index is then the availability of that pokemon in each game.
    //Since there are currently 20 games in this app, that is how many lists are created.
    //The lists are initialized with the value "U" for Unavailable, meaning it cannot be obtained in the game,
    //for example a generation 2 pokemon will be unavailable in a generation 1 game.
    //The games in order are:
    //red, blue, yellow, gold, silver, crystal, ruby, sapphire, firered, leafgreen, emerald, diamond, pearl, platinum, heartgold, soulsilver, black, white, black2, white2
    var pokemonAvailability = Array<Array<String>>(pokedexSize) {Array<String>(36){"U"} }

    fun addPokemonToPokedex(pokemon: Pokemon){

        this.pokedex.add(pokemon)
    }




}