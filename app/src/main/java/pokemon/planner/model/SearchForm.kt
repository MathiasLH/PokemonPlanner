package pokemon.planner.model

import java.io.Serializable

class SearchForm(
    val name: String,
    val number: String,
    val minStats : ArrayList<Int>,//hp, atk, dfns, sp. atk, sp. dfns, speed, total
    val maxStats: ArrayList<Int>,
    val type1: TYPE,
    val type2: TYPE,
    val version: GameVersion
    ) : Serializable{


}