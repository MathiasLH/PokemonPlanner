package pokemon.planner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pokemon_searcher.*
import pokemon.planner.adapters.StatSelectorAdapter
import pokemon.planner.adapters.TypeSelectorAdapter
import pokemon.planner.model.*

class PokemonSearcher : AppCompatActivity() {
    private lateinit var typeSelector1LayoutManager: LinearLayoutManager
    private lateinit var typeSelector2LayoutManager: LinearLayoutManager
    private lateinit var statRangeLayoutManager: LinearLayoutManager
    private lateinit var ssa: StatSelectorAdapter
    private lateinit var team: Team


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_searcher)
        team = intent.getSerializableExtra("team") as Team
        typeSelector1LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        typeSelector2LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var typeSelector1 = findViewById<RecyclerView>(R.id.type1Selector)
        var typeSelector2 = findViewById<RecyclerView>(R.id.type2Selector)
        typeSelector1.layoutManager = typeSelector1LayoutManager
        typeSelector2.layoutManager = typeSelector2LayoutManager
        var tsa  = TypeSelectorAdapter(this, Pokedex.getGenSpecificTypes(team), true)
        typeSelector1.adapter = tsa
        var tsa2 = TypeSelectorAdapter(this, Pokedex.getGenSpecificTypes(team), true)
        typeSelector2.adapter = tsa2


        statRangeLayoutManager = LinearLayoutManager(this)
        var statRangeSelector = findViewById<RecyclerView>(R.id.statSelector)
        statRangeSelector.layoutManager = statRangeLayoutManager
        ssa = StatSelectorAdapter(this, Pokedex.getGenSpecificStatNames(team))

        statRangeSelector.adapter = ssa

        searchButton.setOnClickListener {

            var searchForm = createSearchForm(tsa.currentType, tsa2.currentType)
            var intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("searchForm", searchForm)
            intent.putExtra("team", team)
            startActivityForResult(intent, 1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {

                finishSearch(data?.getIntExtra("num", 0))
            }
        }

    }

    private fun finishSearch(number: Int?) {
        var resultIntent =  Intent()
        setResult(1,resultIntent)
        resultIntent.putExtra("num", number)
        finish()
    }

    private fun createSearchForm(type1: TYPE, type2: TYPE): SearchForm {
        var stats = ssa.getContents()

        return SearchForm("", "", 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,TYPE.NONE,TYPE.NONE,"", "", Pokedex.moves[1] as Move, GameVersion.NONE)

    }
}
