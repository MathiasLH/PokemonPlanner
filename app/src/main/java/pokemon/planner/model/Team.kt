package pokemon.planner.model

import java.io.Serializable

class Team (
    public val name: String
): Serializable{
    //val pokemonList = ArrayList<Pokemon>()
    val pokemonList = arrayOfNulls<Pokemon>(6)
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