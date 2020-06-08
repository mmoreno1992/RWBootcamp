package model.caffe

import java.util.*

/**
 * Sealed class for defining the possible options
 */
sealed class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Double
) {

    class Drink(thePrice: Double, theName: String) : Product(price = thePrice, name = theName)
    class Food(thePrice: Double, theName: String) : Product(price = thePrice, name = theName)

    /**
     * Overriding equals default method
     * @param other instance to be compared to
     * @return [Boolean] indicates if this instance is equivalent to the compared
     */
    override fun equals(other: Any?): Boolean {
        if (other is Product)
            return this.id == other.id
        return false
    }

    /**
     * Overriding default hashCopde
     * @return [Int] hashCode of this object
     */
    override fun hashCode(): Int {
        return id.hashCode()
    }

    /**
     * Overriding default String representation of the object
     * @return [String]
     */
    override fun toString(): String {
        return "Item: $id, Desc: $name ."
    }
}