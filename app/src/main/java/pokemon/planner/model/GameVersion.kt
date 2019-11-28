package pokemon.planner.model

enum class GameVersion(val generation: Int, val pokemonList: Int, val readableName: String, val image: Int){
    NONE(0, 0,"None", 0),
    RED(1, 2,"Pokémon red",0),
    BLUE(1, 4,"Pokémon Blue",0),
    YELLOW(1, 5,"Pokémon Yellow", 0),
    GOLD(2, 6,"Pokémon Gold", 0),
    SILVER(2, 7, "Pokémon Silver", 0),
    CRYSTAL(2, 8, "Pokémon Crystal", 0),
    RUBY(3, 9, "Pokémon Ruby", 0),
    SAPPHIRE(3, 10, "Pokémon Sapphire", 0),
    FIRERED(3, 11, "Pokémon Fire Red", 0),
    LEAFGREEN(3, 12, "Pokémon Leaf Green", 0),
    EMERALD(3, 13, "Pokémon Emerald", 0),
    DIAMOND(4, 16, "Pokémon Diamond", 0),
    PEARL(4, 17, "Pokémon Pearl", 0),
    PLATINUM(4, 18, "Pokémon Platinum", 0),
    HEARTGOLD(4, 19, "Pokémon Heart Gold", 0),
    SOULSILVER(4,20, "Pokémon Soul Silver", 0),
    BLACK(5, 22, "Pokémon Black", 0),
    WHITE(5, 23, "Pokémon White", 0),
    BLACK2(5, 24, "Pokémon Black 2", 0),
    WHITE2(5, 25, "Pokémon White 2", 0),


}

