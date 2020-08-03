package com.mmoreno.pokeapp.model

/**
 * Custom interface defined for Repository operations
 */
interface Repository<T, K> {
    suspend fun create(entity: T)
    suspend fun create(entities: List<T>)
    suspend fun update(entity: T)
    suspend fun delete(entity: T)
}