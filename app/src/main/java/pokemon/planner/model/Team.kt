package pokemon.planner.model

import java.io.Serializable

class Team (
    public val name: String,
    public val version: GameVersion,
    public val gender: Boolean
): Serializable{
    //val pokemonList = ArrayList<Pokemon>()
    var dummyPokemon = Pokemon("-1", "dummy", Array<Int>(6) {0},Array<Int>(5) {0}, TYPE.NONE, TYPE.NONE, "dummy", "dummy", -1, arrayListOf(-1), arrayListOf(-1), "", Array<LearnSet>(14) { LearnSet(ArrayList<Move>(), ArrayList<Int>(), ArrayList<Int>())})
    val pokemonList = arrayOf(dummyPokemon, dummyPokemon, dummyPokemon, dummyPokemon, dummyPokemon, dummyPokemon)
    fun addPokemon(pokemon: Pokemon, slot: Int){
        this.pokemonList[slot] = pokemon
    }
    fun size(): Int{
        var size = 0
        for(pokemon in pokemonList){
            if(!pokemon.number.equals("-1")){
                size++
            }
        }
        return size
    }

    fun returnDatabaseTeam(): DatabaseTeam{
        var pokemonNumbers = ArrayList<Int>()
        pokemonNumbers.add(Integer.parseInt(pokemonList[0].number))
        pokemonNumbers.add(Integer.parseInt(pokemonList[1].number))
        pokemonNumbers.add(Integer.parseInt(pokemonList[2].number))
        pokemonNumbers.add(Integer.parseInt(pokemonList[3].number))
        pokemonNumbers.add(Integer.parseInt(pokemonList[4].number))
        pokemonNumbers.add(Integer.parseInt(pokemonList[5].number))
        return DatabaseTeam(name, version.readableName, gender, pokemonNumbers)
    }
}