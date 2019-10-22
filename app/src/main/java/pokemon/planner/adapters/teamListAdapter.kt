package pokemon.planner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import pokemon.planner.R
import pokemon.planner.model.Team

class teamListAdapter(items: ArrayList<Team>, ctx: Context): ArrayAdapter<Team>(ctx, R.layout.team_list_element, items){

    private class teamListViewHolder{
        internal var name: TextView? = null
        internal var number: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View{
        var view = view
        val viewHolder: teamListViewHolder

        if(view == null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.team_list_element, viewGroup, false)

            viewHolder = teamListViewHolder()
            viewHolder.name = view!!.findViewById<View>(R.id.teamName) as TextView
            viewHolder.number = view!!.findViewById<View>(R.id.amountOfPokemon) as TextView
        }else{
            viewHolder = view.tag as teamListViewHolder
        }

        val team = getItem(i)
        viewHolder.name!!.text = team!!.name
        viewHolder.number!!.text = team!!.size().toString()

        view.tag = viewHolder
        return view
    }

}