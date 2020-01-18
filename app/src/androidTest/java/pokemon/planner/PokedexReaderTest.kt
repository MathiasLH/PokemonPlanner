package pokemon.planner

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import pokemon.planner.model.Pokedex
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import pokemon.planner.model.Move
import pokemon.planner.model.TYPE

//The purpose of this testclass is to ensure that all the data is read correctly,
//and that none of the data entries are attributed to the wrong pokemon.
//If the first and last pokemon pass tests, all the pokemon in between should also
//necessarily be correctly read.
@RunWith(AndroidJUnit4::class)
class PokedexReaderTest {
    @Test
    fun pokedexReadDataTest(){
        //test first pokemon
        assertEquals(Pokedex.pokedex.get(0).name, "bulbasaur")
        //test last pokemon
        assertEquals(Pokedex.pokedex.get(648).name, "genesect")
    }

    @Test
    fun pokedexReadStatsTest(){
        //test first pokemon
        //reference valus can be seen at https://bulbapedia.bulbagarden.net/wiki/Bulbasaur_(Pok%C3%A9mon)#Base_stats
        assertEquals(Pokedex.pokedex.get(0).stats[0], 45)
        assertEquals(Pokedex.pokedex.get(0).stats[1], 49)
        assertEquals(Pokedex.pokedex.get(0).stats[2], 49)
        assertEquals(Pokedex.pokedex.get(0).stats[3], 65)
        assertEquals(Pokedex.pokedex.get(0).stats[4], 65)
        assertEquals(Pokedex.pokedex.get(0).stats[5], 45)

        //test last pokemon
        //reference values can be seen at https://bulbapedia.bulbagarden.net/wiki/Genesect_(Pok%C3%A9mon)#Base_stats
        assertEquals(Pokedex.pokedex.get(648).stats[0], 71)
        assertEquals(Pokedex.pokedex.get(648).stats[1], 120)
        assertEquals(Pokedex.pokedex.get(648).stats[2], 95)
        assertEquals(Pokedex.pokedex.get(648).stats[3], 120)
        assertEquals(Pokedex.pokedex.get(648).stats[4], 95)
        assertEquals(Pokedex.pokedex.get(648).stats[5], 99)
    }

    @Test
    fun pokedexReadTypesTest(){
        //test first pokemon
        assertEquals(Pokedex.pokedex.get(0).typePrimary, TYPE.GRASS)
        assertEquals(Pokedex.pokedex.get(0).typeSecondary, TYPE.POISON)

        //test last pokemon
        assertEquals(Pokedex.pokedex.get(648).typePrimary, TYPE.BUG)
        assertEquals(Pokedex.pokedex.get(648).typeSecondary, TYPE.STEEL)
    }

     @Test
     fun pokedexReadPokemonAbilitiesTest(){
         //test first pokemon
         assertEquals(Pokedex.pokedex.get(0).abilityPrimary, "overgrow")

         //test last pokemon
         assertEquals(Pokedex.pokedex.get(648).abilityPrimary, "download")
     }

    @Test
    fun pokedexReadLearnsetTest(){
        //test first pokemon
        val vineWhip = Move("vine whip", 22, TYPE.GRASS, "2")
        assertTrue(Pokedex.pokedex.get(0).learnSets[0].isLearnable(vineWhip))

        //test last pokemon
        val xScissor = Move("x scissor", 404, TYPE.BUG, "2")
        assertTrue(Pokedex.pokedex.get(648).learnSets[10].isLearnable(xScissor))
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun readData(){
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            Pokedex.readPokedexData(appContext,appContext.getSharedPreferences("dolphin :^)", 0))
            Thread.sleep(3000)
        }
    }
}