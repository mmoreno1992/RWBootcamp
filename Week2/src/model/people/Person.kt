package model.people

import model.animals.Cat
import java.util.*

/**
 * @param id unique person's id
 * @param firstName
 * @param lastName
 * @param phoneNumber
 * @param email
 * @param cats list of adopted cats
 * @constructor
 */
open class Person(
    val id: String = UUID.randomUUID().toString(), // generates a random string for the ID!
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val cats: MutableSet<Cat> = mutableSetOf() // every person can adopt cats, and as many as they want!
) {

    /**
     * Method to add an adopted feline to a person
     * @param cat instance of Cat class
     * @return [Unit]
     */
    fun addAdoptedCat(cat: Cat) {
        cats.add(cat)
    }

    /**
     * @param: [other] parameter to be compared to the current instance
     * In this case for simplicity I decided to use firstName and lastName
     * also
     */
    override fun equals(other: Any?): Boolean {
        if (other is Person) {
            return this.id == other.id
        }
        return false
    }

    /**
     * Overriding default hashCode method
     * @return [Int] hashCode
     */
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        val sb = StringBuilder(
            """
            Name: $firstName, $lastName
            Phone: $phoneNumber
            Email: $email
            CatsAdopted:[
        """.trimIndent()
        )
        sb.append("\n")
        cats.forEach {
            sb.append(" - ").append(it).append("\n")
        }
        sb.append("]")
        return sb.toString()

    }


}