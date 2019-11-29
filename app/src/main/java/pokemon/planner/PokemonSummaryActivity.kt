package pokemon.planner

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_pokemon_summary.*
import org.jetbrains.anko.find
import pokemon.planner.fragments.PokemonFragmentOld
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Pokemon
import pokemon.planner.model.TYPE
import pokemon.planner.model.Team

class PokemonSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_summary)
        val number = intent.getIntExtra("num", 0)
        val team = intent.getSerializableExtra("team") as Team
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = PokemonFragmentOld(Pokedex.pokedex.get(number), team, this)
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
