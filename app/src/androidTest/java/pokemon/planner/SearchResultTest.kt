package pokemon.planner

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pokemon.planner.model.*
import org.junit.Assert.*
import org.junit.BeforeClass

//The purpose of this testclass is to ensure that the search functionality works as intended.
class SearchResultTest {

    @Test
    fun searchForNameTest(){
        var searchForm = SearchForm(
            "bulbasaur",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            TYPE.NONE,
            TYPE.NONE,
            "",
            "",
            Move("", -1, TYPE.NONE, ""),
            GameVersion.NONE
        )

        var searchResults = Pokedex.searchPokemon(searchForm, Team("testTeam", GameVersion.RED, true))
        assertTrue(searchResults.size == 1)
        assertTrue(searchResults[0].name == "bulbasaur")
    }

    @Test
    fun searchForTypeTest(){
        var searchForm = SearchForm(
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            TYPE.FIRE,
            TYPE.NONE,
            "",
            "",
            Move("", -1, TYPE.NONE, ""),
            GameVersion.NONE
        )

        var searchResults = Pokedex.searchPokemon(searchForm, Team("testTeam", GameVersion.RED, true))

        for(pokemon in searchResults){
            assertEquals(pokemon.typePrimary, TYPE.FIRE)
        }
    }

    @Test
    fun searchForMoveTest(){
        var searchForm = SearchForm(
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            TYPE.NONE,
            TYPE.NONE,
            "",
            "",
            Move("fly", 19, TYPE.FLYING, "2"),
            GameVersion.NONE
        )
        var searchResults = Pokedex.searchPokemon(searchForm, Team("testTeam", GameVersion.EMERALD, true))
        for(pokemon in searchResults){
            assertTrue(pokemon.learnSets[GameVersion.EMERALD.versionGroupId-1].isLearnable(Move("fly", 19, TYPE.FLYING, "2")))
        }
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