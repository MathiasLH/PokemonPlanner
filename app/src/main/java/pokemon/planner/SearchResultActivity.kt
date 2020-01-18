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
        var filteredListofPokemon = Pokedex.searchPokemon(searchForm, team)
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