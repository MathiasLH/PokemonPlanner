package pokemon.planner.io

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pokemon.planner.model.Pokemon
import pokemon.planner.model.TYPE
import pokemon.planner.model.Pokedex
import java.io.*
import java.net.HttpURLConnection

class PokedexReader(private val context: Context) {


        fun readFile() {

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

        for(pokemon in Pokedex.pokedex){
            downloadSmallImages(pokemon)
        }
        for(pokemon in Pokedex.pokedex){
            downloadLargeImages(pokemon)
        }



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
    private fun downloadSmallImages(pokemon: Pokemon) {
        doAsync {
            var pokemonNumberWithZeroes: String
            if(pokemon.number.length == 1){
                pokemonNumberWithZeroes = "00" + pokemon.number
            }else if (pokemon.number.length == 2){
                pokemonNumberWithZeroes = "0" + pokemon.number
            }else{
                pokemonNumberWithZeroes = pokemon.number
            }
            var url: String = "https://www.serebii.net/pokedex-xy/icon/" + pokemonNumberWithZeroes +".png"
            saveFile(getBitmapFromURL(url), "small" + pokemon.number)
            //smallImages.add(Integer.parseInt(pokemon.number)-1, getBitmapFromURL(url))
            uiThread {
            }
        }
    }

    private fun downloadLargeImages(pokemon: Pokemon) {
        doAsync {
            var url: String = "https://www.serebii.net/art/th/"+ pokemon.number + ".png"
            saveFile(getBitmapFromURL(url), "large"+pokemon.number)
            Pokedex.largeImages[Integer.parseInt(pokemon.number)-1] = getBitmapFromURL(url)
            uiThread {
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
