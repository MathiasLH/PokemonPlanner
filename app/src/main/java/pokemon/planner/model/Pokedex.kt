package pokemon.planner.model

import android.graphics.Bitmap


object Pokedex{
    var pokedexSize = 649
    var pokedex = arrayListOf<Pokemon>()
    var abilities = mutableMapOf<Int, String>()

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

    fun getPokemonList(listNumber: Int): ArrayList<Pokemon>{
        var pokemonList = ArrayList<Pokemon>()
        for(x in 0..Pokedex.pokedex.size-1){
            var availability = Pokedex.pokemonAvailability[x][listNumber]
            if(availability.equals("C") || availability.equals("R") || availability.equals("E") || availability.equals("B") || availability.equals("EV") || availability.equals("S") || availability.equals("D")){
                //pokemon IS available in the game
                pokemonList.add(Pokedex.pokedex[x])
            }
        }
        return pokemonList
    }

    fun getGenSpecificStatNames(team: Team): Array<String>{
        var statNames: Array<String>
        if(team.version.generation == 1){
            statNames = arrayOf("HP", "Attack", "Defense", "Special", "Speed", "Total")
        }else{
            statNames = arrayOf("HP", "Attack", "Defense", "Sp. Attack", "Sp. Defense", "Speed", "Total")
        }
        return statNames
    }




}