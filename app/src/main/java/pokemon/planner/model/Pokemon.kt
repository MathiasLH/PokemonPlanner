package pokemon.planner.model

import java.io.Serializable

class Pokemon(
    val number: String,
       val name: String,
    val stats : IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0),//hp, atk, dfns, sp. atk, sp. dfns, speed, total
    val gen1SpecialStat: Int,
    val gen1Total: Int,
    val primaryType: TYPE,
    val secondaryType: TYPE,
    val ability1: String,
    val ability2: String
): Serializable{


    fun isStrongAgainst(opponent: Pokemon): Int{
        return 0
    }



}
