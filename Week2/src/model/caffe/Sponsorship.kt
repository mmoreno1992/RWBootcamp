package model.caffe

/**
 * @param: [patronId] id of the customer
 * @param: [catId] id of the cat for sponsoring
 * @author: Mario
 */
data class Sponsorship(
    val patronId: String,
    val catId: String
)