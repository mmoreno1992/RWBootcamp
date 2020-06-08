package model.animals

import Gender
import model.caffe.Sponsorship
import model.people.Person
import repositories.ShelterRepository
import java.util.*
import kotlin.properties.Delegates

/**
 * Data class that represent a Feline
 */
data class Cat(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sex: Gender,
    val breed: String,
    val shelterId: String
) {

    var adoptedBy: Person? = null

    /**
     * Collection to manage who is sponsoring this cat
     */
    val sponsorships: MutableSet<Sponsorship> = mutableSetOf()

    /**
     * Delegated property, observing change in the state of this
     * instance it is going to automatically update the Repository
     */
    var catAdopted: Boolean
            by Delegates.observable(false, { _, _, newValue ->
                if (newValue) {
                    ShelterRepository.removeAdoptedCatFromShelter(this)
                }
            })

    /**
     * Method for adopting a feline
     * @param person instance of Person that is adopting a Cat
     */
    fun adoptCat(person: Person) {
        catAdopted = true
        adoptedBy = person
        person.addAdoptedCat(this)
    }

    /**
     * Method for sponsoring a feline
     * @param sponsorship information about the sponsor and associated cat
     * @return [Boolean] indicates if the operation was made correctly
     */
    fun sponsorCat(sponsorship: Sponsorship): Boolean {
        if (sponsorship.catId == this.id)
            return sponsorships.add(sponsorship)
        return false
    }

    /**
     * @param sponsorship sponsoring for this feline
     * @return [Unit]
     */
    fun addSponsorShip(sponsorship: Sponsorship) = sponsorships.add(sponsorship)

    /**
     * Overriding default equals method
     */
    override fun equals(other: Any?): Boolean {
        if (other is Cat) {
            return other.id == this.id
        }
        return false
    }

    /**
     * Overriding default hashCode methods
     */
    override fun hashCode(): Int {
        return id.hashCode()
    }

    /**
     * This method indicates if a feline has sponsors or not
     * @return: A message indicating its situation
     */
    fun beautifulFelineShowMeYourSponsors() =
        if (sponsorships.size > 0)
            "My sponsors are: ${sponsorships.forEach { it.patronId }}"
        else
            "Don't have any sponsor yet :("


    override fun toString(): String {
        return "Cat: $name, breed: $breed."
    }
}