package pokemon.planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import pokemon.planner.adapters.TeamListAdapter
import pokemon.planner.model.Pokemon
import pokemon.planner.model.TYPE
import pokemon.planner.model.Team
import android.view.ViewGroup


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pokedexreader = PokedexReader(this)
        pokedexreader.readFile()
        val teamList = arrayListOf<Team>()
        val tl = findViewById<ListView>(R.id.teamList)

        val teamListAdapter = TeamListAdapter(teamList, this)
        tl.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
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

    private fun createNewTeam(name: String) {

    }
}
