package pokemon.planner.model

import java.io.Serializable

class SearchForm(
    private val name: String,
    private val number: Int,
    private val minStats : IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0),//hp, atk, dfns, sp. atk, sp. dfns, speed, total
    private val maxStats: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0)
    ){


}