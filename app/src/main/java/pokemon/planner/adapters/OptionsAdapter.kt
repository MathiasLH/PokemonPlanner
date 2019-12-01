package pokemon.planner.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.R
import androidx.recyclerview.widget.LinearLayoutManager
import pokemon.planner.model.*
import java.util.*
import kotlin.collections.ArrayList

class OptionsAdapter(private val context: Context, private val options: ArrayList<String>,private val team: Team, private val recycler: RecyclerView, private val searchForm: SearchForm): RecyclerView.Adapter<OptionsAdapter.ViewHolder>(){
    private var number = ""
    private var minStats = ArrayList<Int>()
    private var maxStats = ArrayList<Int>()
    private var type1 = TYPE.NONE
    private var type2 = TYPE.NONE
    private lateinit var parent: ViewGroup



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.parent = parent
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_option_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         if(options.get(position).equals("Name")){
            var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_nameoption, holder.optionContainer as ViewGroup, false)
             testView.findViewWithTag<EditText>("nameInput").setText(searchForm.name)
            holder.optionContainer.addView(testView)
        }else if(options.get(position).equals("Number")){
            var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_numberoption, holder.optionContainer as ViewGroup, false)
             testView.findViewWithTag<EditText>("numberInput").setText(searchForm.number)
             holder.optionContainer.addView(testView)
        }else if(options.get(position).equals("Primary Type")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_ptypeoption, holder.optionContainer as ViewGroup, false)
             var llm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
             var types = Pokedex.getGenSpecificTypes(team)
             types.remove(TYPE.NONE)
             var tsa = TypeSelectorAdapter(context, types)
             tsa.setSelected(searchForm.type1)
             var recyclerView = testView.findViewWithTag<RecyclerView>("ptypeInput")
             recyclerView.layoutManager = llm
             recyclerView.adapter = tsa
             holder.optionContainer.addView(testView)
         }else if(options.get(position).equals("Secondary Type")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_stypeoption, holder.optionContainer as ViewGroup, false)
             var llm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
             var tsa = TypeSelectorAdapter(context, Pokedex.getGenSpecificTypes(team))
             tsa.setSelected(searchForm.type2)
             var recyclerView = testView.findViewWithTag<RecyclerView>("stypeInput")
             recyclerView.layoutManager = llm
             recyclerView.adapter = tsa
             holder.optionContainer.addView(testView)
         }else if(options.get(position).equals("HP")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_hpoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.hpMin>0)testView.findViewWithTag<EditText>("hpminInput").setText(searchForm.hpMin.toString())
             if(searchForm.hpMax>0)testView.findViewWithTag<EditText>("hpmaxInput").setText(searchForm.hpMax.toString())
         }else if(options.get(position).equals("Attack")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_attackoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.attackMin>0)testView.findViewWithTag<EditText>("attackminInput").setText(searchForm.attackMin.toString())
             if(searchForm.attackMax>0)testView.findViewWithTag<EditText>("attackmaxInput").setText(searchForm.attackMax.toString())
         }else if(options.get(position).equals("Defense")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_defenseoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.defenseMin>0)testView.findViewWithTag<EditText>("defenseminInput").setText(searchForm.defenseMin.toString())
             if(searchForm.defenseMax>0)testView.findViewWithTag<EditText>("defensemaxInput").setText(searchForm.defenseMax.toString())
         }else if(options.get(position).equals("Special")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_specialoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.specialMin>0)testView.findViewWithTag<EditText>("specialminInput").setText(searchForm.specialMin.toString())
             if(searchForm.specialMax>0)testView.findViewWithTag<EditText>("specialmaxInput").setText(searchForm.specialMax.toString())
         }else if(options.get(position).equals("Sp. Attack")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_spattackoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.spAttackMin>0)testView.findViewWithTag<EditText>("spattackminInput").setText(searchForm.spAttackMin.toString())
             if(searchForm.spAttackMax>0)testView.findViewWithTag<EditText>("spattackmaxInput").setText(searchForm.spAttackMax.toString())
         }else if(options.get(position).equals("Sp. Defense")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_spdefenseoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.spDefenseMin>0)testView.findViewWithTag<EditText>("spdefenseminInput").setText(searchForm.spDefenseMin.toString())
             if(searchForm.spDefenseMax>0)testView.findViewWithTag<EditText>("spdefensemaxInput").setText(searchForm.spDefenseMax.toString())
         }else if(options.get(position).equals("Speed")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_speedoption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.speedMin>0)testView.findViewWithTag<EditText>("speedminInput").setText(searchForm.speedMin.toString())
             if(searchForm.speedMax>0)testView.findViewWithTag<EditText>("speedmaxInput").setText(searchForm.speedMax.toString())
         }else if(options.get(position).equals("Total")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_totaloption, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             if(searchForm.totalMin>0)testView.findViewWithTag<EditText>("totalminInput").setText(searchForm.spAttackMin.toString())
             if(searchForm.totalMax>0)testView.findViewWithTag<EditText>("totalmaxInput").setText(searchForm.spAttackMax.toString())
         }else if(options.get(position).equals("Primary Ability")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_primaryability, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             val array = arrayOfNulls<String>(Pokedex.abilities.size)
             ArrayList(Pokedex.abilities.values).toArray(array)
             array.sort()
             var adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array)
             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
             var spinner = testView.findViewWithTag<Spinner>("pabilitySpinner")
             spinner.adapter = adapter
             if(array.contains(searchForm.ability1)){
                 spinner.setSelection(array.indexOf(searchForm.ability1))
             }

         }else if(options.get(position).equals("Secondary Ability")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_primaryability, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             val array = arrayOfNulls<String>(Pokedex.abilities.size)
             ArrayList(Pokedex.abilities.values).toArray(array)
             array.sort()
             var adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array)
             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
             var spinner = testView.findViewWithTag<Spinner>("sabilitySpinner")
             spinner.adapter = adapter
             if(array.contains(searchForm.ability2)){
                 spinner.setSelection(array.indexOf(searchForm.ability2))
             }

         }else if(options.get(position).equals("Move")){
             var testView = LayoutInflater.from(context).inflate(R.layout.optionlist_move, holder.optionContainer as ViewGroup, false)
             holder.optionContainer.addView(testView)
             var testArray = ArrayList<String>()
             for(x in 0..Pokedex.moves.size-1){
                 if(Pokedex.moves[x] != null){
                     testArray.add((Pokedex.moves[x] as Move).name)
                 }
             }
             val array = arrayOfNulls<String>(testArray.size)
             testArray.toArray(array)
             array.sort()
             var adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array)
             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
             var spinner = testView.findViewWithTag<Spinner>("moveSpinner")
             spinner.adapter = adapter
             if(array.contains(searchForm.move.name)){
                 spinner.setSelection(array.indexOf(searchForm.move.name))
             }

         }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var optionContainer = view.findViewById<FrameLayout>(R.id.optionContainer)
    }

    fun returnUserInput(): SearchForm{

        if(recycler.findViewWithTag<EditText>("nameInput") != null){
            searchForm.name = recycler!!.findViewWithTag<EditText>("nameInput").text.toString()
        }
        if(recycler.findViewWithTag<EditText>("numberInput") != null){
            searchForm.number = recycler!!.findViewWithTag<EditText>("numberInput").text.toString()
        }
        if(recycler.findViewWithTag<EditText>("ptypeInput") != null){
            var view = recycler!!.findViewWithTag<RecyclerView>("ptypeInput")
            searchForm.type1 = (view.adapter as TypeSelectorAdapter).currentType
        }
        if(recycler.findViewWithTag<EditText>("stypeInput") != null){
            var view = recycler!!.findViewWithTag<RecyclerView>("stypeInput")
            searchForm.type2 = (view.adapter as TypeSelectorAdapter).currentType
        }

        if(recycler.findViewWithTag<EditText>("hpminInput") != null){
            if(!recycler.findViewWithTag<EditText>("hpminInput").text.toString().equals("")){ searchForm.hpMin = Integer.parseInt(recycler.findViewWithTag<EditText>("hpminInput").text.toString())}else{searchForm.hpMin = 0}
            if(!recycler.findViewWithTag<EditText>("hpmaxInput").text.toString().equals("")){ searchForm.hpMax = Integer.parseInt(recycler.findViewWithTag<EditText>("hpmaxInput").text.toString())}else{searchForm.hpMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("attackminInput") != null){
            if(!recycler.findViewWithTag<EditText>("attackminInput").text.toString().equals("")){ searchForm.attackMin = Integer.parseInt(recycler.findViewWithTag<EditText>("attackminInput").text.toString())}else{searchForm.attackMin = 0}
            if(!recycler.findViewWithTag<EditText>("attackmaxInput").text.toString().equals("")){ searchForm.attackMax = Integer.parseInt(recycler.findViewWithTag<EditText>("attackmaxInput").text.toString())}else{searchForm.attackMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("defenseminInput") != null){
            if(!recycler.findViewWithTag<EditText>("defenseminInput").text.toString().equals("")){ searchForm.defenseMin = Integer.parseInt(recycler.findViewWithTag<EditText>("defenseminInput").text.toString())}else{searchForm.defenseMin = 0}
            if(!recycler.findViewWithTag<EditText>("defensemaxInput").text.toString().equals("")){ searchForm.defenseMax = Integer.parseInt(recycler.findViewWithTag<EditText>("defensemaxInput").text.toString())}else{searchForm.defenseMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("specialminInput") != null){
            if(!recycler.findViewWithTag<EditText>("specialminInput").text.toString().equals("")){ searchForm.specialMin = Integer.parseInt(recycler.findViewWithTag<EditText>("specialminInput").text.toString())}else{searchForm.specialMin = 0}
            if(!recycler.findViewWithTag<EditText>("specialmaxInput").text.toString().equals("")){ searchForm.specialMax = Integer.parseInt(recycler.findViewWithTag<EditText>("specialmaxInput").text.toString())}else{searchForm.specialMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("spattackminInput") != null){
            if(!recycler.findViewWithTag<EditText>("spattackminInput").text.toString().equals("")){ searchForm.spAttackMin = Integer.parseInt(recycler.findViewWithTag<EditText>("spattackminInput").text.toString())}else{searchForm.spAttackMin = 0}
            if(!recycler.findViewWithTag<EditText>("spattackmaxInput").text.toString().equals("")){ searchForm.spAttackMax = Integer.parseInt(recycler.findViewWithTag<EditText>("spattackmaxInput").text.toString())}else{searchForm.spAttackMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("spdefenseminInput") != null){
            if(!recycler.findViewWithTag<EditText>("spdefenseminInput").text.toString().equals("")){ searchForm.spDefenseMin = Integer.parseInt(recycler.findViewWithTag<EditText>("spdefenseminInput").text.toString())}else{searchForm.spDefenseMin = 0}
            if(!recycler.findViewWithTag<EditText>("spdefensemaxInput").text.toString().equals("")){ searchForm.spDefenseMax = Integer.parseInt(recycler.findViewWithTag<EditText>("spdefensemaxInput").text.toString())}else{searchForm.spDefenseMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("speedminInput") != null){
            if(!recycler.findViewWithTag<EditText>("speedminInput").text.toString().equals("")){ searchForm.speedMin = Integer.parseInt(recycler.findViewWithTag<EditText>("speedminInput").text.toString())}else{searchForm.speedMin = 0}
            if(!recycler.findViewWithTag<EditText>("speedmaxInput").text.toString().equals("")){ searchForm.speedMax = Integer.parseInt(recycler.findViewWithTag<EditText>("speedmaxInput").text.toString())}else{searchForm.speedMax = 0}
        }
        if(recycler.findViewWithTag<EditText>("totalminInput") != null){
            if(!recycler.findViewWithTag<EditText>("totalminInput").text.toString().equals("")){ searchForm.totalMin = Integer.parseInt(recycler.findViewWithTag<EditText>("totalminInput").text.toString())}else{searchForm.totalMin = 0}
            if(!recycler.findViewWithTag<EditText>("totalmaxInput").text.toString().equals("")){ searchForm.totalMax = Integer.parseInt(recycler.findViewWithTag<EditText>("totalmaxInput").text.toString())}else{searchForm.totalMax = 0}
        }
        if(recycler.findViewWithTag<Spinner>("pabilitySpinner") != null){
            searchForm.ability1 = recycler.findViewWithTag<Spinner>("pabilitySpinner").selectedItem.toString()
        }
        if(recycler.findViewWithTag<Spinner>("sabilitySpinner") != null){
            searchForm.ability2 = recycler.findViewWithTag<Spinner>("sabilitySpinner").selectedItem.toString()
        }
        if(recycler.findViewWithTag<Spinner>("moveSpinner") != null){
            searchForm.move = Pokedex.moveNameToMove(recycler.findViewWithTag<Spinner>("moveSpinner").selectedItem.toString())
        }
        return searchForm
    }
}