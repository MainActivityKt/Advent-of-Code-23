
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

fun String.handScore(treatJAsJoker: Boolean):Double {
    val card: String
    if (treatJAsJoker) {
        val max = this.groupingBy { it }.eachCount().maxBy { if (it.key != 'J') it.value else 0}.key
        card = this.replace('J', max)
    } else {
        card = this
    }
    val distinct = card.toSet().size

    if (distinct == 2) {
        // handle the case when the hand is either four-of-a-kind or a full-house
        card.forEach { c ->
            if (card.count { it == c } == 2) return distinct + 0.5
            // if it's full-house, increase score by 0.5
        }
    }
    if (distinct == 3) {
        //  handle the cae when the hand is either three-of-a-kind or two-pair
        var isThreeOfAKind = false
        card.forEach { c ->
            if (card.count { it == c } == 3) isThreeOfAKind = true
        }
        return if (isThreeOfAKind) distinct.toDouble() else distinct + 0.5
        // if it's three-of-a-kind, return score else increase it by 0.5
    }
    return distinct.toDouble()
    // for any other cases, just return the number of distinct items as score
}

fun getTotalWinning(handBidMap: Map<String, Int>, treatJAsJoker: Boolean = false):Int {

    val handComparator = Comparator<String> { card1, card2 ->
        val card1Score = card1.handScore(treatJAsJoker)
        val card2Score = card2.handScore(treatJAsJoker)
        if (card1Score > card2Score) 1 else if (card1Score < card2Score) -1 else 0
        // manually check the values because handScore is a double
    }
    val cardComparator = object: Comparator<String> {
        val cards = if (!treatJAsJoker) listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        else listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        override fun compare(card1: String, card2: String): Int {
            for (i in 0..4) {
                if (card1[i] != card2[i]) return cards.indexOf(card1[i]) - cards.indexOf(card2[i])
            }
            return 0 // if the card is of an unknown type, return 0 [ unreachable ]
        }
    }
    val sortedHands = handBidMap.keys.sortedWith(handComparator.then(cardComparator)).reversed()
    var winnings = 0
    sortedHands.forEachIndexed { i, h ->
        val winning = handBidMap[h]!! * (i + 1)
        winnings += winning
    }
    return winnings
}



fun main() {
    fun part1(input: List<String>): Int {
        val handBidMap = getHandToBid(input)
        return getTotalWinning(handBidMap)
    }

    fun part2(input: List<String>): Int {
        val handBidMap = getHandToBid(input)
        return getTotalWinning(handBidMap, true)
    }

    val input = readInput("Day07")
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    part1(input).println()
    part2(input).println()
}