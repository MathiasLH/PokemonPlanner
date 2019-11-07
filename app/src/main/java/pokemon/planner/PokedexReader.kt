package pokemon.planner

import android.app.Application
import android.content.Context
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import pokemon.planner.model.Pokemon
import pokemon.planner.model.TYPE
import pokemon.planner.model.Pokedex
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class PokedexReader(private val context: Context) {
    fun readFile() {

        context.assets.open("pokedex.csv").bufferedReader().use {
            it.readLine()
            for(i in 0..151){
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
}
