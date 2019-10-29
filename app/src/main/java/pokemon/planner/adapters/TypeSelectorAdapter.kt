package pokemon.planner.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.type_selector_element.view.*
import pokemon.planner.model.TYPE


class TypeSelectorAdapter(private val context: Context, private val types: ArrayList<TYPE>) : RecyclerView.Adapter<TypeSelectorAdapter.ViewHolder>() {
    private var currentType: TYPE = TYPE.NONE
    private var lastPressedType: TYPE? = TYPE.NONE
    private var lastPressedHolder: ViewHolder? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(pokemon.planner.R.layout.type_selector_element, parent, false))
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.typeName.setText(types.get(position).toString())
        val unSelected = GradientDrawable()
        val selected = GradientDrawable()
        unSelected.setStroke(5, Color.GRAY)
        selected.setStroke(8, Color.BLACK)
        unSelected.cornerRadius = 8f
        selected.cornerRadius = 8f
        unSelected.setColor(Color.parseColor(types.get(position).color))
        selected.setColor(Color.parseColor(types.get(position).color))
        if(types.get(position).equals(currentType)){
            holder.typeName.background = selected
        }else{
            holder.typeName.background = unSelected
        }

        holder.typeName.setOnClickListener {
            holder.typeName.background = selected
            currentType = types.get(position)
            this.notifyDataSetChanged()
        }

    }


    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){

        val typeName = view.typeName
        val layout = view.cl
    }
}