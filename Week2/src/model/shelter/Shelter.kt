package model.shelter

import model.animals.Cat


/**
 * Class that represents a Shelter
 * @author: Mario
 */
class Shelter(val shelterId: String, var address: String, var phone: String) {

    val availableCats = mutableSetOf<Cat>()
    val adoptedCats = mutableSetOf<Cat>()

    /**
     * Function to add an instance of Cat to the avaible cats
     * @return: [Unit]
     */
    fun addCat(cat: Cat) = availableCats.add(cat)

    /**
     * Method used for removing an adopted cat and also manages
     * another list that serves as a control for adopted cats
     * because a cat can be removed for another reason may be death :(
     */
    fun removeAdoptedCat(cat: Cat) {
        availableCats.remove(cat)
        adoptedCats.add(cat)
    }

    /**
     * Produce the list of unadopted, unsponsored cats staying
     * at the cafe currently
     * @return: [List] of Cats
     */
    fun unadoptedAndUnsponsoredCats() = availableCats.filter { !it.catAdopted && it.sponsorships.size == 0 }

    fun sposoredButUnadopted() = availableCats.filter { !it.catAdopted && it.sponsorships.size > 0 }

    fun mostPopularCats(top: Int): List<Pair<String, Int>> {
        var result: List<Pair<String, Int>> = listOf()
        result = availableCats.flatMap { it.sponsorships }
            .groupingBy { it.catId }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
        return result.take(top)
    }

    /**
     * Overriding default equals method
     */
    override fun equals(other: Any?): Boolean {
        if (other is Shelter) {
            return this.shelterId == other.shelterId
        }
        return false
    }

    /**
     * Overriding hashCode default method
     */
    override fun hashCode(): Int {
        return shelterId.hashCode()
    }

    /**
     * Overriding default toString Method
     * @return: [String] representation of this object
     */
    override fun toString(): String {
        return "Shelter: $shelterId, number of felines available ${availableCats.size}, it has: \n$availableCats"
    }

    fun findCatById(catId: String): Cat? {
        return availableCats.find { it.id == catId }
    }
}