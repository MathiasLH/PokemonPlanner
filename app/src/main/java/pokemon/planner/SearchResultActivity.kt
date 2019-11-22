package pokemon.planner

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.adapters.SearchResultAdapter
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Pokemon
import pokemon.planner.model.SearchForm
import pokemon.planner.model.TYPE

class SearchResultActivity : AppCompatActivity() {
    private lateinit var searchResultLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        val searchForm = intent.getSerializableExtra("searchForm") as SearchForm
        searchResultLayoutManager = LinearLayoutManager(this)
        var searchResults = findViewById<RecyclerView>(R.id.resultRecyclerView)
        searchResults.layoutManager = searchResultLayoutManager
        var filteredListofPokemon = searchPokemon(searchForm)
        searchResults.adapter = SearchResultAdapter(this, filteredListofPokemon)

        searchResults.addOnItemTouchListener(RecyclerItemClickListenr(this, searchResults, object : RecyclerItemClickListenr.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {

                startPokemonSummartActivity(Integer.parseInt(filteredListofPokemon.get(position).number)-1)
            }

            override fun onItemLongClick(view: View?, position: Int) {

            }

        }))

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

    fun startPokemonSummartActivity(position: Int){
        val intent = Intent(this, PokemonSummaryActivity::class.java)
        intent.putExtra("num", position)
        startActivityForResult(intent, 1)
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

    //https://stackoverflow.com/questions/29424944/recyclerview-itemclicklistener-in-kotlin/51223101#51223101
    class RecyclerItemClickListenr(context: Context, recyclerView: RecyclerView, private val mListener: OnItemClickListener?) : RecyclerView.OnItemTouchListener {

        private val mGestureDetector: GestureDetector

        interface OnItemClickListener {
            fun onItemClick(view: View, position: Int)

            fun onItemLongClick(view: View?, position: Int)
        }

        init {

            mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val childView = recyclerView.findChildViewUnder(e.x, e.y)

                    if (childView != null && mListener != null) {
                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
            val childView = view.findChildViewUnder(e.x, e.y)

            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
            }

            return false
        }

        override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}}

}