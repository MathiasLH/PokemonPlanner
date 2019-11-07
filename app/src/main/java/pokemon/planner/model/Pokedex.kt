package pokemon.planner.model

object Pokedex{
    init {
        //maybe init here?
    }
    var pokedex = arrayListOf<Pokemon>()

    fun addPokemonToPokedex(pokemon: Pokemon){
        this.pokedex.add(pokemon)
    }
}