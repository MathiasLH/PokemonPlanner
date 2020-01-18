package pokemon.planner.model

import android.location.Criteria
import java.io.Serializable

class Pokemon(
    val number: String,
    val name: String,
    val stats : Array<Int>,//hp, atk, dfns, sp. atk, sp. dfns, speed
    val gen1Stats : Array<Int>,//hp, atk, defense, special, speed
    val typePrimary: TYPE,
    val typeSecondary: TYPE,
    val abilityPrimary: String,
    val abilitySecondary: String,
    val minLevel: Int,
    val evolvesFrom: ArrayList<Int>,
    val evolvesTo: ArrayList<Int>,
    var evolveCriteria: String,
    var learnSets: Array<LearnSet>
): Serializable{


    fun isStrongAgainst(opponent: Pokemon): Int{
        return 0
    }



}
