package pokemon.planner.model

enum class GameVersion(val generation: Int, val pokemonList: Int, val readableName: String, val image: Int){
    NONE(0, 0,"None", 0),
    RED(1, 0,"Pokémon red",0),
    BLUE(1, 2,"Pokémon Blue",0),
    YELLOW(1, 3,"Pokémon Yellow", 0),
    GOLD(2, 4,"Pokémon Gold", 0),
    SILVER(2, 5, "Pokémon Silver", 0),
    CRYSTAL(2, 6, "Pokémon Crystal", 0),
    RUBY(3, 7, "Pokémon Ruby", 0),
    SAPPHIRE(3, 8, "Pokémon Sapphire", 0),
    FIRERED(3, 9, "Pokémon Fire Red", 0),
    LEAFGREEN(3, 10, "Pokémon Leaf Green", 0),
    EMERALD(3, 11, "Pokémon Emerald", 0),
    DIAMOND(4, 14, "Pokémon Diamond", 0),
    PEARL(4, 15, "Pokémon Pearl", 0),
    PLATINUM(4, 16, "Pokémon Platinum", 0),
    HEARTGOLD(4, 17, "Pokémon Heart Gold", 0),
    SOULSILVER(4,18, "Pokémon Soul Silver", 0),
    BLACK(5, 20, "Pokémon Black", 0),
    WHITE(5, 21, "Pokémon White", 0),
    BLACK2(5, 22, "Pokémon Black 2", 0),
    WHITE2(5, 23, "Pokémon White 2", 0),


}

