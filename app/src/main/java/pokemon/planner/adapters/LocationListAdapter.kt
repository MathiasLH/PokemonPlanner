package pokemon.planner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import pokemon.planner.R

class LocationListAdapter(private val context: Context, private val locations: ArrayList<String>): RecyclerView.Adapter<LocationListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.location_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.location.text = locations[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val location = view.locationLabel
    }
}