package com.mmoreno.mycustomcard.model

/**
 * Data class for holding the custom cards
 * @author Mario
 * @param programmingLanguage Name of the programming language in the card
 * @param a brief description for a person as a developer to be included in the custom card
 * @param resourceName Name of the vector drawable representing the logo of the programming language
 */
data class CustomCard(
    val programmingLanguage: String,
    var experience: String,
    val resourceName: String
) {

    /**
     * Overriding default equals method
     * Two objects are equals depending on its programming language
     * @return [Boolean]
     */
    override fun equals(other: Any?): Boolean {
        if (other is CustomCard)
            return programmingLanguage == other.programmingLanguage
        return false
    }

    /**
     * Overriding default hashCode method
     * @return [Int] hashCode of the object
     */
    override fun hashCode(): Int {
        return programmingLanguage.hashCode()
    }
}