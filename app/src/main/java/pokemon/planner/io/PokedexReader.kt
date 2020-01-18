package pokemon.planner.io

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pokemon.planner.model.*
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection

class PokedexReader(private val context: Context) {

    fun readPokedexData(){
        var idArray = Array<String>(Pokedex.pokedexSize) {"-1"}
        var nameArray = Array<String>(Pokedex.pokedexSize) {"n/a"}
        var statsArray = Array<Array<Int>>(Pokedex.pokedexSize) { Array<Int>(7) {0} }
        var gen1StatArray = Array<Array<Int>>(Pokedex.pokedexSize) { Array<Int>(6) {0} }
        var gen1SpecialArray = Array<Int>(Pokedex.pokedexSize) {-1}
        var gen1TotalsArray = Array<Int>(Pokedex.pokedexSize) {-1}
        var primaryTypeArray = Array<TYPE>(Pokedex.pokedexSize) {TYPE.NONE}
        var secondaryTypeArray = Array<TYPE>(Pokedex.pokedexSize) {TYPE.NONE}
        var primaryAbilityArray = Array<String>(Pokedex.pokedexSize) {"n/a"}
        var secondaryAbilityArray = Array<String>(Pokedex.pokedexSize) {"n/a"}
        var minimumLevelArray = Array<Int>(Pokedex.pokedexSize) {-1}
        var evolvesTo = Array<ArrayList<Int>>(Pokedex.pokedexSize) {ArrayList<Int>()}
        var evolvesFrom = Array<ArrayList<Int>>(Pokedex.pokedexSize) {ArrayList<Int>()}
        var evolveCriteria = Array<String>(Pokedex.pokedexSize) {""}
        var learnSets = Array<Array<LearnSet>>(Pokedex.pokedexSize) {Array<LearnSet>(14) { LearnSet(ArrayList<Move>(), ArrayList<Int>(), ArrayList<Int>())}}


        //read name and ID of pokemon
        context.assets.open("pokemon.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..Pokedex.pokedexSize-1){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    idArray[i] = inputArray[0]
                    nameArray[i] = inputArray[1]
                }
            }
        }

        //read stats of pokemon
        context.assets.open("pokemon_stats.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..3893){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    statsArray[Integer.parseInt(inputArray[0])-1][Integer.parseInt(inputArray[1])-1] = Integer.parseInt(inputArray[2])
                }
            }
        }

        //read types of pokemon
        context.assets.open("pokemon_types.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..957){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    if(inputArray[2].equals("1")){
                        primaryTypeArray[Integer.parseInt(inputArray[0])-1] = typeIDtoTYPE(inputArray[1])
                    }else{
                        secondaryTypeArray[Integer.parseInt(inputArray[0])-1] = typeIDtoTYPE(inputArray[1])
                    }
                }
            }
        }

        //read info about abilities
        context.assets.open("abilities.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..232){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    Pokedex.abilities[Integer.parseInt(inputArray[0])] = inputArray[1].replace('-', ' ')
                }
            }
        }

        //read abilities of pokemon
        context.assets.open("pokemon_abilities.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..1598){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    if(inputArray[3].equals("1")){
                        primaryAbilityArray[Integer.parseInt(inputArray[0])-1] = Pokedex.abilities[Integer.parseInt(inputArray[1])] as String
                    }else if(inputArray[3].equals("2")){
                        secondaryAbilityArray[Integer.parseInt(inputArray[0])-1] = Pokedex.abilities[Integer.parseInt(inputArray[1])] as String
                    }
                }
            }
        }
        //read special stat
        context.assets.open("gen1stats.csv").bufferedReader().use {
            for(i in 0..150){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    gen1SpecialArray[i] = Integer.parseInt(inputArray[6])
                    gen1TotalsArray[i] = statsArray[i][0] + statsArray[i][1] + statsArray[i][2] + statsArray[i][5] + gen1SpecialArray[i]
                }
            }
            println("Reading gen 1 stats")
        }

        //read evolutionLevels
        context.assets.open("pokemon_evolution.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..403){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    if(inputArray[2].equals("1") && Integer.parseInt(inputArray[1]) < 649){
                        //pokemon evolves by level
                        if(!inputArray[4].equals("")){
                            minimumLevelArray[Integer.parseInt(inputArray[1])-1] = Integer.parseInt(inputArray[4])
                        }
                    }else if(inputArray[2].equals("3") && Integer.parseInt(inputArray[1]) < 649){

                    }
                }
            }
            println("reading evolutions")
        }

        //read info about locations
        context.assets.open("locations.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..781){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    Pokedex.locationNames[Integer.parseInt(inputArray[0])] = inputArray[2]
                }
            }

        }
        context.assets.open("location_areas.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..683){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    Pokedex.locationIds[Integer.parseInt(inputArray[0])] = Integer.parseInt(inputArray[1])
                }
            }
            println("reading locations")
        }

        context.assets.open("moves.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..728){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    Pokedex.moves[Integer.parseInt(inputArray[0])] = Move(inputArray[1].replace('-', ' '), Integer.parseInt(inputArray[0]), typeIDtoTYPE(inputArray[3]),inputArray[9])
                }
            }
            println("reading moves")
        }

        context.assets.open("pokemon_moves.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..410113){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    if(Integer.parseInt(inputArray[1]) <= 14){
                        learnSets[Integer.parseInt(inputArray[0])-1][Integer.parseInt(inputArray[1])-1].addMove(Pokedex.moves[Integer.parseInt(inputArray[2])] as Move, Integer.parseInt(inputArray[3]), Integer.parseInt(inputArray[4]))
                    }
                }
            }
            println("reading learnsets")
        }

        for(i in 0..Pokedex.pokedexSize-1){
            gen1StatArray[i][0] = statsArray[i][0]
            gen1StatArray[i][1] = statsArray[i][1]
            gen1StatArray[i][2] = statsArray[i][2]
            gen1StatArray[i][3] = gen1SpecialArray[i]
            gen1StatArray[i][4] = statsArray[i][5]
            gen1StatArray[i][5] = statsArray[i][0] + gen1StatArray[i][1] + gen1StatArray[i][2] + gen1StatArray[i][3] + gen1StatArray[i][4]
            statsArray[i][6] = statsArray[i][0] + statsArray[i][1] + statsArray[i][2] + statsArray[i][3] + statsArray[i][4] + statsArray[i][5]
            Pokedex.addPokemonToPokedex(Pokemon(idArray[i], nameArray[i], statsArray[i], gen1StatArray[i], primaryTypeArray[i], secondaryTypeArray[i], primaryAbilityArray[i], secondaryAbilityArray[i], minimumLevelArray[i], evolvesFrom[i], evolvesTo[i], evolveCriteria[i], learnSets[i]))
        }
        readEvolutionFiles()
        readAvailabilityFiles()
        readPokemonEncounters()
        println("done reading.")
    }

    fun readPokemonEncounters(){


        context.assets.open("encounters.csv").bufferedReader().use {
            it.readLine()
            var j = 0
            for(i in 0..46833){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    //black majiks
                    //reads the entire encounter file, sorts the encounters into each list (which represent one game each)
                    //THEN sorts those encounters for each pokemon in that game.
                    if(Integer.parseInt(inputArray[4]) <= 649 && Integer.parseInt(inputArray[1]) <= 22){
                        Pokedex.encounters[Integer.parseInt(inputArray[1])-1][Integer.parseInt(inputArray[4])-1].add(Encounter(Integer.parseInt(inputArray[1]), Pokedex.locationIds[Integer.parseInt(inputArray[2])]as Int,Integer.parseInt(inputArray[3]),Integer.parseInt(inputArray[4]), Integer.parseInt(inputArray[5]), Integer.parseInt(inputArray[6])))
                    }
                }
            }
            println("Reading encounters")
        }
    }

    fun versionIdToGameVersion(versionId: Int): GameVersion{
        when(versionId){
            1 -> return GameVersion.RED
            2 -> return GameVersion.BLUE
            3 -> return GameVersion.YELLOW
            4 -> return GameVersion.GOLD
            5 -> return GameVersion.SILVER
            6 -> return GameVersion.CRYSTAL
            7 -> return GameVersion.RUBY
            8 -> return GameVersion.SAPPHIRE
            9 -> return GameVersion.EMERALD
            10 -> return GameVersion.FIRERED
            11 -> return GameVersion.LEAFGREEN
            12 -> return GameVersion.DIAMOND
            13 -> return GameVersion.PEARL
            14 -> return GameVersion.PLATINUM
            15 -> return GameVersion.HEARTGOLD
            16 -> return GameVersion.SOULSILVER
            17 -> return GameVersion.BLACK
            18 -> return GameVersion.WHITE
            21 -> return GameVersion.BLACK2
            22 -> return GameVersion.WHITE2
            else-> return GameVersion.NONE
        }
    }

    fun readEvolutionFiles(){
        readEvolutionFile(84, "gen1Evolution.csv")
        readEvolutionFile(49, "gen2Evolution.csv")
        readEvolutionFile(74, "gen3Evolution.csv")
        readEvolutionFile(40, "gen4Evolution.csv")
        readEvolutionFile(71, "gen5Evolution.csv")

    }

    private fun readEvolutionFile(size: Int, filename: String) {
        context.assets.open(filename).bufferedReader().use {
            //it.readLine()
            for(i in 0..size){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    if(inputArray.size > 4 && !inputArray[5].equals("")){
                        //Pokemon has one evolution
                        Pokedex.pokedex.get(Integer.parseInt(inputArray[2])-1).evolvesTo.add(Integer.parseInt(inputArray[5]))
                        Pokedex.pokedex.get(Integer.parseInt(inputArray[5])-1).evolvesFrom.add(Integer.parseInt(inputArray[2]))
                        Pokedex.pokedex.get(Integer.parseInt(inputArray[5])-1).evolveCriteria = inputArray[4]

                        if(inputArray.size > 7 && !inputArray[8].equals("")){
                            //pokemon has two evolutions
                            Pokedex.pokedex.get(Integer.parseInt(inputArray[5])-1).evolvesTo.add(Integer.parseInt(inputArray[8]))
                            Pokedex.pokedex.get(Integer.parseInt(inputArray[8])-1).evolvesFrom.add(Integer.parseInt(inputArray[5]))
                            Pokedex.pokedex.get(Integer.parseInt(inputArray[8])-1).evolveCriteria = inputArray[7]
                        }
                    }

                }
            }
            println("reading also evolutins?")
        }
    }


    /*fun readPokedexFile() {

        context.assets.open("pokedex.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..Pokedex.pokedexSize-1){
                val line: String? = it.readLine()
                if(line != null){
                    val pokemon = line.split(",")
                    val test = Pokemon(
                        pokemon[1],
                        pokemon[2],
                        intArrayOf(Integer.parseInt(pokemon[3]), Integer.parseInt(pokemon[4]), Integer.parseInt(pokemon[5]), Integer.parseInt(pokemon[6]), Integer.parseInt(pokemon[7]),Integer.parseInt(pokemon[8]), Integer.parseInt(pokemon[9])),
                        stringToTYPE(pokemon[10]),
                        stringToTYPE(pokemon[11]),
                        pokemon[12], pokemon[13],
                        pokemon[32])
                    Pokedex.addPokemonToPokedex(test)
                }
            }
        }
    }*/

    fun readAvailabilityFiles() {
        readAvailabilityFile(151, 0, 0, "gen1Availability.csv")
        readAvailabilityFile(99, 4, 151, "gen2Availability.csv")
        readAvailabilityFile(134, 7, 251, "gen3Availability.csv")
        readAvailabilityFile(106, 14, 386, "gen4Availability.csv")
        readAvailabilityFile(155, 20, 493, "gen5Availability.csv")
        println("yo")
    }

    fun readAvailabilityFile(size: Int, offset: Int, pokedexStart: Int, filename: String){
        context.assets.open(filename).bufferedReader().use {
            //it.readLine()
            for(i in 0..size){
                val line: String? = it.readLine()
                if(line != null){
                    var inputArray = line.split(",")
                    for(j in 2 .. inputArray.size-1){
                        Pokedex.pokemonAvailability[pokedexStart+i][j-2+offset]= inputArray[j]
                    }

                }
            }
        }
        println("yo")
    }

    fun typeIDtoTYPE(input: String): TYPE{
        when(Integer.parseInt(input)){
            1 -> return TYPE.NORMAL
            2 -> return TYPE.FIGHTING
            3 -> return TYPE.FLYING
            4 -> return TYPE.POISON
            5 -> return TYPE.GROUND
            6 -> return TYPE.ROCK
            7 -> return TYPE.BUG
            8 -> return TYPE.GHOST
            9 -> return TYPE.STEEL
            10 -> return TYPE.FIRE
            11 -> return TYPE.WATER
            12 -> return TYPE.GRASS
            13 -> return TYPE.ELECTRIC
            14 -> return TYPE.PSYCHIC
            15 -> return TYPE.ICE
            16 -> return TYPE.DRAGON
            17 -> return TYPE.DARK
            18 -> return TYPE.NORMAL
            else -> return TYPE.NONE
        }
    }

    fun stringToTYPE(input: String): TYPE {
        when(input){
            "Normal" -> return TYPE.NORMAL
            "Fighting" -> return TYPE.FIGHTING
            "Flying" -> return TYPE.FLYING
            "Poison" -> return TYPE.POISON
            "Ground" -> return TYPE.GROUND
            "Rock" -> return TYPE.ROCK
            "Bug" -> return TYPE.BUG
            "Ghost" -> return TYPE.GHOST
            "Steel" -> return TYPE.STEEL
            "Fire" -> return TYPE.FIRE
            "Water" -> return TYPE.WATER
            "Grass" -> return TYPE.GRASS
            "Electric" -> return TYPE.ELECTRIC
            "Psychic" -> return TYPE. PSYCHIC
            "Ice" -> return TYPE.ICE
            "Dragon" -> return TYPE.DRAGON
            "Dark" -> return TYPE.DARK
            "Fairy" -> return TYPE.FAIRY
            else -> return TYPE.NONE
        }
    }

    fun downloadImages(){

        downloadSmallImages()
        downloadLargeImages()

        /*for(pokemon in Pokedex.pokedex){
            downloadSmallImages(pokemon)
        }
        for(pokemon in Pokedex.pokedex){
            downloadLargeImages(pokemon)
        }*/



    }

    fun loadImages() {

        for(x in 0..Pokedex.smallImages.size-1){
            Pokedex.smallImages[x] = loadBitmap("small" + (x+1).toString())
            Pokedex.largeImages[x] = loadBitmap("large" + (x+1).toString())
        }
        println("yo")
    }

    private fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = java.net.URL(src)
            val connection = url
                .openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
    private fun downloadSmallImages(/*pokemon: Pokemon*/) {
        doAsync {
            for(pokemon in Pokedex.pokedex){
                var pokemonNumberWithZeroes: String
                if(pokemon.number.length == 1){
                    pokemonNumberWithZeroes = "00" + pokemon.number
                }else if (pokemon.number.length == 2){
                    pokemonNumberWithZeroes = "0" + pokemon.number
                }else{
                    pokemonNumberWithZeroes = pokemon.number
                }
                var url: String = "https://www.serebii.net/pokedex-xy/icon/" + pokemonNumberWithZeroes +".png"
                val smallImage = getBitmapFromURL(url);
                saveFile(smallImage, "small" + pokemon.number)
                Pokedex.smallImages[Integer.parseInt(pokemon.number)-1] = smallImage
                //smallImages.add(Integer.parseInt(pokemon.number)-1, getBitmapFromURL(url))
                uiThread {
                }
            }
        }
    }

    private fun downloadLargeImages(/*pokemon: Pokemon*/) {
        doAsync {
            for(pokemon in Pokedex.pokedex){
                var url: String = "https://www.serebii.net/art/th/"+ pokemon.number + ".png"
                val largeImage = getBitmapFromURL(url)
                saveFile(largeImage, "large"+pokemon.number)
                Pokedex.largeImages[Integer.parseInt(pokemon.number)-1] = largeImage
                uiThread {
                }
            }

        }
    }

    fun loadBitmap(picName: String): Bitmap? {
        var b: Bitmap? = null
        var fis: FileInputStream? = null
        try {
            fis = context.openFileInput(picName)
            b = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            Log.d(ContentValues.TAG, "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "io exception")
            e.printStackTrace()
        } finally {
            fis?.close()
        }
        return b
    }

    fun saveFile(b: Bitmap?, picName: String) {
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE)
            b?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
            Log.d(ContentValues.TAG, "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "io exception")
            e.printStackTrace()
        } finally {
            fos?.close()
        }
    }
}
