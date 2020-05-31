import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.lang.StringBuilder

data class Card(val pip: String, val suit: String) {
     val suitIcon: StringBuilder
     val pipValue: Int

    init {
        if (pip.trim().isEmpty() || suit.trim().isEmpty())
            throw IllegalArgumentException("You must specify the PIP and the SUIT")

        suitIcon = when (suit) {
            "C" -> StringBuilder("\u2663")
            "S" -> StringBuilder("\u2660")
            "D" -> StringBuilder("${27.toChar()}[31m\u2666${27.toChar()}[0m")
            "H" -> StringBuilder("${27.toChar()}[31m\u2665${27.toChar()}[0m")
            else ->
                throw IllegalArgumentException("Not a valid suit")
        }

        pipValue = when(pip){
            "A" -> 11
            "K", "Q", "J" -> 10
            else -> {
                try {
                    pip.toInt()
                }catch (e: NumberFormatException){
                    throw IllegalArgumentException("Not a valid pip, please check.")
                }
            }
        }
    }

    fun getCardValue() = pipValue

    override fun toString(): String {
        return (
                """
            .---------------.
            | $pip             |
            |               |
            |               |
            |       $suitIcon       |
            |               |
            |               |
            |             $pip |
            \_______________/
        """.trimIndent()
                )
    }
}