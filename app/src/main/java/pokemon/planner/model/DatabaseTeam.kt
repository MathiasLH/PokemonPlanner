package pokemon.planner.model

import java.io.Serializable

class DatabaseTeam (
    public val name: String,
    public val version: String,
    public val gender: Boolean,
    val pokemon: ArrayList<Int>
): Serializable {

}