
fun getHandToBid(input: List<String>): Map<String, Int> {
    val handBidMap = mutableMapOf<String, Int>()
    input.forEach { row ->
        row.split(" ").apply {
            handBidMap[this.first()] = this.last().toInt()
        }
    }
    println(handBidMap)
    return handBidMap
}

fun getTotalWinning(handBidMap: Map<String, Int>, sortedHands: List<String>):Int {
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
        var value = false
        this.forEach { c ->
            if (this.count { it == c } == 2) return distinct + 0.5
        }

    }
    if (distinct == 3) {
        var value = false
        this.forEach { c ->
            if (this.count { it == c } == 3) value = true
        }
        return if (value) distinct.toDouble() else distinct + 0.5
    }
    return distinct.toDouble()
}

fun main() {
    fun part1(input: List<String>): Int {
        val handBidMap = getHandToBid(input)
        val sortedHands = mutableListOf<String>()
        val group = handBidMap.keys.groupBy { it.handScore() }.toMutableMap()
        val list = mutableListOf<String>()


        val listOfCards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val comparator = object: Comparator<String> {
            override fun compare(card1: String, card2: String): Int {
                for (i in 0..4) {
                    if (card1[i] != card2[i]) return listOfCards.indexOf(card1[i]) - listOfCards.indexOf(card2[i])
                }
                return 0
            }
        }

        group.forEach { t, u ->
            group[t] = u.sortedWith(comparator.then(comparator.then(comparator)))

        }
        group.toSortedMap().forEach{ (t, u) ->
            sortedHands.addAll(u)
        }

        return getTotalWinning(handBidMap, sortedHands.reversed())
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