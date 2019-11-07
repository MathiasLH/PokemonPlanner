package pokemon.planner.model

import java.io.Serializable

class SearchForm(
    val name: String,
    val number: String,
    val minStats : IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0),//hp, atk, dfns, sp. atk, sp. dfns, speed, total
    val maxStats: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0),
    val type1: TYPE,
    val type2: TYPE
    ) : Serializable{


}