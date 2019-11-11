package pokemon.planner

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import pokemon.planner.adapters.TeamListAdapter
import pokemon.planner.model.Team
import android.view.ViewGroup
import com.crashlytics.android.Crashlytics
import pokemon.planner.io.PokedexReader
import pokemon.planner.model.Pokedex


class MainActivity : AppCompatActivity() {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "dolphin :^)"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val pokedexreader = PokedexReader(this)
        pokedexreader.readFile()
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        if(sharedPref.getBoolean("init", true)){
            editor.putBoolean("init", false)
            editor.apply()
            pokedexreader.downloadImages()
        }else{


        }

        val teamList = arrayListOf<Team>()
        val tl = findViewById<ListView>(R.id.teamList)


        var team = Team("testTeam")
        team.addPokemon(Pokedex.pokedex[6], 0)
        team.addPokemon(Pokedex.pokedex[50], 1)
        team.addPokemon(Pokedex.pokedex[100], 2)

        teamList.add(team)

        val teamListAdapter = TeamListAdapter(teamList, this)
        tl.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            pokedexreader.loadImages()
            val SelectedItem = parent.getItemAtPosition(position) as Team
            val intent = Intent(this, TeamActivity::class.java)
            intent.putExtra("team", SelectedItem)
            startActivity(intent)
        }
        tl.adapter = teamListAdapter

        val builder = AlertDialog.Builder(this@MainActivity)
        var teamNameText: EditText?
        with(builder){
            setTitle("Choose your team name")
            teamNameText = EditText(context)
            teamNameText!!.hint="name"
            teamNameText!!.inputType = InputType.TYPE_CLASS_TEXT

            setPositiveButton("OK"){
                dialog, whichButton ->
                val newTeam = Team(teamNameText!!.text.toString())
                teamList.add(newTeam)
                intent.putExtra("team", newTeam)
                startActivity(intent)
            }
        }

        teamButton.setOnClickListener{
            val dialog = builder.create()
            if (teamNameText!!.getParent() != null) {
                (teamNameText!!.getParent() as ViewGroup).removeView(teamNameText)

            }
            dialog.setView(teamNameText)
            teamNameText!!.setText("")
            dialog.show()
        }

    }

}
