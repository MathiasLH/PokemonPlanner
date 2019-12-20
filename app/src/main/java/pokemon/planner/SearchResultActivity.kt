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
import pokemon.planner.model.*

class SearchResultActivity : AppCompatActivity() {
    private lateinit var searchResultLayoutManager: LinearLayoutManager
    private lateinit var team: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        val searchForm = intent.getSerializableExtra("searchForm") as SearchForm
        team = intent.getSerializableExtra("team") as Team
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
        intent.putExtra("team", team)
        startActivityForResult(intent, 1)
    }



    fun searchPokemon(searchForm: SearchForm): ArrayList<Pokemon>{
        var pokemonList = Pokedex.getPokemonList(team.version.pokemonList)

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

        if(searchForm.hpMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[0] >= searchForm.hpMin }
        }

        if(searchForm.hpMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[0] <= searchForm.hpMax }
        }

        if(searchForm.attackMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[1] >= searchForm.attackMin }
        }

        if(searchForm.attackMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[1] <= searchForm.attackMax }
        }
        if(searchForm.defenseMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[2] >= searchForm.defenseMin }
        }

        if(searchForm.defenseMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[2] <= searchForm.defenseMax }
        }
        if(searchForm.specialMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.gen1Stats[3] >= searchForm.specialMin }
        }

        if(searchForm.specialMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.gen1Stats[3] <= searchForm.specialMax }
        }

        if(searchForm.spAttackMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[3] >= searchForm.hpMin }
        }

        if(searchForm.spAttackMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[3] <= searchForm.hpMax }
        }

        if(searchForm.spDefenseMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[4] >= searchForm.spDefenseMin }
        }

        if(searchForm.spDefenseMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[4] <= searchForm.spDefenseMax }
        }
        if(searchForm.speedMin != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[5] >= searchForm.hpMin }
        }

        if(searchForm.speedMax != 0){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.stats[5] <= searchForm.hpMax }
        }
        if(!searchForm.ability1.equals("")){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.ability1.equals(searchForm.ability1) }
        }
        if(!searchForm.ability2.equals("")){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.ability2.equals(searchForm.ability2) }
        }
        if(searchForm.move.id != -1){
            pokemonList2 = pokemonList2.filter { pokemon -> pokemon.learnSets[team.version.versionGroupId].isLearnable(searchForm.move) }
            println("yo")
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