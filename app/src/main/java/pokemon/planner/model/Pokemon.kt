package pokemon.planner.model

import android.location.Criteria
import java.io.Serializable

class Pokemon(
    val number: String,
       val name: String,
    val stats : Array<Int>,//hp, atk, dfns, sp. atk, sp. dfns, speed
    val gen1Stats : Array<Int>,//hp, atk, defense, special, speed
    val primaryType: TYPE,
    val secondaryType: TYPE,
    val ability1: String,
    val ability2: String,
    val minLevel: Int,
    val evolvesFrom: ArrayList<Int>,
    val evolvesTo: ArrayList<Int>,
    var evolveCriteria: String
): Serializable{


    fun isStrongAgainst(opponent: Pokemon): Int{
        return 0
    }



}
