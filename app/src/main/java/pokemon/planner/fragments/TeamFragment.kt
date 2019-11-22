package pokemon.planner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
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


        var hint = view.findViewById<TextView>(R.id.hintLabel)
        if(team.size() == 0){
            hint.setText("Press a Pokéball to add a Pokémon to your team!")
        }else{
            hint.setText("Tap an open Pokéball to view your Pokémon, or a closed to add another!")
        }
        return view
    }
}