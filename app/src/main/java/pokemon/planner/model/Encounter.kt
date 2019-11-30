package pokemon.planner.model

class Encounter (
    public val versionId: Int,
    public val locationId: Int,
    public val encounterSlotId: Int,
    public val pokemonId: Int,
    public val minLevel: Int,
    public val maxLevel: Int
){

}