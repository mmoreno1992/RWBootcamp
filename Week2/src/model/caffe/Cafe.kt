package model.caffe

import model.people.Employee
import repositories.ShelterRepository

/**
 * Class for representing a Cafe
 * @author Mario
 */
class Cafe {

    /**
     * receiptsByDay repository of our Week
     */
    private val receiptsByDay = mutableMapOf(
        "Monday" to mutableSetOf<Receipt>(),
        "Tuesday" to mutableSetOf<Receipt>(),
        "Wednesday" to mutableSetOf<Receipt>(),
        "Thursday" to mutableSetOf<Receipt>(),
        "Friday" to mutableSetOf<Receipt>(),
        "Saturday" to mutableSetOf<Receipt>(),
        "Sunday" to mutableSetOf<Receipt>()
    )

    /**
     * Determine the total number of transactions
     * @param: [day] indicates the day of the week we want to check
     * @return: void, only prints the number of transactions or zero if there are
     * no transactions for the given day
     */
    fun showTotalNumberOfReceiptsForDay(day: String) {
        val receiptForDay = receiptsByDay[day]?.size ?: 0 // wrong day inserted!
        println("${27.toChar()}[31m On $day you made $receiptForDay transactions!${27.toChar()}[0m")
    }

    /**
     * Determine the total number of DISTINCT customers (both employees
     * and regular patrons)
     * @param: [day] indicates the day of the week we want to check
     * @return: [Unit], only prints the number of distinct customers
     */
    fun showTotalNumberOfCustomersForDay(day: String) {
        val numberOfDistinctCustomers =
            receiptsByDay[day]?.map { it.patron }?.toSet() ?: setOf()
        println("${27.toChar()}[31m On $day you had ${numberOfDistinctCustomers.size} DISTINCT customers! ${27.toChar()}[0m")

    }

    /**
     * Determine the total number of customers (that aren't employees)
     * @param: [day] indicates the day of the week we want to check
     * @return: void, only prints the number of non employee customers
     */
    fun showTotalNumberOfNonEmployeePatrons(day: String) {
        val numberOfDistinctNonEmployeePatrons =
            receiptsByDay[day]?.map { it.patron }?.filter { it !is Employee }?.toSet() ?: setOf()
        println("${27.toChar()}[31m On $day you had ${numberOfDistinctNonEmployeePatrons.size} non employee patrons :) ${27.toChar()}[0m")
    }

    /**
     * Method for saving the Receipt
     * @param receipt Receipt to save
     * @param day String indicating the day for saving
     */
    fun saveReceipt(receipt: Receipt, day: String) {
        val setOfDay = receiptsByDay[day]
        setOfDay?.add(receipt) ?: println("The receipt has not been added because you indicated a wrong day :( $day.")
    }

    /**
     * Show Total Number of Adoptions for every Shelter
     * @return [Unit]
     */
    fun showTotalNumberOfAdoptionsPerShelter() {
        for ((k, v) in ShelterRepository.shelters) {
            println("${27.toChar()}[31mCats adopted in Shelter ${k}, number of cats: ${v.adoptedCats.size}${27.toChar()}[0m")
        }
    }

    /**
     * Method for printing list of catas that are unadopted and unsponsored
     * Loops through the map of shelters and prints the set of every shelter
     * @return [Unit]
     */
    fun showListOfUnadoptedAndUnsponsoredCatsAvailableAtCafe() {
        for ((k, v) in ShelterRepository.shelters) {
            println("${27.toChar()}[31mCats available for being adopted in  ${k} and don't have sponsors, number of cats: ${v.unadoptedAndUnsponsoredCats().size}${27.toChar()}[0m")
            println("${v.unadoptedAndUnsponsoredCats()}")
        }
    }

    /**
     * @param top indicates number of most popular cats the user wants to
     * see, if it encounters this number shows it otherwise it prints less data
     * @return: [Unit] does not return anything
     */
    fun showMostPopularCatsByShelter(top: Int) {
        for ((k, v) in ShelterRepository.shelters) {
            val popularCats = v.mostPopularCats(top)
            println("${27.toChar()}[31m$k${27.toChar()}[0m, Checking for top $top, List size: ${popularCats.size} ")
            popularCats.forEach { println("Cat's name : ${ShelterRepository.findCatById(it.first)}, number of sponsors: ${it.second}") }
            println()
        }
    }


    /**
     * Method for printing the list of cats that are unadopted but have sponsors
     * Loops through the maps of shelters and prints the data of every item
     * @return [Unit]
     */
    fun showListOfUnadoptedButSponsoredCatsAvailableAtCafe() {
        for ((k, v) in ShelterRepository.shelters) {
            println("${27.toChar()}[31mCats that have sponsors but are available for being adopted in ${k}, number of cats: ${v.sposoredButUnadopted().size}${27.toChar()}[0m")
            println("${v.sposoredButUnadopted()}")
        }
    }

    /**
     * Method for returning top ten products
     * @param day String that indicates the String used for identifying the day
     * @return [List] containing the top ten products
     */
    fun topTenSellingItemsPerDay(day: String): List<Pair<String, Int>> {
        val setOfDay = receiptsByDay[day]
        var result: List<Pair<String, Int>> = listOf()
        if (setOfDay != null) {
            result = setOfDay.flatMap { list ->
                list.items
            }.groupingBy { it.product.id }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
        }
        return result.take(10)
    }

    /**
     * @param receipt instance or Receipt to be printed
     * @return [Unit] does not return anything
     */
    fun showReceiptsByDay(day: String) {
        val setOfDay = receiptsByDay[day]
        println("Printing receipts of $day")
        setOfDay?.forEach {
            println(it)
        }
    }

}