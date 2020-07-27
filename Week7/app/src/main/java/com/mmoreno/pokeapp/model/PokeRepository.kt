package com.mmoreno.pokeapp.model

import com.mmoreno.pokeapp.model.dao.PokeDao

/**
 * Implementation of the Repository interface
 * in this case for interacting with Room Database
 */
class PokeRepository(private val database: PokeDatabase) : Repository<PokeEntity, String> {

    //Variable for holding the Dao for interacting with the table
    //and delegate it the interaction with the database
    private val pokeDao: PokeDao = database.pokeDao()

    /**
     * Method for updating an entity
     * @param entity - instance to be updated
     */
    override suspend fun update(entity: PokeEntity) {
        pokeDao.insert(entity)
    }

    /**
     * Method for deleting an entity
     * @param entity - instance to be deleted
     */
    override suspend fun delete(entity: PokeEntity) {
        pokeDao.delete(entity)
    }

    /**
     * Method for retrieving the Pokerecords
     */
    fun pokeRecords() = pokeDao.pokeRecords()

    /**
     * Method for creating a new entity
     * @param entity - instance to be persisted
     */
    override suspend fun create(entity: PokeEntity) {
        pokeDao.insert(entity)
    }

    /**
     * Method for persisting a list of entities
     * @param entities - list of entities to be persisted
     */
    override suspend fun create(entities: List<PokeEntity>) {
        pokeDao.insert(entities)
    }

}