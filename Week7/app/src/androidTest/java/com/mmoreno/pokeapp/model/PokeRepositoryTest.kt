package com.mmoreno.pokeapp.model

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mmoreno.pokeapp.model.dao.PokeDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Implemented class for testing create, update, delete
 * operations in room database
 */
class PokeRepositoryTest {

    companion object {
        const val URL_TEST = "url-test"
        const val NAME_TEST = "poke-name-test"
    }

    private lateinit var pokeDatabase: PokeDatabase
    private lateinit var pokeDao: PokeDao

    /**
     * Custom method for opening resources and setting up our environment
     */
    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        pokeDatabase = Room.inMemoryDatabaseBuilder(
            context, PokeDatabase::class.java
        ).build()
        pokeDao = pokeDatabase.pokeDao()
    }

    /**
     * Custom method for closing the opened resources
     */
    @After
    fun tearDown() {
        pokeDatabase.close()
    }

    /**
     * Custom method for checking if the update method
     * in Room works fine
     */
    @Test
    fun update() {
        create()
        runBlocking {
            //Variable for being a copy of the selected entity
            val pokemonCopy: PokeEntity

            var pokemonOld: PokeEntity = pokeDao.findById(URL_TEST)
            //Creating a copy of the already selected entity
            //for comparing after and checking if the update action is working
            pokemonCopy = PokeEntity(pokemonOld.url, pokemonOld.name)

            //Here I change one property of the object
            pokemonOld.name = "named has changed"

            //And tell room to update it
            pokeDao.update(pokemonOld)

            //After I query the same object and check if the operation has worked fine
            pokemonOld = pokeDao.findById(URL_TEST)

            //So when assertNotEquals compares with their equals method
            //the test will pass if the operation has being done correctly
            assertNotEquals(pokemonOld, pokemonCopy)

        }
    }

    /**
     * Custom method for testing if delete operation in room
     * works properly, it first queries an entity, after deletes it
     * and checks if the new selected entity is null to pass the test
     */
    @Test
    fun delete() {
        create()
        runBlocking {
            var pokeEntity = pokeDao.findById(URL_TEST)
            pokeDao.delete(pokeEntity)
            //If the entity has been deleted the assertNull
            //test will be true and the test will pass :D
            pokeEntity = pokeDao.findById(URL_TEST)
            assertNull(pokeEntity)
        }
    }

    /**
     * Custom method for checking if our create/save
     * function works properly
     */
    @Test
    fun create() {
        val pokemon = PokeEntity(URL_TEST, NAME_TEST)
        var numberOfRecords = 0
        runBlocking {
            pokeDao.insert(pokemon)
            numberOfRecords = pokeDao.countPokeRecords()
        }
        //Checking if the query returns more than one record
        //the test passes :D
        assert(numberOfRecords > 0)
    }

}