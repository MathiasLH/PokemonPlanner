package pokemon.planner.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.R
import pokemon.planner.TeamActivity
import pokemon.planner.adapters.StatSummaryAdapter
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Pokemon
import pokemon.planner.model.TYPE
import pokemon.planner.model.Team
import pokemon.planner.adapters.ObtainMethodAdapter

class PokemonFragment(private var pokemon: Pokemon, private var team: Team, private var  ctx: Context): Fragment() {
    private lateinit var ssa: StatSummaryAdapter
    private lateinit var LayoutManger : LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.pokemon_fragment, container, false)
            if(!pokemon.number.equals("-1")){
                var image = view.findViewById<ImageView>(R.id.pokemonImage)
                image.setImageBitmap(Pokedex.largeImages.get(Integer.parseInt(pokemon.number)-1))

                var nameLabel = view.findViewById<TextView>(R.id.pokemonNameLabel)
                nameLabel.setText(pokemon.name)


                var numberLabel = view.findViewById<TextView>(R.id.numberLabel)
                numberLabel.setText("#" + (pokemon.number).toString())

                var statList = view.findViewById<RecyclerView>(R.id.statList)
                var llm = LinearLayoutManager(ctx)
                var statNames = Pokedex.getGenSpecificStatNames(team)
                var statValues : Array<Int>
                if(team.version.generation == 1){
                    statValues = pokemon.gen1Stats
                }else{
                    statValues = pokemon.stats
                }
                statList.layoutManager = llm
                statList.adapter = StatSummaryAdapter(ctx, team, statNames, statValues, 1)

                //createObtainMethodComponent(view.findViewById(R.id.replaceView) as ViewGroup, pokemon)
                var obtainRecycler = view.findViewById<RecyclerView>(R.id.obtainRecyclerView)

                LayoutManger = LinearLayoutManager(ctx)
                obtainRecycler.layoutManager = LayoutManger
                obtainRecycler.adapter = ObtainMethodAdapter(ctx, pokemon, team)





                /*var replaceView = view.findViewById<ConstraintLayout>(R.id.constraintLayout)
                replaceView = testView*/

                var card1 = view.findViewById<CardView>(R.id.card1)
                var card2 = view.findViewById<CardView>(R.id.card2)
                var type1 = view.findViewById<TextView>(R.id.type1Text)
                var type2 = view.findViewById<TextView>(R.id.type2Text)

                if(pokemon.secondaryType.equals(TYPE.NONE)){
                    //only has one type
                    card1.visibility = View.GONE
                    type2.setText(pokemon.primaryType.toString().substring(0, 3))
                    val background = GradientDrawable()
                    background.setStroke(5, Color.GRAY)
                    background.cornerRadius = 8f
                    background.setColor(Color.parseColor(pokemon.primaryType.color))
                    card2.background = background
                }else{
                    //two types
                    card1.visibility = View.VISIBLE
                    card2.visibility = View.VISIBLE
                    type1.setText(pokemon.primaryType.toString().substring(0, 3))
                    type2.setText(pokemon.secondaryType.toString().substring(0, 3))

                    val background1 = GradientDrawable()
                    background1.setStroke(5, Color.GRAY)
                    background1.cornerRadius = 8f
                    background1.setColor(Color.parseColor(pokemon.primaryType.color))
                    card1.background = background1

                    val background2 = GradientDrawable()
                    background2.setStroke(5, Color.GRAY)
                    background2.cornerRadius = 8f
                    background2.setColor(Color.parseColor(pokemon.secondaryType.color))

                    card2.background = background2
                    card1.background = background1
                }
                 var deleteButton = view.findViewById<Button>(R.id.deleteButton)
                deleteButton.setOnClickListener {
                    (activity as TeamActivity).deletepokemon()
                }
            }
        return view
    }



    /*private fun createObtainMethodComponent(view: View, parent: ViewGroup, pokemon: Pokemon){
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //val parent = view.findViewById(R.id.replaceView) as ViewGroup
        inflater.inflate(R.layout.acquisition, parent)
        var testView = view.findViewById<ViewGroup>(R.id.replaceView)
        testView.findViewById<TextView>(R.id.numberNameLabel).text = ("#" + pokemon.number + " " + pokemon.name + " is obtained by:")
        testView.findViewById<TextView>(R.id.obtainedByLabel).text = getObtainMethod(pokemon, team)
        testView.findViewById<ImageView>(R.id.image).setImageBitmap(Pokedex.smallImages[Integer.parseInt(pokemon.number)-1])
        if(getObtainMethod(pokemon,team).equals("Evolves from other pokemon")){
            inflater.inflate(R.layout.acquisition, testView.findViewById(R.id.replaceView))
            //createObtainMethodComponent(view, testView as ViewGroup, Pokedex.pokedex[Integer.parseInt(pokemon.number)-2])
        }
    }*/




}