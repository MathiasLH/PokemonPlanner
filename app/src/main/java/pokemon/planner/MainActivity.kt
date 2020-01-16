package pokemon.planner

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
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

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //user already logged in
            //Pokedex.getTeamsFromDatabase()
            val intent = Intent(this, TeamSelectorActivity::class.java)
            startActivityForResult(intent, 0)
        }else{
            emailText.visibility = View.VISIBLE
            passwordText.visibility = View.VISIBLE
            loginButton.visibility = View.VISIBLE
            registerButton.visibility = View.VISIBLE
        }



        var registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterAccountActivity::class.java)
            startActivityForResult(intent, 0)
        }

        var loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            emailText.visibility = View.INVISIBLE
            passwordText.visibility = View.INVISIBLE
            loginButton.visibility = View.INVISIBLE
            registerButton.visibility = View.INVISIBLE
            loadingIcon.visibility = View.VISIBLE
            loadingText.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(findViewById<EditText>(R.id.emailText).text.toString(), findViewById<EditText>(R.id.passwordText).text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this, TeamSelectorActivity::class.java)
                        startActivityForResult(intent, 0)
                    } else {
                        // If sign in fails, display a message to the user.
                        emailText.visibility = View.VISIBLE
                        passwordText.visibility = View.VISIBLE
                        loginButton.visibility = View.VISIBLE
                        registerButton.visibility = View.VISIBLE
                        loadingIcon.visibility = View.INVISIBLE
                        loadingText.visibility = View.INVISIBLE
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()


                    }
                }
        }
    }

    override fun onStart() {
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
        super.onStart()
    }





    override fun onBackPressed() {
        super.onBackPressed()
        finishAndRemoveTask()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(auth.currentUser != null && resultCode != 0){
            val intent = Intent(this, TeamSelectorActivity::class.java)
            startActivityForResult(intent, 0)
        }
        if(resultCode == 420){
            finishAndRemoveTask()
        }
    }
}

