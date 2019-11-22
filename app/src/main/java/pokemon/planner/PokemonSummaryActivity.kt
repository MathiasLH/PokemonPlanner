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
import pokemon.planner.model.Pokedex
import pokemon.planner.model.TYPE

class PokemonSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_summary)
        val number = intent.getIntExtra("num", 0)
        var image = findViewById<ImageView>(R.id.pokemonImage)
        image.setImageBitmap(Pokedex.largeImages.get(number))
        var name = findViewById<TextView>(R.id.pokemonNameLabel)
        name.setText(Pokedex.pokedex.get(number).name)
        var numberText = find<TextView>(R.id.numberLabel)
        numberText.setText("#" + (number+1).toString())

        var hpBar = findViewById<ProgressBar>(R.id.HPbar)
        hpBar.max = 200
        hpBar.progress = Pokedex.pokedex[number].stats[0]

        var atkBar = findViewById<ProgressBar>(R.id.AtkBar)
        atkBar.max= 200
        atkBar.progress = Pokedex.pokedex[number].stats[1]

        var defBar = findViewById<ProgressBar>(R.id.DefBar)
        defBar.max = 200
        defBar.progress = Pokedex.pokedex[number].stats[2]

        var spAtkBar = findViewById<ProgressBar>(R.id.SpAtkBar)
        spAtkBar.max = 200
        spAtkBar.progress = Pokedex.pokedex[number].stats[3]

        var spDefkBar = findViewById<ProgressBar>(R.id.SpDefBar)
        spDefkBar.max = 200
        spDefkBar.progress = Pokedex.pokedex[number].stats[4]

        var speedBar = findViewById<ProgressBar>(R.id.SpeedBar)
        speedBar.max = 200
        speedBar.progress = Pokedex.pokedex[number].stats[5]

        var totalBar = findViewById<ProgressBar>(R.id.TotalBar)
        totalBar.max = 700
        totalBar.progress = Pokedex.pokedex[number].stats[6]

        var card1 = findViewById<CardView>(R.id.card1)
        var card2 = findViewById<CardView>(R.id.card2)
        var type1 = findViewById<TextView>(R.id.type1Text)
        var type2 = findViewById<TextView>(R.id.type2Text)

        if(Pokedex.pokedex.get(number).secondaryType.equals(TYPE.NONE)){
            //only has one type
            card1.visibility = View.GONE
            type2.setText(Pokedex.pokedex.get(number).primaryType.toString().substring(0, 3))
            val background = GradientDrawable()
            background.setStroke(5, Color.GRAY)
            background.cornerRadius = 8f
            background.setColor(Color.parseColor(Pokedex.pokedex.get(number).primaryType.color))
            card2.background = background
        }else{
            //two types
            card1.visibility = View.VISIBLE
            card2.visibility = View.VISIBLE
            type1.setText(Pokedex.pokedex.get(number).primaryType.toString().substring(0, 3))
            type2.setText(Pokedex.pokedex.get(number).secondaryType.toString().substring(0, 3))

            val background1 = GradientDrawable()
            background1.setStroke(5, Color.GRAY)
            background1.cornerRadius = 8f
            background1.setColor(Color.parseColor(Pokedex.pokedex.get(number).primaryType.color))
            card1.background = background1

            val background2 = GradientDrawable()
            background2.setStroke(5, Color.GRAY)
            background2.cornerRadius = 8f
            background2.setColor(Color.parseColor(Pokedex.pokedex.get(number).secondaryType.color))

            card2.background = background2
            card1.background = background1
        }

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
