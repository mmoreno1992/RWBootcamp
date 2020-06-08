package repositories

import model.people.Person


/**
 * Singleton to work as a unique repository for the people application data
 * @author: Mario
 */
object PeopleRepository {
    val people = mutableSetOf<Person>()

    /**
     * @param: [person] instance of Person to be added to the set [people]
     */
    fun addPerson(person: Person) = people.add(person)
}