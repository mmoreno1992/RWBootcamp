package model.people

import java.time.LocalDate

class Employee(
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    val salary: Double,
    val socialSecurityNumber: String,
    val hireDate: LocalDate
) : Person(firstName = firstName, lastName = lastName, email = email, phoneNumber = phoneNumber) {

    override fun toString(): String {
        return "" // TODO format the data in any way you want! :]
    }

    /**
     * Prints a time of clocking in, in a nice format.
     *
     * Hint: to get time, you can create a `Date` object. Use SimpleDateFormatter to format the time!
     * */
    fun clockIn() {
        println("Checking in ${LocalDate.now()}")
    }

    // TODO same as above, but times for clocking out!
    fun clockOut() {
        println("Checking out ${LocalDate.now()}")
    }
}