package pokemon.planner

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_team.*
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team

class TeamActivity : AppCompatActivity() {
    private lateinit var team: Team
    private var lastPressedBall: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        val intent = getIntent()
        team = intent.getSerializableExtra("team") as Team
        teamName.text = team.name
        createPokeballBar(team)
        var listOfPokemonButtons = arrayOf(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6)

        for (x in 0..listOfPokemonButtons.size-1){
            listOfPokemonButtons[x].tag = x.toString()
            listOfPokemonButtons[x].setOnClickListener {
                lastPressedBall = Integer.parseInt(listOfPokemonButtons[x].tag as String)
                val intent = Intent(this, PokemonSearcher::class.java)
                startActivityForResult(intent, 1)
                //launch pokemon activity
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {
                team.pokemonList[lastPressedBall] = Pokedex.pokedex[data!!.getIntExtra("num", 0)]
                createPokeballBar(team)
            }
        }

    }

    fun createPokeballBar(team: Team){

        if(team.pokemonList[0] != null){
            pokemon1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(team.pokemonList[1] != null){
            pokemon2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(team.pokemonList[2] != null){
            pokemon3.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon3.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(team.pokemonList[3] != null){
            pokemon4.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon4.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(team.pokemonList[4] != null){
            pokemon5.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon5.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(team.pokemonList[5] != null){
            pokemon6.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon6.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }

    }
}
