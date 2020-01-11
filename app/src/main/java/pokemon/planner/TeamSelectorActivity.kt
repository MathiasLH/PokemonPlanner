package pokemon.planner

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.adapters.TeamListAdapter
import pokemon.planner.model.GameVersion
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team

class TeamSelectorActivity : AppCompatActivity() {
    private lateinit var teamListLayoutManager: LinearLayoutManager
    private lateinit var teamList: ArrayList<Team>
    private lateinit var tl : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_selector)



        tl = findViewById<RecyclerView>(R.id.teamList)
        teamListLayoutManager = LinearLayoutManager(this)
        tl.layoutManager = teamListLayoutManager

        teamList = intent.getSerializableExtra("listOfTeams") as ArrayList<Team>

        var team = Team("testTeam", GameVersion.RED, true)
        team.addPokemon(Pokedex.pokedex[6], 0)
        team.addPokemon(Pokedex.pokedex[50], 1)
        team.addPokemon(Pokedex.pokedex[100], 2)

        teamList.add(team)
        tl.adapter = TeamListAdapter(this, teamList)
        tl.addOnItemTouchListener(
            SearchResultActivity.RecyclerItemClickListenr(this, tl, object : SearchResultActivity.RecyclerItemClickListenr.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {

                    val selectedItem = teamList.get(position)
                    val intent = Intent(this@TeamSelectorActivity, TeamActivity::class.java)
                    intent.putExtra("team", selectedItem)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View?, position: Int) {

                }

            })
        )
        var teamButton = findViewById<Button>(R.id.teamButton)
        teamButton.setOnClickListener{
            var mBuilder = AlertDialog.Builder(this)
            var mView = layoutInflater.inflate(R.layout.team_dialog, null)
            mBuilder.setTitle("Create new team")
            var versionSpinner = mView.findViewById<Spinner>(R.id.spinner)
            var versions = arrayOf(
                GameVersion.RED.readableName,
                GameVersion.BLUE.readableName,
                GameVersion.YELLOW.readableName,
                GameVersion.GOLD.readableName,
                GameVersion.SILVER.readableName,
                GameVersion.CRYSTAL.readableName,
                GameVersion.RUBY.readableName,
                GameVersion.SAPPHIRE.readableName,
                GameVersion.FIRERED.readableName,
                GameVersion.LEAFGREEN.readableName,
                GameVersion.EMERALD.readableName,
                GameVersion.DIAMOND.readableName,
                GameVersion.PEARL.readableName,
                GameVersion.PLATINUM.readableName,
                GameVersion.HEARTGOLD.readableName,
                GameVersion.SOULSILVER.readableName,
                GameVersion.BLACK.readableName,
                GameVersion.WHITE.readableName,
                GameVersion.BLACK2.readableName,
                GameVersion.WHITE2.readableName
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
                    val version = Pokedex.stringToVersion(versionSpinner.selectedItem.toString())

                    val isMale = mView.findViewById<RadioButton>(R.id.maleRadioButton).isChecked
                    createTeam(name, version, isMale)
                    dialog.dismiss()
                }
            })
            mBuilder.setView(mView)
            mBuilder.show()
        }
    }

    override fun onBackPressed() {
        setResult(420)
        finish()
        super.onBackPressed()

    }

    private fun createTeam(name: String, version: GameVersion, gender: Boolean){
        teamList.add(Team(name, version, gender))
        tl.adapter!!.notifyDataSetChanged()
    }
}
