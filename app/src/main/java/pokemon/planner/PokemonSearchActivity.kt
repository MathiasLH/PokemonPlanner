package pokemon.planner

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pokemon_search.*
import pokemon.planner.adapters.OptionsAdapter
import pokemon.planner.model.*

class PokemonSearchActivity : AppCompatActivity() {
    private lateinit var optionsLayoutManager: LinearLayoutManager
    private lateinit var optionsAdapter: OptionsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_search)
        var team = intent.getSerializableExtra("team") as Team
        var possibleOptions = ArrayList<String>()
        //possibleOptions.add("newOption")
        possibleOptions.add("Name")
        possibleOptions.add("Number")
        possibleOptions.add("Primary Type")
        possibleOptions.add("Secondary Type")
        if(team.version.generation >= 3){
            possibleOptions.add("Primary Ability")
            possibleOptions.add("Secondary Ability")
        }
        possibleOptions.add("Move")
        possibleOptions.addAll(Pokedex.getGenSpecificStatNames(team))

        var selectedOptions = ArrayList<String>()



        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, possibleOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spinner = findViewById<Spinner>(R.id.optionsSpinner)
        spinner.adapter = adapter

        var optionsList = findViewById<RecyclerView>(R.id.optionsRecycler)
        optionsLayoutManager = LinearLayoutManager(this)
        optionsList.layoutManager = optionsLayoutManager
        var searchForm = SearchForm("", "", 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,TYPE.NONE,TYPE.NONE,"","", Move("", -1, TYPE.NONE,""),GameVersion.NONE)
        optionsAdapter = OptionsAdapter(this, selectedOptions, team, optionsList, searchForm)
        optionsList.adapter = optionsAdapter

        addButton.setOnClickListener {
            selectedOptions.add(spinner.selectedItem.toString())
            //optionsAdapter.notifyDataSetChanged()
            searchForm = optionsAdapter.returnUserInput()
            optionsAdapter = OptionsAdapter(this, selectedOptions, team, optionsList, searchForm)
            optionsList.adapter = optionsAdapter
            possibleOptions.remove(spinner.selectedItem.toString())
            adapter.notifyDataSetChanged()

        }

        search.setOnClickListener {
            var sf = optionsAdapter.returnUserInput()
            var intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("searchForm", sf)
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
}

/*if(options.get(position).equals("newOption")){
    var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_newoption, holder.optionContainer as ViewGroup, false)
    var spinner = testView.findViewWithTag<Spinner>("optionsSpinnerInput")
    var addNewOptionButton = testView.findViewWithTag<Button>("addButton")
    addNewOptionButton.setOnClickListener {
        options.add(spinner.getSelectedItem().toString())
        this.notifyDataSetChanged()
    }
    holder.optionContainer.addView(testView)
}else*/