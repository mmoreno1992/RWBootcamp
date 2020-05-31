fun main() {
    println("Welcome to my first project using Kotlin!")
    println("Hope you are ready to start :)")

    println("Getting deck...")
    val deck = createDeck()
    println("Now I am going to deal the hand (with 2 cards)")
    val myHand = dealHand(deck, 2)
    evaluateHandAndPrintResult(myHand)
}

fun evaluateHandAndPrintResult(hand: List<Card>) {
    println("Your hand was: ")
    var sum = 0
    var numberOfAces = 0
    hand.forEach {
        println(it)
        sum += it.pipValue
        if (it.pip == "A")
            numberOfAces++

    }
    println("For a total of: $sum")
    val message =
        when {
            sum == 21 -> "You Win! Perfect Score (21)"
            numberOfAces == 2 -> "You Lose! Just for a unit (Have to Aces) :("
            else -> "You Lose :(, but try again It's free :)"
        }
    println(message)

}


fun dealHand(deck: MutableSet<Card>, howMany: Int): List<Card> {
    val hand = mutableListOf<Card>()
    for (i in 1..howMany) {
        val card = deck.random()
        hand.add(deck.random())
        deck.remove(card)
    }
    return hand
}

fun createDeck(): MutableSet<Card> {
    val deckSet = mutableSetOf<Card>()
    for (pip in Util.pips) {
        for (suit in Util.suits) {
            deckSet.add(Card(pip, suit))
        }
    }
    return deckSet
}