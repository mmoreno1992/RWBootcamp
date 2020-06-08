package repositories

import model.caffe.Product

/**
 * Singleton to work as a unique repository for the product application data
 * @author: Mario
 */
object ProductRepository {
    val availableProducts = mutableSetOf<Product>()

    /**
     * @param product instdance of Product to be added to the available repository
     */
    fun addProduct(product: Product) = availableProducts.add(product)

    fun getProduct(productId: String) = availableProducts.find { it.id == productId }
}