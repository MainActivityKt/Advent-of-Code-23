
fun getHandToBid(input: List<String>): Map<String, Int> {
    // takes the input and returns a map of hand-to-bid
    val handBidMap = mutableMapOf<String, Int>()
    input.forEach { row ->
        row.split(" ").apply {
            handBidMap[this.first()] = this.last().toInt()
        }
    }
    return handBidMap
}

fun getTotalWinning(handBidMap: Map<String, Int>, sortedHands: List<String>):Int {
    // takes a map of -hand-to-bid and the list of hands sorted by its value, counts and returns the total winnings
    var winnings = 0
    sortedHands.forEachIndexed { i, h ->
        val winning = handBidMap[h]!! * (i + 1)
        winnings += winning
    }
    return winnings
}

fun String.handScore():Double {
    val distinct = this.toSet().size
    if (distinct == 2) {
        // handle the case when the hand is either four-of-a-kind or a full-house
        var value = false
        this.forEach { c ->
            if (this.count { it == c } == 2) return distinct + 0.5
            // if it's full-house, increase score by 0.5
        }
    }
    if (distinct == 3) {
        //  handle the cae when the hand is either three-of-a-kind or two-pair
        var isThreeOfAKind = false
        this.forEach { c ->
            if (this.count { it == c } == 3) isThreeOfAKind = true
        }
        return if (isThreeOfAKind) distinct.toDouble() else distinct + 0.5
        // if it's three-of-a-kind, return score else increase it by 0.5
    }
    return distinct.toDouble()
    // for any other cases, just return the number of distinct items as score
}

fun main() {
    fun part1(input: List<String>): Int {
        val handBidMap = getHandToBid(input)
        val handComparator = Comparator<String> { card1, card2 ->
            val card1Score = card1.handScore()
            val card2Score = card2.handScore()
            if (card1Score > card2Score) 1 else if (card1Score < card2Score) -1 else 0
            // manually check the values because handScore is a double
        }
        val cardComparator = object: Comparator<String> {
            val cards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
            override fun compare(card1: String, card2: String): Int {
                for (i in 0..4) {
                    if (card1[i] != card2[i]) return cards.indexOf(card1[i]) - cards.indexOf(card2[i])
                }
                return 0 // if the card is of an unknown type, return 0 [ unreachable line ]
            }
        }
        val sortedHands = handBidMap.keys.sortedWith(handComparator.then(cardComparator)).reversed()
        return getTotalWinning(handBidMap, sortedHands)
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day07")
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == -1)

    part1(input).println()
//    part2(input).println()
}