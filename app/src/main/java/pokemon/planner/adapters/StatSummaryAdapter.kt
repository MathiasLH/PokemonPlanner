package pokemon.planner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stat_summary_list_item.view.*
import pokemon.planner.R
import pokemon.planner.model.Team

class StatSummaryAdapter(private val context: Context, private val team: Team,private val statNames: Array<String>, private val statValues: Array<Int>) : RecyclerView.Adapter<StatSummaryAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vh = ViewHolder(LayoutInflater.from(context).inflate(R.layout.stat_summary_list_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return statValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.statName.text = statNames.get(position)
            holder.statValue.text = statValues.get(position).toString()
            if(statNames.get(position).equals("Total")){
                holder.statMaxValue.text = "700"
                holder.statBar.max = 700
                holder.statBar.progress = statValues.get(position)
            }else{
                holder.statMaxValue.text = "200"
                holder.statBar.max = 200
                holder.statBar.progress = statValues.get(position)
            }



    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statName = view.statNameLabel
        val statValue = view.statValueLabel
        val statBar = view.statValueBar
        val statMaxValue = view.statMaxValueLabel
    }
}