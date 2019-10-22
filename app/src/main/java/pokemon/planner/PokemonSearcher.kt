package pokemon.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pokemon_searcher.*
import pokemon.planner.adapters.TypeSelectorAdapter
import pokemon.planner.model.TYPE

class PokemonSearcher : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var linearLayoutManager2: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_searcher)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        linearLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var rv = findViewById<RecyclerView>(R.id.type1Selector)
        var rv2 = findViewById<RecyclerView>(R.id.type2Selector)
        rv.layoutManager = linearLayoutManager
        rv2.layoutManager = linearLayoutManager2

        val listOfTypes = arrayListOf(TYPE.NONE, TYPE.NORMAL, TYPE.FIGHTING, TYPE.FLYING, TYPE.POISON, TYPE.GROUND, TYPE.ROCK, TYPE.BUG, TYPE.GHOST, TYPE.STEEL, TYPE.FIRE, TYPE.WATER, TYPE.GRASS, TYPE.ELECTRIC, TYPE.PSYCHIC, TYPE.ICE, TYPE.DRAGON, TYPE.DARK, TYPE.FAIRY)
        rv.layoutManager = linearLayoutManager
        rv2.layoutManager = linearLayoutManager2
        rv.adapter = TypeSelectorAdapter(this, listOfTypes)
        rv2.adapter = TypeSelectorAdapter(this, listOfTypes)
    }
}
