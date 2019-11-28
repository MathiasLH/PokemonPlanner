package pokemon.planner.model

import java.io.Serializable

class Team (
    public val name: String,
    public val version: GameVersion,
    public val gender: Boolean
): Serializable{
    //val pokemonList = ArrayList<Pokemon>()
    var dummyPokemon = Pokemon("-1", "dummy", intArrayOf(0,0,0,0,0,0,0), TYPE.NONE, TYPE.NONE, "dummy", "dummy")
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
}