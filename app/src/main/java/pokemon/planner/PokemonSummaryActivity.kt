package pokemon.planner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pokemon_summary.*
import pokemon.planner.fragments.PokemonFragment
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team

class PokemonSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_summary)
        val number = intent.getIntExtra("num", 0)
        val team = intent.getSerializableExtra("team") as Team
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = PokemonFragment(Pokedex.pokedex.get(number), team, this, false)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()

        addButton.setOnClickListener {
            var resultIntent =  Intent()
            setResult(1,resultIntent)
            resultIntent.putExtra("num", number)
            finish()
        }
    }



    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityReenter(requestCode, data)
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                println("yo")
            }
        }
    }*/

}
