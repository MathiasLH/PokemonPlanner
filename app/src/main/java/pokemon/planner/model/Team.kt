package pokemon.planner.model

import java.io.Serializable

class Team (
    public val name: String
): Serializable{
    //val pokemonList = ArrayList<Pokemon>()
    var dummyPokemon = Pokemon("-1", "dummy", intArrayOf(0,0,0,0,0,0,0), TYPE.NONE, TYPE.NONE, "dummy", "dummy", "dummy")
    val pokemonList = arrayOf(dummyPokemon, dummyPokemon, dummyPokemon, dummyPokemon, dummyPokemon, dummyPokemon)
    fun addPokemon(pokemon: Pokemon, slot: Int){
        this.pokemonList[slot] = pokemon
    }
    fun size(): Int{
        var size: Int = 0
        for(pokemon in pokemonList){
            if(pokemon != null){
                size++
            }
        }
        return size
    }
}