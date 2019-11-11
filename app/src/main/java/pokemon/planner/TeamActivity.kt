package pokemon.planner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import kotlinx.android.synthetic.main.activity_team.*
import pokemon.planner.fragments.PokemonFragment
import pokemon.planner.fragments.TeamFragment
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team


private const val NUM_PAGES = 7

class TeamActivity : FragmentActivity() {
    private lateinit var vp: ViewPager
    private lateinit var pagerAdapter: ScreenSlidePagerAdapter
    private lateinit var team: Team
    private lateinit var listOfPokemonButtons: Array<ImageButton>
    private var lastPressedBall: Int = 0
    private var lastViewedPokemon: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        val intent = getIntent()
        team = intent.getSerializableExtra("team") as Team


        listOfPokemonButtons = arrayOf(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6)
        createPokeballBar(team)
        for (x in 0..listOfPokemonButtons.size-1){
            listOfPokemonButtons[x].tag = x.toString()
            listOfPokemonButtons[x].setOnClickListener {
                if(!team.pokemonList[Integer.parseInt(listOfPokemonButtons[x].tag.toString())].number.equals("-1")){
                    vp.setCurrentItem(x+1, true)
                }else{
                    lastPressedBall = Integer.parseInt(listOfPokemonButtons[x].tag as String)
                    val intent = Intent(this, PokemonSearcher::class.java)
                    startActivityForResult(intent, 1)
                }
                //launch pokemon activity
            }
        }




        vp = findViewById(R.id.vp)
        pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        vp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //openPokeball(position, listOfPokemonButtons)
            }

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
            }
        })

        vp.adapter = pagerAdapter
        val updatedAdatper = pagerAdapter
        updatedAdatper.notifyDataSetChanged()
        vp.adapter = updatedAdatper


    }

    //wip
    /*private fun openPokeball(position: Int, listOfPokemonButtons: Array<ImageButton>) {
        if(position > 0){
            if(lastViewedPokemon != 0){
                listOfPokemonButtons[lastViewedPokemon].setImageResource(R.drawable.closed)
            }
            lastViewedPokemon = position-1
            listOfPokemonButtons[position-1].setImageResource(R.drawable.open)
        }

    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {
                team.pokemonList[lastPressedBall] = Pokedex.pokedex[data!!.getIntExtra("num", 0)]
                createPokeballBar(team)


                var adapter = vp.adapter
                adapter?.notifyDataSetChanged()
                finish()
                startActivity(getIntent())
            }
        }

    }

    fun deletepokemon(){
        team.pokemonList[vp.currentItem-1] = team.dummyPokemon

        finish()
        startActivity(getIntent())
    }

    fun createPokeballBar(team: Team){
        var listOfPokemonSprites = arrayOf(pokemon1Sprite, pokemon2Sprite, pokemon3Sprite, pokemon4Sprite, pokemon5Sprite, pokemon6Sprite)
        for(x in 0..team.pokemonList.size-1){
            if(!team.pokemonList[x].number.equals("-1")){
                listOfPokemonButtons[x].setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.open))
                listOfPokemonSprites[x].visibility = View.VISIBLE
                listOfPokemonSprites[x].setImageBitmap(Pokedex.smallImages[Integer.parseInt(team.pokemonList[x].number)-1])
            }else{
                listOfPokemonButtons[x].setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
                listOfPokemonSprites[x].visibility = View.GONE
            }
        }


        /*if(!team.pokemonList[0].number.equals("-1")){
            pokemon1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(!team.pokemonList[1].number.equals("-1")){
            pokemon2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(!team.pokemonList[2].number.equals("-1")){
            pokemon3.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon3.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(!team.pokemonList[3].number.equals("-1")){
            pokemon4.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon4.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(!team.pokemonList[4].number.equals("-1")){
            pokemon5.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon5.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }
        if(!team.pokemonList[5].number.equals("-1")){
            pokemon6.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.closed))
        }else{
            pokemon6.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.plus))
        }*/

    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment {
            when(position){
             0 -> return TeamFragment(team)
            else -> return PokemonFragment(team.pokemonList[position-1])
            }
        }
    }



}
