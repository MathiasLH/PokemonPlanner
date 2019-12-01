package pokemon.planner.model

enum class GameVersion(val versionId: Int,val versionGroupId: Int, val generation: Int, val pokemonList: Int, val readableName: String, val color: String){
    NONE        (0, 0,0, 0,"None", "#ffffff"),
    RED         (1, 1,1, 0,"Pokémon Red", "#FF1111"),
    BLUE        (2, 1,1, 2,"Pokémon Blue", "#1111FF"),
    YELLOW      (3,2,1, 3,"Pokémon Yellow", "#FFD733"),
    GOLD        (4,3,2, 4,"Pokémon Gold", "#DAA520"),
    SILVER      (5,3,2, 5, "Pokémon Silver", "#C0C0C0"),
    CRYSTAL     (6,4,2, 6, "Pokémon Crystal", "#4FD9FF"),
    RUBY        (7,5,3, 7, "Pokémon Ruby", "#A00000"),
    SAPPHIRE    (8,5,3, 8, "Pokémon Sapphire", "#0000A0"),
    EMERALD     (9,6,3, 11, "Pokémon Emerald", "#00A000"),
    FIRERED     (10,7, 3, 9, "Pokémon Fire Red", "#FF7327"),
    LEAFGREEN   (11,7, 3, 10, "Pokémon Leaf Green", "#00DD00"),
    DIAMOND     (12,8,4, 14, "Pokémon Diamond", "#AAAAFF"),
    PEARL       (13,8, 4, 15, "Pokémon Pearl", "#FFAAAA"),
    PLATINUM    (14,9, 4, 16, "Pokémon Platinum", "#999999"),
    HEARTGOLD   (15,10,4, 17, "Pokémon Heart Gold", "#B69E00"),
    SOULSILVER  (16,10,4,18, "Pokémon Soul Silver", "#C0C0E1"),
    BLACK       (17,11,5, 20, "Pokémon Black", "#444444"),
    WHITE       (18,11, 5, 21, "Pokémon White", "#E1E1E1"),
    BLACK2      (21,14, 5, 22, "Pokémon Black 2", "#424B50"),
    WHITE2      (22, 14, 5, 23, "Pokémon White 2", "#E3CED0"),
}

