class ScratchCardsWithCopies(private val input: List<String>) {
    private val instances = mutableMapOf<Int, Int>()
    private var luckyNumbers = listOf<Int>()
    private var numbers = listOf<Int>()
    var currentCard = 0

    fun calculatePoints(): Any {
        input.forEach { row ->
            var currentPoint = 0
            parseRow(row)
            updateInstanceInitial()
            luckyNumbers.forEach {
                if (it in numbers) {
                    currentPoint += 1
                }
            }
            val startIndex = currentCard + 1
            val endIndex = currentCard + currentPoint
            for (i in startIndex..endIndex) {
                updateCardInstance(i)
            }

        }
        return instances.values.sum()
    }

    private fun updateInstanceInitial() {
        if (instances.containsKey(currentCard)) {
            instances[currentCard] = instances[currentCard]!! + 1
        } else {
            instances.put(currentCard, 1)
        }
    }

    private fun updateCardInstance(card: Int) {
        if (instances.containsKey(card)) {
            instances[card] = instances[card]!! + (1 * instances[currentCard]!!)
        } else if (instances.containsKey(currentCard)) {
            instances[card] = instances[currentCard]!!
        } else {
            instances.put(currentCard, 1)
        }

    }


    private fun parseRow(row: String) {
        updateLuckyNumbers(row.split("|").first())
        updateOwnedNumbers(row.split("|").last())

    }

    private fun updateLuckyNumbers(row: String) {
        row.split(":").apply {
            currentCard = first().split(" ").last().trim().toInt()
            luckyNumbers = last().split(" ").mapNotNull { it.toIntOrNull() }
        }
    }

    private fun updateOwnedNumbers(row: String) {
        numbers = row.split(" ").mapNotNull { it.toIntOrNull() }
    }

}