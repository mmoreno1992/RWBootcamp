package model.caffe

import model.animals.Cat
import model.people.Employee
import model.people.Person
import java.util.*

/**
 * Class representing a receipt for a Client
 * @author Mario
 */
class Receipt(
    val receiptId: String = UUID.randomUUID().toString(),
    val patron: Person
) {
    val items = mutableListOf<Item>()

    private val createdAt: Date = Date()

    //Computed property
    private val isACatOnReceipt: Boolean
        get() {
            return catsToAdopt.size > 0
        }

    /**
     * I decided to add Collections, so that a person can
     * adopt/sponsor more than one cat at a time :D
     */
    private val catsToAdopt = mutableSetOf<Cat>()
    private val catsToSponsor = mutableSetOf<Sponsorship>()

    /**
     * @param [cat] instance of cat to be added to [catsToAdopt] for adopting for than one feline
     * @return [Unit]
     */
    fun addCatToAdopt(cat: Cat) {
        cat.catAdopted = true
        cat.adoptCat(patron)
        catsToAdopt.add(cat)
    }

    /**
     * @param [cat] instance of cat to get the id and add Sponsorship to [catsToSponsor]
     * @return [Unit]
     */
    fun addCatToSponsor(cat: Cat) {
        val sponsorship = Sponsorship(patron.id, cat.id)
        catsToSponsor.add(sponsorship)
        cat.addSponsorShip(sponsorship)
    }

    /**
     * Add an item (product) to the list of products the customer bought
     * @param product An instance of the sealed class [Product]
     * @param quantity indicates the quantity of the product
     * @return: [Unit]
     */
    fun addItem(product: Product, quantity: Int) {
        items.add(Item(product, quantity))
    }

    /**
     * Overriding default hashCode
     * @return [Int] hashCode of the object
     */
    override fun hashCode(): Int {
        return receiptId.hashCode()
    }

    /**
     * @return: [Double] returns different totals
     * depending on the instance or if the Customer is adopting or not
     * a feline
     * If there is a cat on a receipt or the customer is not an employee
     * the price of the product is 100%
     * If Not is an employee nor it has adopted a feline
     * the price is 85% or equivalent to 15% discount
     */
    val total: Double
        get() {
            if (isACatOnReceipt || patron !is Employee)
                return items.fold(0.0) { acum, item ->
                    item.product.price + acum
                }

            return items.fold(0.00) { acum, item ->
                if (item.product is Product.Food || item.product is Product.Drink) {
                    return (item.product.price * 0.85) + acum
                } else
                    item.product.price + acum
            }


        }

    /**
     * Overriding default equals
     * @param: instance to be compared to
     * @return: [Boolean] indicates if both instances are equivalent or not
     */
    override fun equals(other: Any?): Boolean {
        if (other is Receipt)
            return receiptId == other.receiptId
        return false
    }

    /**
     * Overriding default toString
     * @return: [String] returns a String representation of this instance
     */
    override fun toString(): String {
        val sb = StringBuilder(
            """
            Receipt Id: $receiptId, 
            Patron name: ${patron.firstName} ${patron.lastName},
            Patron email: ${patron.email},
            Created at: ${createdAt},
            Number of items: ${items.size} 
            items: [""".trimIndent()
        )
        items.forEach {
            sb.append(it)
        }
        // sb.append("]\n")
        sb.append("${27.toChar()}[33mReceipt Total --> $total${27.toChar()}[0m")
        return sb.toString()
    }
}