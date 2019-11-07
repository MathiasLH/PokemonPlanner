package pokemon.planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pokemon_searcher.*
import pokemon.planner.adapters.SearchResultAdapter
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Pokemon
import pokemon.planner.model.SearchForm
import pokemon.planner.model.TYPE
import java.util.*
import kotlin.collections.ArrayList

class SearchResultActivity : AppCompatActivity() {
    private lateinit var searchResultLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        val searchForm = intent.getSerializableExtra("searchForm") as SearchForm
        searchResultLayoutManager = LinearLayoutManager(this)
        var searchResults = findViewById<RecyclerView>(R.id.resultRecyclerView)
        searchResults.layoutManager = searchResultLayoutManager




        searchResults.adapter = SearchResultAdapter(this, searchPokemon(searchForm))

    }

    fun searchPokemon(searchForm: SearchForm): ArrayList<Pokemon>{
        var pokemonList = ArrayList<Pokemon>()
        pokemonList.addAll(Pokedex.pokedex)

        var pokemonList2: List<Pokemon> = pokemonList

        //Below are all the filter conditions.
        //It checks whether or not a field is filled in, and only then applies each filter.
        //It also checks for stat ranges, if there is a minimum, a maximum or both, and applies the
        //correct filter for each situation.

        //Name
        if(!searchForm.name.equals("")){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.name.toLowerCase().contains(searchForm.name.toLowerCase()) }
        }

        //Number
        if(!searchForm.number.equals("")){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.number.equals(searchForm.number) }
        }

        //type1
        if(!searchForm.type1.equals(TYPE.NONE)){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.primaryType.equals(searchForm.type1) }
        }

        //type2
        if(!searchForm.type2.equals(TYPE.NONE)){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.secondaryType.equals(searchForm.type2) }
        }

        //stats
        for(x in 0..6) {
            if (searchForm.minStats[x] > 0) {
                pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[x] > searchForm.minStats[x] }
            }

            if (searchForm.maxStats[x] > 0) {
                pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[x] < searchForm.maxStats[x] }
            }
        }
        return ArrayList(pokemonList2)
    }
}