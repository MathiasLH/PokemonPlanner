package pokemon.planner.model

enum class GameVersion(val generation: Int, val pokemonList: Int, val image: Int){
    NONE(0, 0,0),
    RED(1, 2,0),
    BLUE(1, 4,0),
    YELLOW(1, 5,0),
    GOLD(2, 6,0),
    SILVER(2, 7, 0),
    CRYSTAL(2, 8, 0),
    RUBY(3, 9, 0),
    SAPPHIRE(3, 10, 0),
    FIRERED(3, 11, 0),
    LEAFGREEN(3, 12, 0),
    EMERALD(3, 13, 0),
    DIAMOND(4, 16, 0),
    PEARL(4, 17, 0),
    PLATINUM(4, 18, 0),
    HEARTGOLD(4, 19, 0),
    SOULSILVER(4,20, 0),
    BLACK(5, 22, 0),
    WHITE(5, 23, 0),
    BLACK2(5, 24, 0),
    WHITE2(5, 25, 0),
}