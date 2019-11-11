package pokemon.planner.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_result_element.view.*
import pokemon.planner.model.Pokemon
import pokemon.planner.model.TYPE
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import pokemon.planner.R
import pokemon.planner.model.Pokedex
import java.io.IOException
import java.net.HttpURLConnection


class SearchResultAdapter(private val context: Context, private val pokedex: ArrayList<Pokemon>): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_result_element, parent, false))
    }

    override fun getItemCount(): Int{
        return pokedex.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.name.setText((pokedex.get(position).name))
        holder.number.setText(pokedex.get(position).number)
        if(pokedex.get(position).secondaryType.equals(TYPE.NONE)){
            //only has one type
            holder.card1.visibility = View.GONE
            holder.type2.setText(pokedex.get(position).primaryType.toString().substring(0, 3))
            val background = GradientDrawable()
            background.setStroke(5, Color.GRAY)
            background.cornerRadius = 8f
            background.setColor(Color.parseColor(pokedex.get(position).primaryType.color))
            holder.card2.background = background
        }else{
            //two types
            holder.card1.visibility = View.VISIBLE
            holder.card2.visibility = View.VISIBLE
            holder.type1.setText(pokedex.get(position).primaryType.toString().substring(0, 3))
            holder.type2.setText(pokedex.get(position).secondaryType.toString().substring(0, 3))

            val background1 = GradientDrawable()
            background1.setStroke(5, Color.GRAY)
            background1.cornerRadius = 8f
            background1.setColor(Color.parseColor(pokedex.get(position).primaryType.color))
            holder.card1.background = background1

            val background2 = GradientDrawable()
            background2.setStroke(5, Color.GRAY)
            background2.cornerRadius = 8f
            background2.setColor(Color.parseColor(pokedex.get(position).secondaryType.color))
            holder.card1.background = background2

            holder.card2.background = background2
            holder.card1.background = background1
        }
            //DON'T TOUCH
            holder.image.setImageBitmap(Pokedex.smallImages[Integer.parseInt(pokedex[position].number)-1])

    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl = view.constraintLayout
        val card1 = view.card1
        val card2 = view.card2
        val image = view.pokemonImage
        val name = view.nameLabel
        val number = view.numberLabel
        val type1 = view.type1Text
        val type2 = view.type2Text
    }
}