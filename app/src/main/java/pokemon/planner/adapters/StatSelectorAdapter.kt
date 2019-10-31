package pokemon.planner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.statrange.view.*
import pokemon.planner.R
import pokemon.planner.model.SearchForm

class StatSelectorAdapter(private val context: Context, private val stats: ArrayList<String>) : RecyclerView.Adapter<StatSelectorAdapter.ViewHolder>(){
    private var holders = ArrayList<ViewHolder>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vh = ViewHolder(LayoutInflater.from(context).inflate(R.layout.statrange, parent, false))
        holders.add(vh)
        return vh
    }

    override fun getItemCount(): Int {
        return stats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.statName.setText(stats.get(position))
    }

    fun getContents(): ArrayList<IntArray>{
        var stats = ArrayList<IntArray>()
        var statsMin = intArrayOf(0, 0, 0, 0, 0, 0, 0)
        var statsMax = intArrayOf(0, 0, 0, 0, 0, 0, 0)

        for(i in 0..holders.size-1){
            if(!holders.get(i).statMin.text.toString().equals("")){
                statsMin[i] = Integer.parseInt(holders.get(i).statMin.text.toString())
            }
            if(!holders.get(i).statMax.text.toString().equals("")){
                statsMax[i] = Integer.parseInt(holders.get(i).statMax.text.toString())
            }
        }
        stats.add(statsMin)
        stats.add(statsMax)
        return stats
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statName = view.statNameLabel
        val statMin = view.statMin
        val statMax = view.statMax
    }

}