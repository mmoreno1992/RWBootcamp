package controller

import Gender
import model.animals.Cat
import model.caffe.Product
import model.people.Employee
import model.people.Patron
import model.shelter.Shelter
import repositories.PeopleRepository
import repositories.ProductRepository
import repositories.ShelterRepository
import java.time.LocalDate


/**
 * Utility class for filling the repositories with Dummy Data
 */
fun fillShelterData() {
    ShelterRepository.addShelter(Shelter("Guatemalan Shelter", "Guatemala City", "502"))
    ShelterRepository.addShelter(Shelter("Salvadorian Shelter", "El Salvador", "503"))
}

fun fillCatData() {
    val genders = listOf("MASCULINE", "FEMININE")
    val availableShelters = ShelterRepository.shelters.keys.toList()

    //Creating 100 beautiful cats :D and assigning them randomly getting an entry from the Map of Shelters
    for (i in 1..20) {
        val randomShelterId = availableShelters.random()
        //Variable temp data
        val temp = Cat(
            name = "Pretty Cat No. $i",
            sex = Gender.valueOf(genders.random()),
            shelterId = randomShelterId,
            breed = "Pretty"
        )
        ShelterRepository.addCatToShelter(
            temp
        )

        //Add the same instance previously created to our repository
        ShelterRepository.shelters[randomShelterId]?.addCat(temp)
    }
}

fun fillPeopleData() {

    //Fill patrons
    PeopleRepository.addPerson(
        Patron(
            firstName = "Patron 1",
            lastName = "Custom 1",
            email = "patron1@example.com",
            phoneNumber = "1"
        )
    )
    PeopleRepository.addPerson(
        Patron(
            firstName = "Patron 2",
            lastName = "Custom 2",
            email = "patron2@example.com",
            phoneNumber = "2"
        )
    )
    PeopleRepository.addPerson(
        Patron(
            firstName = "Patron 3",
            lastName = "Custom 3",
            email = "patron3@example.com",
            phoneNumber = "3"
        )
    )
    PeopleRepository.addPerson(
        Patron(
            firstName = "Patron 4",
            lastName = "Custom 4",
            email = "patron4@example.com",
            phoneNumber = "4"
        )
    )
    PeopleRepository.addPerson(
        Patron(
            firstName = "Patron 5",
            lastName = "Custom 5",
            email = "@patron5@example.com",
            phoneNumber = "5"
        )
    )

    //Fill employees
    PeopleRepository.addPerson(
        Employee(
            firstName = "John",
            lastName = "Walker",
            email = "jwalker@example.com",
            phoneNumber = "123",
            salary = 2000.0,
            socialSecurityNumber = "",
            hireDate = LocalDate.of(2020, 1, 6)
        )
    )
    PeopleRepository.addPerson(
        Employee(
            firstName = "Charles", lastName = "Dut", email = "dut@example.com", phoneNumber = "456", salary = 2000.0,
            socialSecurityNumber = "", hireDate = LocalDate.of(2020, 4, 15)
        )
    )

}

fun fillProductData() {
    //Random Products and also prices
    ProductRepository.addProduct(Product.Drink(theName = "Coffee", thePrice = 2.0))
    ProductRepository.addProduct(Product.Drink(theName = "Coke", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Water", thePrice = 1.0))
    ProductRepository.addProduct(Product.Drink(theName = "Beer", thePrice = 1.5))
    ProductRepository.addProduct(Product.Food(theName = "Chicken Hamburguer", thePrice = 15.0))
    ProductRepository.addProduct(Product.Drink(theName = "Sandwich", thePrice = 2.0))
    ProductRepository.addProduct(Product.Drink(theName = "Fish", thePrice = 20.0))
    ProductRepository.addProduct(Product.Drink(theName = "Pepsi", thePrice = 3.5))
    ProductRepository.addProduct(Product.Drink(theName = "Fish Hamburguer", thePrice = 16.0))
    ProductRepository.addProduct(Product.Drink(theName = "Salad", thePrice = 50.0))
    ProductRepository.addProduct(Product.Drink(theName = "Fries", thePrice = 6.0))
    ProductRepository.addProduct(Product.Drink(theName = "Fried Chicken", thePrice = 25.0))
    ProductRepository.addProduct(Product.Drink(theName = "Cheesecake", thePrice = 10.0))
    ProductRepository.addProduct(Product.Drink(theName = "Onion Rings", thePrice = 12.0))
    ProductRepository.addProduct(Product.Drink(theName = "Chocolate", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Incaparina", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Milk", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Tea", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Cappucino", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Bacon", thePrice = 4.0))
    ProductRepository.addProduct(Product.Drink(theName = "Beans", thePrice = 4.0))
}