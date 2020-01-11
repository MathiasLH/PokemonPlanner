package pokemon.planner

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity
import pokemon.planner.io.PokedexReader
import pokemon.planner.model.GameVersion
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team


class MainActivity : AppCompatActivity() {
    private lateinit var teamListLayoutManager: LinearLayoutManager
    private lateinit var teamList: ArrayList<Team>
    private lateinit var tl : RecyclerView
    private lateinit var auth: FirebaseAuth
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "dolphin :^)"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Start loading all the dataz
        val pokedexreader = PokedexReader(this)
        pokedexreader.readPokedexData()
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        if(sharedPref.getBoolean("init", true)){
            editor.putBoolean("init", false)
            editor.apply()
            pokedexreader.downloadImages()
        }else{
            pokedexreader.loadImages()
        }

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //user already logged in
            getUserTeamsAndStartTeamActivity()
        }

        var registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterAccountActivity::class.java)
            startActivityForResult(intent, 0)
        }

        var loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(findViewById<EditText>(R.id.emailText).text.toString(), findViewById<EditText>(R.id.passwordText).text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                       getUserTeamsAndStartTeamActivity()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    private fun getUserTeamsAndStartTeamActivity() {
        var team = Team("testTeam", GameVersion.RED, true)
        team.addPokemon(Pokedex.pokedex[6], 0)
        team.addPokemon(Pokedex.pokedex[50], 1)
        team.addPokemon(Pokedex.pokedex[100], 2)

        var listOfTeams = ArrayList<Team>()
        listOfTeams.add(team)
        val intent = Intent(this, TeamSelectorActivity::class.java)
        intent.putExtra("listOfTeams", listOfTeams)
        startActivityForResult(intent, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndRemoveTask()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(auth.currentUser != null && resultCode != 0){
                getUserTeamsAndStartTeamActivity()
        }
        if(resultCode == 420){
            finishAndRemoveTask()
        }
    }
}

