package pokemon.planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_team.*
import pokemon.planner.model.Team

class TeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        val intent = getIntent()
        val team: Team = intent.getSerializableExtra("team") as Team
        teamName.text = team.name
        createPokeballBar(team)
        var listOfPokemonButtons = arrayOf(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6)

        for (imageButton in listOfPokemonButtons){
            imageButton.setOnClickListener {
                val intent = Intent(this, PokemonSearcher::class.java)
                startActivity(intent)
                //launch pokemon activity
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
