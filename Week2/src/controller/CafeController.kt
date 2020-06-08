package controller

import model.caffe.Cafe
import model.caffe.Receipt
import model.people.Patron
import repositories.ProductRepository
import repositories.ShelterRepository

/**
 * Entry point of the program the Controller
 */
fun main() {

    println("Welcome! It's good to see you trying this software :)")
    println("Generating data...")
    //Creating the Cafe
    val cafe = Cafe()
    fillShelterData()
    fillCatData()
    fillProductData()

    println("Shelters generated and their Cats are: ")
    for ((k, v) in ShelterRepository.shelters) {
        println("$k = $v")
    }

    val customer1 =
        Patron(firstName = "Mario", lastName = "Moreno", email = "moreno.mario@gmail.com", phoneNumber = "35308491")
    val customer2 =
        Patron(firstName = "Humberto", lastName = "Guzman", email = "humberto.guzman@gmail.com", phoneNumber = "123456")
    val customer3 =
        Patron(firstName = "Selvyn", lastName = "Barrientos", email = "sbarrientos@gmail.com", phoneNumber = "111111")
    val customer4 =
        Patron(firstName = "Eddy", lastName = "Posadas", email = "sposadas@gmail.com", phoneNumber = "22222222")

    println()
    println("--------------------------------------------------------------------")
    println("Total number of unadopted cats, before adopting (starting the day).")
    cafe.showListOfUnadoptedAndUnsponsoredCatsAvailableAtCafe()

    val receipt = Receipt(patron = customer1)
    receipt.addItem(ProductRepository.availableProducts.random(), 1000)
    receipt.addItem(ProductRepository.availableProducts.random(), 5)
    receipt.addItem(ProductRepository.availableProducts.random(), 6)
    receipt.addItem(ProductRepository.availableProducts.random(), 3)
    receipt.addItem(ProductRepository.availableProducts.random(), 2)
    receipt.addCatToAdopt(ShelterRepository.shelters["Guatemalan Shelter"]!!.availableCats.random())

    cafe.saveReceipt(receipt, "Monday")
    // cafe.showReceiptAdded(receipt)

    val receipt2 = Receipt(patron = customer2)
    receipt2.addItem(ProductRepository.availableProducts.random(), 2)
    receipt2.addItem(ProductRepository.availableProducts.random(), 2)
    receipt2.addItem(ProductRepository.availableProducts.random(), 1)
    receipt2.addItem(ProductRepository.availableProducts.random(), 2)
    receipt2.addItem(ProductRepository.availableProducts.random(), 3)
    cafe.saveReceipt(receipt2, "Monday")
    receipt2.addCatToAdopt(ShelterRepository.shelters["Guatemalan Shelter"]!!.availableCats.random())
    //  cafe.showReceiptAdded(receipt2)

    val receipt3 = Receipt(patron = customer1)
    receipt3.addItem(ProductRepository.availableProducts.random(), 1)
    receipt3.addItem(ProductRepository.availableProducts.random(), 4)
    receipt3.addItem(ProductRepository.availableProducts.random(), 3)
    receipt3.addItem(ProductRepository.availableProducts.random(), 2)
    receipt3.addItem(ProductRepository.availableProducts.random(), 1)
    receipt3.addCatToSponsor(ShelterRepository.shelters["Salvadorian Shelter"]!!.availableCats.random())
    cafe.saveReceipt(receipt3, "Monday")

    //cafe.showReceiptAdded(receipt3)

    println()
    println("${27.toChar()}[32mGenerating Cafe's Report${27.toChar()}[0m")

    println("Total number of transactions: ")
    cafe.showTotalNumberOfReceiptsForDay("Monday")
    println()

    println("Total number of DISTINCT customers (both employees and regular patrons)")
    cafe.showTotalNumberOfCustomersForDay("Monday")
    println()

    println("Total number of NON-EMPLOYEE patrons")
    cafe.showTotalNumberOfNonEmployeePatrons("Monday")
    println()

    println("Total number of adoptions, per shelter")
    cafe.showTotalNumberOfAdoptionsPerShelter()
    println()

    println("Total number of Unadopted And Unsponsored cats (but ready for being adopted :D) , staying at the Cafe")
    cafe.showListOfUnadoptedAndUnsponsoredCatsAvailableAtCafe()
    println()

    println("Total number of Unadopted But Sponsored cats, staying at the Cafe")
    cafe.showListOfUnadoptedButSponsoredCatsAvailableAtCafe()
    println()

    println("Top ten selling Menu Items")
    cafe.topTenSellingItemsPerDay("Monday")
        .forEach {
            println("${27.toChar()}[32m - Product: ${ProductRepository.getProduct(it.first)?.name}, Customers bought it: ${it.second} (times)${27.toChar()}[0m")
        }
    println()

    println("List of the most popular cats by Shelter")
    cafe.showMostPopularCatsByShelter(3)
    println()

    println("Show Adopted Cars by Customer1 \n$customer1")

    cafe.showReceiptsByDay("Monday")

}

