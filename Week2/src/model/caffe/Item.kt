package model.caffe

/**
 * Class that serves as a line of a receipt
 * @author Mario
 */
data class Item(val product: Product, var quantity: Int) {
    override fun toString(): String {
        return """
            {
                productId: ${product.id},
                desc: ${product.name}
                quantity: ${quantity}
                normal price: ${product.price} 
            }
        """
    }
}