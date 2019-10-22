package pokemon.planner.model

import java.io.Serializable

class Pokemon(
    public val name: String,
    private val number: Int,
    private val attack: Int,
    private val defense: Int,
    private val sAttack: Int,
    private val sDefense: Int,
    private val speed: Int,
    private val hp: Int,
    private val primaryType: TYPE,
    private val secondaryType: TYPE): Serializable{


    fun isStrongAgainst(opponent: Pokemon): Int{
        return 0
    }



}
