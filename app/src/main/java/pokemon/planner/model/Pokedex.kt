package pokemon.planner.model

import android.graphics.Bitmap


object Pokedex{
    var pokedexSize = 649
    var pokedex = arrayListOf<Pokemon>()
    var locationNames = mutableMapOf<Int, String>()
    var locationIds = mutableMapOf<Int, Int>()
    var abilities = mutableMapOf<Int, String>()
    var moves = mutableMapOf<Int, Move>()

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

    var encounters = Array<Array<ArrayList<Encounter>>>(22) {Array<ArrayList<Encounter>>(Pokedex.pokedexSize) {ArrayList<Encounter>()}}



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
    fun getGenSpecificTypes(team: Team):ArrayList<TYPE>{
        if(team.version.generation == 1){
            return arrayListOf(TYPE.NONE, TYPE.NORMAL, TYPE.FIGHTING, TYPE.FLYING, TYPE.POISON, TYPE.GROUND, TYPE.ROCK, TYPE.BUG, TYPE.GHOST, TYPE.FIRE, TYPE.WATER, TYPE.GRASS, TYPE.ELECTRIC, TYPE.PSYCHIC, TYPE.ICE, TYPE.DRAGON)
        }else{
            return arrayListOf(TYPE.NONE, TYPE.NORMAL, TYPE.FIGHTING, TYPE.FLYING, TYPE.POISON, TYPE.GROUND, TYPE.ROCK, TYPE.BUG, TYPE.GHOST, TYPE.STEEL, TYPE.FIRE, TYPE.WATER, TYPE.GRASS, TYPE.ELECTRIC, TYPE.PSYCHIC, TYPE.ICE, TYPE.DRAGON, TYPE.DARK)
        }
    }

    fun moveNameToMove(input: String): Move{
        for(x in 0..moves.size){
            if(moves[x] != null){
                if((moves[x] as Move).name.equals(input)){
                    return moves[x] as Move
                }
            }
        }
        return moves[1] as Move
    }

    fun stringToVersion(input: String): GameVersion {
        when(input){
            "Pokémon Red" -> return GameVersion.RED
            "Pokémon Blue" -> return GameVersion.BLUE
            "Pokémon Yellow" -> return GameVersion.YELLOW
            "Pokémon Gold" -> return GameVersion.GOLD
            "Pokémon Silver" -> return GameVersion.SILVER
            "Pokémon Crystal" -> return GameVersion.CRYSTAL
            "Pokémon Ruby" -> return GameVersion.RUBY
            "Pokémon Sapphire" -> return GameVersion.SAPPHIRE
            "Pokémon Fire Red" -> return GameVersion.FIRERED
            "Pokémon Leaf Green" -> return GameVersion.LEAFGREEN
            "Pokémon Emerald" -> return GameVersion.EMERALD
            "Pokémon Diamond" -> return GameVersion.DIAMOND
            "Pokémon Pearl" -> return GameVersion.PEARL
            "Pokémon Platinum" -> return GameVersion.PLATINUM
            "Pokémon Heart Gold" -> return GameVersion.HEARTGOLD
            "Pokémon Soul Silver" -> return GameVersion.SOULSILVER
            "Pokémon Black" -> return GameVersion.BLACK
            "Pokémon White" -> return GameVersion.WHITE
            "Pokémon Black 2" -> return GameVersion.BLACK2
            "Pokémon White 2" -> return GameVersion.WHITE2
            else -> return GameVersion.NONE
        }
    }

    fun getVersionNames(): Array<String>{
        return arrayOf(
            GameVersion.RED.readableName,
            GameVersion.BLUE.readableName,
            GameVersion.YELLOW.readableName,
            GameVersion.GOLD.readableName,
            GameVersion.SILVER.readableName,
            GameVersion.CRYSTAL.readableName,
            GameVersion.RUBY.readableName,
            GameVersion.SAPPHIRE.readableName,
            GameVersion.FIRERED.readableName,
            GameVersion.LEAFGREEN.readableName,
            GameVersion.EMERALD.readableName,
            GameVersion.DIAMOND.readableName,
            GameVersion.PEARL.readableName,
            GameVersion.PLATINUM.readableName,
            GameVersion.HEARTGOLD.readableName,
            GameVersion.SOULSILVER.readableName,
            GameVersion.BLACK.readableName,
            GameVersion.WHITE.readableName,
            GameVersion.BLACK2.readableName,
            GameVersion.WHITE2.readableName
        )
    }




}