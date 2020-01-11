package pokemon.planner

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pokemon_search.*
import pokemon.planner.adapters.TypeSelectorAdapter
import pokemon.planner.model.*

class PokemonSearchActivity : AppCompatActivity() {
    private lateinit var insertPoint: LinearLayout
    private lateinit var team: Team
    private lateinit var searchForm: SearchForm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_search)
        team = intent.getSerializableExtra("team") as Team
        searchForm = SearchForm(
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            TYPE.NONE,
            TYPE.NONE,
            "",
            "",
            Move("", -1, TYPE.NONE, ""),
            GameVersion.NONE
        )
        var possibleOptions = ArrayList<String>()
        //possibleOptions.add("newOption")
        possibleOptions.add("Name")
        possibleOptions.add("Number")
        possibleOptions.add("Primary Type")
        possibleOptions.add("Secondary Type")
        if (team.version.generation >= 3) {
            possibleOptions.add("Primary Ability")
            possibleOptions.add("Secondary Ability")
        }
        possibleOptions.add("Move")
        possibleOptions.addAll(Pokedex.getGenSpecificStatNames(team))

        var selectedOptions = ArrayList<String>()

        addButton.setOnClickListener {
            var optionSelectorView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_default, null)
            var adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, possibleOptions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            var spinner = optionSelectorView.findViewById<Spinner>(R.id.optionsSpinner3)
            spinner.adapter = adapter
            insertPoint = findViewById(R.id.insertPoint)
            var transformButton = optionSelectorView.findViewById<Button>(R.id.transformButton)
            transformButton.setOnClickListener {
                transformView(
                    insertPoint.indexOfChild(optionSelectorView),
                    spinner.selectedItem.toString()
                )
            }
            var deleteButton = optionSelectorView.findViewById<ImageButton>(R.id.deleteFilterButton)
            deleteButton.setOnClickListener {
                insertPoint.removeView(optionSelectorView)
            }
            insertPoint.addView(
                optionSelectorView,
                0,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }



        search.setOnClickListener {
            collectUserInput()
            var intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("searchForm", searchForm)
            intent.putExtra("team", team)
            startActivityForResult(intent, 1)
        }
    }

    fun transformView(position: Int, optionType: String) {
        insertPoint.removeViewAt(position)
        var inflatedView: View? = null
        if (optionType == "Name") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_nameoption, null)
        } else if (optionType == "Number") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_numberoption, null)
        } else if (optionType == "Primary Type") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_ptypeoption, null)
            var llm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            var types = Pokedex.getGenSpecificTypes(team)
            types.remove(TYPE.NONE)
            var tsa = TypeSelectorAdapter(this, types, true)
            //tsa.setSelected(searchForm.type1)
            var recyclerView = inflatedView.findViewWithTag<RecyclerView>("ptypeInput")
            recyclerView.layoutManager = llm
            recyclerView.adapter = tsa
        } else if (optionType == "Secondary Type") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_stypeoption, null)
            var llm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            var tsa = TypeSelectorAdapter(this, Pokedex.getGenSpecificTypes(team), true)
            //tsa.setSelected(searchForm.type2)
            var recyclerView = inflatedView.findViewWithTag<RecyclerView>("stypeInput")
            recyclerView.layoutManager = llm
            recyclerView.adapter = tsa
        } else if (optionType == "HP") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_hpoption, null)
        } else if (optionType == "Attack") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_attackoption, null)
        } else if (optionType == "Defense") {
            inflatedView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_defenseoption, null)
        } else if (optionType == "Special") {
            inflatedView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_specialoption, null)
        } else if (optionType == "Sp. Attack") {
            inflatedView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_spattackoption, null)
        } else if (optionType == "Sp. Defense") {
            inflatedView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_spdefenseoption, null)
        } else if (optionType == "Speed") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_speedoption, null)
        } else if (optionType == "Total") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_totaloption, null)
        } else if (optionType == "Primary Ability") {
            inflatedView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_primaryability, null)
            val array = arrayOfNulls<String>(Pokedex.abilities.size)
            ArrayList(Pokedex.abilities.values).toArray(array)
            array.sort()
            var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            var spinner = inflatedView.findViewWithTag<Spinner>("pabilitySpinner")
            spinner.adapter = adapter
        } else if (optionType == "Secondary Ability") {
            inflatedView =
                LayoutInflater.from(this).inflate(R.layout.optionlist_secondaryability, null)
            val array = arrayOfNulls<String>(Pokedex.abilities.size)
            ArrayList(Pokedex.abilities.values).toArray(array)
            array.sort()
            var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            var spinner = inflatedView.findViewWithTag<Spinner>("sabilitySpinner")
            spinner.adapter = adapter
        } else if (optionType == "Move") {
            inflatedView = LayoutInflater.from(this).inflate(R.layout.optionlist_move, null)
            var moveNames = ArrayList<String>()
            for (x in 0..Pokedex.moves.size - 1) {
                if (Pokedex.moves[x] != null) {
                    moveNames.add((Pokedex.moves[x] as Move).name)
                }
            }
            val array = arrayOfNulls<String>(moveNames.size)
            moveNames.toArray(array)
            array.sort()
            var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            var spinner = inflatedView.findViewWithTag<Spinner>("moveSpinner")
            spinner.adapter = adapter
        }
        var deleteButton = inflatedView?.findViewById<ImageButton>(R.id.deleteFilterButton)
        deleteButton?.setOnClickListener {
            insertPoint.removeView(inflatedView)
        }

        insertPoint.addView(
            inflatedView,
            position,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    fun collectUserInput() {
        if (insertPoint.findViewWithTag<EditText>("nameInput") != null) {
            searchForm.name = insertPoint.findViewWithTag<EditText>("nameInput").text.toString()
        }
        if (insertPoint.findViewWithTag<EditText>("numberInput") != null) {
            searchForm.number = insertPoint.findViewWithTag<EditText>("numberInput").text.toString()
        }
        if (insertPoint.findViewWithTag<EditText>("ptypeInput") != null) {
            var view = insertPoint.findViewWithTag<RecyclerView>("ptypeInput")
            searchForm.type1 = (view.adapter as TypeSelectorAdapter).currentType
        }
        if (insertPoint.findViewWithTag<EditText>("stypeInput") != null) {
            var view = insertPoint.findViewWithTag<RecyclerView>("stypeInput")
            searchForm.type2 = (view.adapter as TypeSelectorAdapter).currentType
        }

        if (insertPoint.findViewWithTag<EditText>("hpminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("hpminInput").text.toString().equals("")) {
                searchForm.hpMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("hpminInput").text.toString())
            } else {
                searchForm.hpMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("hpmaxInput").text.toString().equals("")) {
                searchForm.hpMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("hpmaxInput").text.toString())
            } else {
                searchForm.hpMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("attackminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("attackminInput").text.toString().equals("")) {
                searchForm.attackMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("attackminInput").text.toString())
            } else {
                searchForm.attackMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("attackmaxInput").text.toString().equals("")) {
                searchForm.attackMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("attackmaxInput").text.toString())
            } else {
                searchForm.attackMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("defenseminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("defenseminInput").text.toString().equals("")) {
                searchForm.defenseMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("defenseminInput").text.toString())
            } else {
                searchForm.defenseMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("defensemaxInput").text.toString().equals("")) {
                searchForm.defenseMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("defensemaxInput").text.toString())
            } else {
                searchForm.defenseMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("specialminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("specialminInput").text.toString().equals("")) {
                searchForm.specialMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("specialminInput").text.toString())
            } else {
                searchForm.specialMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("specialmaxInput").text.toString().equals("")) {
                searchForm.specialMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("specialmaxInput").text.toString())
            } else {
                searchForm.specialMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("spattackminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("spattackminInput").text.toString().equals("")) {
                searchForm.spAttackMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("spattackminInput").text.toString())
            } else {
                searchForm.spAttackMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("spattackmaxInput").text.toString().equals("")) {
                searchForm.spAttackMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("spattackmaxInput").text.toString())
            } else {
                searchForm.spAttackMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("spdefenseminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("spdefenseminInput").text.toString().equals("")) {
                searchForm.spDefenseMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("spdefenseminInput").text.toString())
            } else {
                searchForm.spDefenseMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("spdefensemaxInput").text.toString().equals("")) {
                searchForm.spDefenseMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("spdefensemaxInput").text.toString())
            } else {
                searchForm.spDefenseMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("speedminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("speedminInput").text.toString().equals("")) {
                searchForm.speedMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("speedminInput").text.toString())
            } else {
                searchForm.speedMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("speedmaxInput").text.toString().equals("")) {
                searchForm.speedMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("speedmaxInput").text.toString())
            } else {
                searchForm.speedMax = 0
            }
        }
        if (insertPoint.findViewWithTag<EditText>("totalminInput") != null) {
            if (!insertPoint.findViewWithTag<EditText>("totalminInput").text.toString().equals("")) {
                searchForm.totalMin =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("totalminInput").text.toString())
            } else {
                searchForm.totalMin = 0
            }
            if (!insertPoint.findViewWithTag<EditText>("totalmaxInput").text.toString().equals("")) {
                searchForm.totalMax =
                    Integer.parseInt(insertPoint.findViewWithTag<EditText>("totalmaxInput").text.toString())
            } else {
                searchForm.totalMax = 0
            }
        }
        if (insertPoint.findViewWithTag<Spinner>("pabilitySpinner") != null) {
            searchForm.ability1 =
                insertPoint.findViewWithTag<Spinner>("pabilitySpinner").selectedItem.toString()
        }
        if (insertPoint.findViewWithTag<Spinner>("sabilitySpinner") != null) {
            searchForm.ability2 =
                insertPoint.findViewWithTag<Spinner>("sabilitySpinner").selectedItem.toString()
        }
        if (insertPoint.findViewWithTag<Spinner>("moveSpinner") != null) {
            searchForm.move =
                Pokedex.moveNameToMove(insertPoint.findViewWithTag<Spinner>("moveSpinner").selectedItem.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {

                finishSearch(data?.getIntExtra("num", 0))
            }
        }

    }

    private fun finishSearch(number: Int?) {
        var resultIntent = Intent()
        setResult(1, resultIntent)
        resultIntent.putExtra("num", number)
        finish()
    }
}