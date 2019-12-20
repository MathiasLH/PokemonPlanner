package pokemon.planner.model

import java.io.Serializable

class SearchForm(
    var name: String,
    var number: String,
    var hpMin: Int,
    var hpMax: Int,
    var attackMin: Int,
    var attackMax: Int,
    var defenseMin: Int,
    var defenseMax: Int,
    var specialMin: Int,
    var specialMax: Int,
    var spAttackMin: Int,
    var spAttackMax: Int,
    var spDefenseMin: Int,
    var spDefenseMax: Int,
    var speedMin: Int,
    var speedMax: Int,
    var totalMin: Int,
    var totalMax: Int,
    var type1: TYPE,
    var type2: TYPE,
    var ability1: String,
    var ability2: String,
    var move: Move,
    var version: GameVersion
    ) : Serializable{


}