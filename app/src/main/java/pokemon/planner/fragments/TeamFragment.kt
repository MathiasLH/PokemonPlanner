package pokemon.planner.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.R
import pokemon.planner.adapters.StatSummaryAdapter
import pokemon.planner.adapters.TypeSelectorAdapter
import pokemon.planner.model.Pokedex
import pokemon.planner.model.TYPE
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

        var versionText = view.findViewById<TextView>(R.id.versionLabel)
        versionText.setText(team.version.readableName)

        var generationText = view.findViewById<TextView>(R.id.generationLabel)
        generationText.setText("Generation " + team.version.generation.toString())

        var typesInTeam = ArrayList<TYPE>()

        for(pokemon in team.pokemonList){
            if(pokemon.typePrimary != TYPE.NONE && !typesInTeam.contains(pokemon.typePrimary)){
                typesInTeam.add(pokemon.typePrimary)
            }
            if(pokemon.typeSecondary != TYPE.NONE && !typesInTeam.contains(pokemon.typeSecondary)){
                typesInTeam.add(pokemon.typeSecondary)
            }
        }

        var typesInTeamLLM = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        var typesInTeamAdapter = TypeSelectorAdapter(activity as Context, typesInTeam, false)
        var typesInTeamRecycler = view.findViewById<RecyclerView>(R.id.typesInTeamRecycler)
        typesInTeamRecycler.layoutManager = typesInTeamLLM
        typesInTeamRecycler.adapter = typesInTeamAdapter

        var statList = view.findViewById<RecyclerView>(R.id.statList)
        var llm = LinearLayoutManager(activity)
        var statValues : Array<Int>
        if(team.version.generation == 1){
            statValues = Array<Int>(6) { 0 }
        }else{
            statValues = Array<Int>(7) { 0 }
        }
        for(pokemon in team.pokemonList){
            if(team.version.generation == 1){
                for(x in 0..pokemon.gen1Stats.size-1){
                    statValues[x] += pokemon.gen1Stats[x]
                }
            }else{
                for(x in 0..pokemon.stats.size-1){
                    statValues[x] += pokemon.stats[x]

                }
            }
        }

        statList.layoutManager = llm
        statList.adapter = StatSummaryAdapter(activity as Context, team, Pokedex.getGenSpecificStatNames(team), statValues, team.size())


        /*var hint = view.findViewById<TextView>(R.id.hintLabel)
        if(team.size() == 0){
            hint.setText("Press a Pokéball to add a Pokémon to your team!")
        }else{
            hint.setText("Tap an open Pokéball to view your Pokémon, or a closed to add another!")
        }*/
        return view
    }
}