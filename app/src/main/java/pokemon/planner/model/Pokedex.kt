package pokemon.planner.model

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.net.HttpURLConnection
import android.content.Context.MODE_PRIVATE
import android.util.Log
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


object Pokedex{
    var pokedexSize = 151
    var pokedex = arrayListOf<Pokemon>()
    var smallImages = Array<Bitmap?>(pokedexSize) {null}

    var largeImages = Array<Bitmap?>(pokedexSize) {null}

    fun addPokemonToPokedex(pokemon: Pokemon){

        this.pokedex.add(pokemon)
    }




}