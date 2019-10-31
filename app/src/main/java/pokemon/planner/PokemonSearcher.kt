package pokemon.planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pokemon_searcher.*
import pokemon.planner.adapters.StatSelectorAdapter
import pokemon.planner.adapters.TypeSelectorAdapter
import pokemon.planner.model.SearchForm
import pokemon.planner.model.TYPE

class PokemonSearcher : AppCompatActivity() {
    private lateinit var typeSelector1LayoutManager: LinearLayoutManager
    private lateinit var typeSelector2LayoutManager: LinearLayoutManager
    private lateinit var statRangeLayoutManager: LinearLayoutManager
    private lateinit var ssa: StatSelectorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_searcher)
        typeSelector1LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        typeSelector2LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var typeSelector1 = findViewById<RecyclerView>(R.id.type1Selector)
        var typeSelector2 = findViewById<RecyclerView>(R.id.type2Selector)
        typeSelector1.layoutManager = typeSelector1LayoutManager
        typeSelector2.layoutManager = typeSelector2LayoutManager

        val listOfTypes = arrayListOf(TYPE.NONE, TYPE.NORMAL, TYPE.FIGHTING, TYPE.FLYING, TYPE.POISON, TYPE.GROUND, TYPE.ROCK, TYPE.BUG, TYPE.GHOST, TYPE.STEEL, TYPE.FIRE, TYPE.WATER, TYPE.GRASS, TYPE.ELECTRIC, TYPE.PSYCHIC, TYPE.ICE, TYPE.DRAGON, TYPE.DARK, TYPE.FAIRY)
        typeSelector1.layoutManager = typeSelector1LayoutManager
        typeSelector2.layoutManager = typeSelector2LayoutManager
        var tsa  = TypeSelectorAdapter(this, listOfTypes)
        typeSelector1.adapter = tsa
        typeSelector2.adapter = TypeSelectorAdapter(this, listOfTypes)

        val listOfStats = arrayListOf<String>("HP", "Attack", "Defense", "Special Attack", "Special Defense", "Speed", "total")
        statRangeLayoutManager = LinearLayoutManager(this)
        var statRangeSelector = findViewById<RecyclerView>(R.id.statSelector)
        statRangeSelector.layoutManager = statRangeLayoutManager
        ssa = StatSelectorAdapter(this, listOfStats)

        statRangeSelector.adapter = ssa

        searchButton.setOnClickListener {
            var searchForm = createSearchForm()
            var intent : Intent

        }
    }

    private fun createSearchForm(): SearchForm {
        var stats = ssa.getContents()
        var num: Int
        if(!number.text.toString().equals("")){
            num =  Integer.parseInt(number.text.toString())
        }else{
            num = 0
        }
        return SearchForm(name.text.toString(), num, stats.get(0), stats.get(1))

    }
}
