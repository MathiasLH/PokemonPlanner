package pokemon.planner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.R
import pokemon.planner.model.Pokemon
import pokemon.planner.model.Team
import pokemon.planner.model.Pokedex

class obtainMethodAdapter(private val context: Context, private val pokemon: Pokemon, private val team: Team): RecyclerView.Adapter<obtainMethodAdapter.ViewHolder>() {
    private lateinit var nestedLayoutManger : LinearLayoutManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.acquisition, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberName.text = ("#" + pokemon.number + " " + pokemon.name + getObtainMethod())
        //holder.obtain.text = getObtainMethod()
        holder.image.setImageBitmap(Pokedex.smallImages[Integer.parseInt(pokemon.number)-1])
        //if(Pokedex.pokemonAvailability[Integer.parseInt(pokemon.number)-1][team.version.pokemonList].equals("E")){
        nestedLayoutManger = LinearLayoutManager(context)
        holder.nestedRecycler.layoutManager = nestedLayoutManger
        if(pokemon.evolvesFrom.size > 0){
            holder.nestedRecycler.adapter = obtainMethodAdapter(context, Pokedex.pokedex.get(pokemon.evolvesFrom.get(0)-1), team)
            if(pokemon.minLevel == -1){
                holder.numberName.text = ("#" + pokemon.number + " " + pokemon.name + " evolves with " + pokemon.evolveCriteria + " from")
            }else{
                holder.numberName.text = ("#" + pokemon.number + " " + pokemon.name + " evolves at " + pokemon.evolveCriteria + " from")
            }
        }else if(Pokedex.pokemonAvailability[Integer.parseInt(pokemon.number)-1][team.version.pokemonList].equals("C")){
            var encounters = Pokedex.encounters[team.version.versionId][Integer.parseInt(pokemon.number)-1]
            var filteredLocations = ArrayList<String>()
            for(encounter in encounters){
                if(!filteredLocations.contains(Pokedex.locationNames[encounter.locationId]!!.replace('-', ' '))){
                    filteredLocations.add(Pokedex.locationNames[encounter.locationId]!!.replace('-', ' '))
                }
            }
            holder.nestedRecycler.adapter = locationListAdapter(context, filteredLocations)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val numberName = view.findViewById<TextView>(R.id.numberNameLabel)
        //val obtain = view.findViewById<TextView>(R.id.obtainedByLabel)
        val nestedRecycler = view.findViewById<RecyclerView>(R.id.obtainRecyclerView)
        val criteria = view.findViewById<TextView>(R.id.criteriaLabel)
    }

    private fun getObtainMethod(): String {
        var availability = Pokedex.pokemonAvailability[Integer.parseInt(pokemon.number)-1][team.version.pokemonList]
        if(availability.equals("C")){
            return " is caught in the wild"
        }else if(availability.equals("R")){
            return " is recieved from an NPC"
        }else if(availability.equals("E")){
            return " evolves at level " + pokemon.minLevel.toString() + " from"

        }else if(availability.equals("B")){
            return " can be bred from another pokemon"
        }else{
            return " Cannot be obtained. This should not happen."
        }
    }

}