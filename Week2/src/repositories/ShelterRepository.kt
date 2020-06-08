package repositories

import model.animals.Cat
import model.shelter.Shelter


/**
 * Repository - Singleton created for adding different Shelters
 * Mario M. - RayWenderlich Android Bootcamp:D
 * In this repository I use a Map because it is more friendly to get
 * in this case an object using a String key (the name of the Shelter)
 * @author: Mario
 */
object ShelterRepository {

    val shelters = mutableMapOf<String, Shelter>()

    /**
     * @param: [shelter] instance of Shelter to be added to the [shelters] set
     * @return: [Unit]
     */
    fun addShelter(shelter: Shelter) {
        shelters[shelter.shelterId] = shelter
    }

    /**
     * @param [cat] instance of Cat to be added
     */
    fun addCatToShelter(cat: Cat) {
        shelters[cat.shelterId]?.addCat(cat)
    }

    /**
     * @param [cat] instance of Cat to be removed
     */
    fun removeAdoptedCatFromShelter(cat: Cat) {
        shelters[cat.shelterId]?.removeAdoptedCat(cat)
    }

    fun findCatById(catId: String): Cat? {
        var cat: Cat? = null
        for ((k, v) in shelters) {
            cat = v.findCatById(catId)
        }
        return cat
    }


}