package pokemon.planner.model

import java.io.Serializable

class Move(
    val name: String,
    val id: Int,
    val type: TYPE,
    val moveType: String
):Serializable{
    override fun toString(): String{
        return name
    }
}