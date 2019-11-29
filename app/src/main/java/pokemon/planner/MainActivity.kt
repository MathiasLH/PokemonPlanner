package pokemon.planner

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import pokemon.planner.adapters.TeamListAdapter
import pokemon.planner.io.PokedexReader
import pokemon.planner.model.GameVersion
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team
import pokemon.planner.model.GameVersion.*


class MainActivity : AppCompatActivity() {
    private lateinit var teamListAdapter: TeamListAdapter
    private lateinit var teamListLayoutManager: LinearLayoutManager
    private lateinit var teamList: ArrayList<Team>
    private lateinit var tl : RecyclerView
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "dolphin :^)"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val pokedexreader = PokedexReader(this)
        pokedexreader.readPokedexData()
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        if(sharedPref.getBoolean("init", true)){
            editor.putBoolean("init", false)
            editor.apply()
            pokedexreader.downloadImages()
        }else{


        }

        teamList = arrayListOf<Team>()
        tl = findViewById<RecyclerView>(R.id.teamList)
        teamListLayoutManager = LinearLayoutManager(this)
        tl.layoutManager = teamListLayoutManager

        var team = Team("testTeam", RED, true)
        team.addPokemon(Pokedex.pokedex[6], 0)
        team.addPokemon(Pokedex.pokedex[50], 1)
        team.addPokemon(Pokedex.pokedex[100], 2)

        teamList.add(team)
        /*tl.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            pokedexreader.loadImages()
            val SelectedItem = parent.getItemAtPosition(position) as Team
            val intent = Intent(this, TeamActivity::class.java)
            intent.putExtra("team", SelectedItem)
            startActivity(intent)
        }*/
        tl.adapter = TeamListAdapter(this, teamList)

        tl.addOnItemTouchListener(
            SearchResultActivity.RecyclerItemClickListenr(this, tl, object : SearchResultActivity.RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        pokedexreader.loadImages()
                        val selectedItem = teamList.get(position)
                        val intent = Intent(this@MainActivity, TeamActivity::class.java)
                        intent.putExtra("team", selectedItem)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }

                })
        )

        teamButton.setOnClickListener{
            var mBuilder = AlertDialog.Builder(this)
            var mView = layoutInflater.inflate(R.layout.team_dialog, null)
            mBuilder.setTitle("Create new team")
            var versionSpinner = mView.findViewById<Spinner>(R.id.spinner)
            var versions = arrayOf(
                RED.readableName,
                BLUE.readableName,
                YELLOW.readableName,
                GOLD.readableName,
                SILVER.readableName,
                CRYSTAL.readableName,
                RUBY.readableName,
                SAPPHIRE.readableName,
                FIRERED.readableName,
                LEAFGREEN.readableName,
                EMERALD.readableName,
                DIAMOND.readableName,
                PEARL.readableName,
                PLATINUM.readableName,
                HEARTGOLD.readableName,
                SOULSILVER.readableName,
                BLACK.readableName,
                WHITE.readableName,
                BLACK2.readableName,
                WHITE2.readableName
            )
            versionSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //var grg = mView.findViewById<RadioGroup>(R.id.genderRadioGroup)
                    var maleButton = mView.findViewById<RadioButton>(R.id.maleRadioButton)
                    var femaleButton = mView.findViewById<RadioButton>(R.id.femaleRadioButton)

                    if(versions.get(position).equals("Pokémon Red") || versions.get(position).equals("Pokémon Blue") || versions.get(position).equals("Pokémon Yellow") || versions.get(position).equals("Pokémon Gold") || versions.get(position).equals("Pokémon Silver")){
                        maleButton.isChecked = true
                        maleButton.isEnabled = false
                        femaleButton.isEnabled = false
                    }else{
                        maleButton.isEnabled = true
                        femaleButton.isEnabled = true
                    }
                }
            }

            var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, versions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            versionSpinner.adapter = adapter
            mBuilder.setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    val name = mView.findViewById<TextView>(R.id.nameText).text.toString()
                    val version = stringToVersion(versionSpinner.selectedItem.toString())

                    val isMale = mView.findViewById<RadioButton>(R.id.maleRadioButton).isChecked
                    createTeam(name, version, isMale)
                    dialog.dismiss()
                }
            })
            mBuilder.setView(mView)
            mBuilder.show()
        }
    }

    private fun createTeam(name: String, version: GameVersion, gender: Boolean){
        teamList.add(Team(name, version, gender))
        tl.adapter!!.notifyDataSetChanged()
    }

    private fun stringToVersion(input: String): GameVersion {
        when(input){
            "Pokémon Red" -> return RED
            "Pokémon Blue" -> return BLUE
            "Pokémon Yellow" -> return YELLOW
            "Pokémon Gold" -> return GOLD
            "Pokémon Silver" -> return SILVER
            "Pokémon Crystal" -> return CRYSTAL
            "Pokémon Ruby" -> return RUBY
            "Pokémon Sapphire" -> return SAPPHIRE
            "Pokémon Fire Red" -> return FIRERED
            "Pokémon Leaf Green" -> return LEAFGREEN
            "Pokémon Emerald" -> return EMERALD
            "Pokémon Diamond" -> return DIAMOND
            "Pokémon Pearl" -> return PEARL
            "Pokémon Platinum" -> return PLATINUM
            "Pokémon Heart Gold" -> return HEARTGOLD
            "Pokémon Soul Silver" -> return SOULSILVER
            "Pokémon Black" -> return BLACK
            "Pokémon White" -> return WHITE
            "Pokémon Black 2" -> return BLACK2
            "Pokémon White 2" -> return WHITE2
            else -> return NONE
        }
    }

}
