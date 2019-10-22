package pokemon.planner.adapters

import android.content.Context
import android.graphics.Color
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
        val defaultShape = GradientDrawable()
        defaultShape.setStroke(5, Color.GRAY)
        val selectedShape = GradientDrawable()
        selectedShape.setStroke(10, Color.BLACK)
        defaultShape.cornerRadius = 8f
        selectedShape.cornerRadius = 8f

        defaultShape.setColor(Color.parseColor(types.get(position).color))
        selectedShape.setColor(Color.parseColor(types.get(position).color))
        holder.typeName.setOnClickListener {
            if(lastPressedHolder != null){
                val test = GradientDrawable()
                //test = lastPressedHolder!!.typeName.background
                var color: Int = Color.TRANSPARENT

                var background: Drawable = lastPressedHolder!!.typeName.background

                lastPressedHolder!!.typeName.background = defaultShape
            }
            Toast.makeText(context, types.get(position).toString(), Toast.LENGTH_LONG).show()
            currentType =types.get(position)
            println(currentType)
            lastPressedHolder = holder
        }
        if(types.get(position) == currentType){
            holder.typeName.background = selectedShape
        }else{
            holder.typeName.background = defaultShape
        }
    }


    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){

        val typeName = view.typeName
        val layout = view.cl
    }
}