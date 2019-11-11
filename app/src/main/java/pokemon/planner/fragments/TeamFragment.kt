package pokemon.planner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import pokemon.planner.R
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team

class TeamFragment(private var team: Team): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view: View = inflater.inflate(R.layout.teamviewer_team_fragment, container, false)


        var nameText = view.findViewById<TextView>(R.id.teamNameLabel)
        nameText.setText(team.name)

        val image1 = view.findViewById<ImageView>(R.id.pokemon1Image)
        val image2 = view.findViewById<ImageView>(R.id.pokemon2Image)
        val image3 = view.findViewById<ImageView>(R.id.pokemon3Image)
        val image4 = view.findViewById<ImageView>(R.id.pokemon4Image)
        val image5 = view.findViewById<ImageView>(R.id.pokemon5Image)
        val image6 = view.findViewById<ImageView>(R.id.pokemon6Image)
        var images = arrayOf(image1, image2, image3, image4, image5, image6)
        for(x in 0..images.size-1){
            if(!team.pokemonList[x].number.equals("-1")){

                images[x].setImageBitmap(Pokedex.largeImages[Integer.parseInt(team.pokemonList[x].number)-1])
            }
        }
        var hint = view.findViewById<TextView>(R.id.hintLabel)
        hint.setText("Tap a plus-icon to add a Pokémon, or a Pokéball to view a pokemon in your team!")
        return view
    }
}