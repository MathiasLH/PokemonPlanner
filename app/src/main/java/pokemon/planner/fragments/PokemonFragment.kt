package pokemon.planner.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_pokemon_searcher.view.*
import kotlinx.android.synthetic.main.teamviewer_pokemon_fragment.view.*
import pokemon.planner.R
import pokemon.planner.TeamActivity
import pokemon.planner.model.Pokemon
import pokemon.planner.model.Pokedex
import pokemon.planner.model.TYPE

class PokemonFragment(private var pokemon: Pokemon): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.teamviewer_pokemon_fragment, container, false)
            if(!pokemon.number.equals("-1")){
                var image = view.findViewById<ImageView>(R.id.pokemonImage)
                image.setImageBitmap(Pokedex.largeImages.get(Integer.parseInt(pokemon.number)-1))

                var nameLabel = view.findViewById<TextView>(R.id.pokemonNameLabel)
                nameLabel.setText(pokemon.name)

                var numberLabel = view.findViewById<TextView>(R.id.numberLabel)
                numberLabel.setText("#" + (pokemon.number).toString())

                val hpBar = view.findViewById<ProgressBar>(R.id.HPbar)
                hpBar.max = 200
                hpBar.progress = pokemon.stats[0]


                val atkBar = view.findViewById<ProgressBar>(R.id.AtkBar)
                atkBar.max= 200
                atkBar.progress = pokemon.stats[1]

                val defBar = view.findViewById<ProgressBar>(R.id.DefBar)
                defBar.max = 200
                defBar.progress = pokemon.stats[2]

                val spAtkBar = view.findViewById<ProgressBar>(R.id.SpAtkBar)
                spAtkBar.max = 200
                spAtkBar.progress = pokemon.stats[3]

                var spDefkBar = view.findViewById<ProgressBar>(R.id.SpDefBar)
                spDefkBar.max = 200
                spDefkBar.progress = pokemon.stats[4]

                var speedBar = view.findViewById<ProgressBar>(R.id.SpeedBar)
                speedBar.max = 200
                speedBar.progress = pokemon.stats[5]

                var totalBar = view.findViewById<ProgressBar>(R.id.TotalBar)
                totalBar.max = 700
                totalBar.progress = pokemon.stats[6]

                var card1 = view.findViewById<CardView>(R.id.card1)
                var card2 = view.findViewById<CardView>(R.id.card2)
                var type1 = view.findViewById<TextView>(R.id.type1Text)
                var type2 = view.findViewById<TextView>(R.id.type2Text)

                if(pokemon.secondaryType.equals(TYPE.NONE)){
                    //only has one type
                    card1.visibility = View.GONE
                    type2.setText(pokemon.primaryType.toString().substring(0, 3))
                    val background = GradientDrawable()
                    background.setStroke(5, Color.GRAY)
                    background.cornerRadius = 8f
                    background.setColor(Color.parseColor(pokemon.primaryType.color))
                    card2.background = background
                }else{
                    //two types
                    card1.visibility = View.VISIBLE
                    card2.visibility = View.VISIBLE
                    type1.setText(pokemon.primaryType.toString().substring(0, 3))
                    type2.setText(pokemon.secondaryType.toString().substring(0, 3))

                    val background1 = GradientDrawable()
                    background1.setStroke(5, Color.GRAY)
                    background1.cornerRadius = 8f
                    background1.setColor(Color.parseColor(pokemon.primaryType.color))
                    card1.background = background1

                    val background2 = GradientDrawable()
                    background2.setStroke(5, Color.GRAY)
                    background2.cornerRadius = 8f
                    background2.setColor(Color.parseColor(pokemon.secondaryType.color))

                    card2.background = background2
                    card1.background = background1
                }
                 var deleteButton = view.findViewById<Button>(R.id.deleteButton)
                deleteButton.setOnClickListener {
                    (activity as TeamActivity).deletepokemon()
                }
            }
        return view
    }


}