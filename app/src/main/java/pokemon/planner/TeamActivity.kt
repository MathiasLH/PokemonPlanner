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
import pokemon.planner.model.GameVersion
import pokemon.planner.model.Pokedex
import pokemon.planner.model.Team


private const val NUM_PAGES = 7

class TeamActivity : FragmentActivity() {
    private lateinit var vp: ViewPager
    private lateinit var pagerAdapter: ScreenSlidePagerAdapter
    private lateinit var team: Team
    private var teamNumber: Int = -1
    private lateinit var listOfPokemonButtons: Array<ImageButton>
    private var lastPressedBall: Int = 0
    private var lastViewedPokemon: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        val intent = getIntent()
        teamNumber = intent.getIntExtra("team", -1)
        team = Pokedex.teams.get(teamNumber)


        listOfPokemonButtons = arrayOf(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6)

        createPokeballBar(team)
        for (x in 0..listOfPokemonButtons.size-1){
            listOfPokemonButtons[x].tag = x.toString()
            listOfPokemonButtons[x].setOnClickListener {
                if(team.pokemonList[Integer.parseInt(listOfPokemonButtons[x].tag.toString())].number.equals("-1")){
                    lastPressedBall = Integer.parseInt(listOfPokemonButtons[x].tag as String)
                    val intent = Intent(this, PokemonSearchActivity::class.java)
                    intent.putExtra("team", teamNumber)
                    startActivityForResult(intent, 1)
                }else{
                    vp.setCurrentItem(x+1, true)
                }
                //launch pokemon activity
            }
        }

        Trainer.setOnClickListener {
            vp.setCurrentItem(0, true)
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

                Pokedex.syncTeamsToDatabase()
                var adapter = vp.adapter
                adapter?.notifyDataSetChanged()
                Pokedex.syncTeamsToDatabase()
                finish()
                startActivity(getIntent())
            }
        }

    }

    fun deletepokemon(){
        team.pokemonList[vp.currentItem-1] = team.dummyPokemon
        Pokedex.syncTeamsToDatabase()
        finish()
        startActivity(getIntent())
    }

    fun createPokeballBar(team: Team){
        setTrainerSprite(team)
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

    }

    fun setTrainerSprite(team: Team){
        if(team.version.equals(GameVersion.RED) || team.version.equals(GameVersion.BLUE)){
            Trainer.setImageResource(R.drawable.rb_back)
        }else if(team.version.equals(GameVersion.YELLOW)){
            Trainer.setImageResource(R.drawable.y_back)
        }else if(team.version.equals(GameVersion.GOLD) || team.version.equals(GameVersion.SILVER)){
            Trainer.setImageResource(R.drawable.gsc_male_back)
        }else if(team.version.equals(GameVersion.CRYSTAL)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.gsc_male_back)
            }else{
                Trainer.setImageResource(R.drawable.c_female_back)
            }
        }else if(team.version.equals(GameVersion.RUBY) || team.version.equals(GameVersion.SAPPHIRE)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.rs_male_back)
            }else{
                Trainer.setImageResource(R.drawable.rs_female_back)
            }
        }else if(team.version.equals(GameVersion.EMERALD)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.e_male_back)
            }else{
                Trainer.setImageResource(R.drawable.e_female_back)
            }
        }else if(team.version.equals(GameVersion.FIRERED) || team.version.equals(GameVersion.LEAFGREEN)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.frlg_male_back)
            }else{
                Trainer.setImageResource(R.drawable.frlg_female_back)
            }
        }else if(team.version.equals(GameVersion.DIAMOND) || team.version.equals(GameVersion.PEARL)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.dp_male_back)
            }else{
                Trainer.setImageResource(R.drawable.dp_female_back)
            }
        }else if(team.version.equals(GameVersion.PLATINUM)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.pt_male_back)
            }else{
                Trainer.setImageResource(R.drawable.pt_female_back)
            }
        }else if(team.version.equals(GameVersion.HEARTGOLD) || team.version.equals(GameVersion.SOULSILVER)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.hgss_male_back)
            }else{
                Trainer.setImageResource(R.drawable.hgss_female_back)
            }
        }else if(team.version.equals(GameVersion.BLACK) || team.version.equals(GameVersion.WHITE)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.bw_male_back)
            }else{
                Trainer.setImageResource(R.drawable.bw_female_back)
            }
        }else if(team.version.equals(GameVersion.BLACK2) || team.version.equals(GameVersion.WHITE2)){
            if(team.gender){
                Trainer.setImageResource(R.drawable.b2w2_male_back)
            }else{
                Trainer.setImageResource(R.drawable.b2w2_female_back)
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment {
            when(position){
             0 -> return TeamFragment(team)
            else -> return PokemonFragment(team.pokemonList[position-1], team, applicationContext, true)
            }
        }
    }



}
