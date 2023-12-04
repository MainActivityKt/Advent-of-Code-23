class Gear(val input: List<String>) {

    private val gearRatios = mutableListOf<Int>()
    private val numbers = mutableListOf<Int>()

    fun findGearRatios(): List<Int> {
        input.forEachIndexed { index, row ->
            val upperRow = getUpperRow(index)
            val lowerRow = getLowerRow(index)
            row.forEachIndexed { i, c ->
                if (c.isStar()) {
                    searchUpperRow(upperRow, i)
                    searchLowerRow(lowerRow, i)
                    searchAround(row, i)
                    if (numbers.size == 2) {
                        gearRatios.add(numbers[0] * numbers[1])
                        println("row index:${index.inc()} star index:$i: gears: ${numbers[0]} & ${numbers[1]} = ${numbers[0] * numbers[1]}")

                    }
                    numbers.clear()
                }
            }
        }
        return gearRatios
    }

    private fun searchLowerRow(lowerRow: String, index: Int): Int {
        if (lowerRow.isEmpty()) return -1
        if (index != 0 && lowerRow[index - 1].isDigit()) {  // Search lower left character
            addNumber(lowerRow, index - 1)
        } else if (index != lowerRow.lastIndex && lowerRow[index + 1].isDigit()) {  // Search lower right character
            addNumber(lowerRow, index + 1)
        } else if (lowerRow[index].isDigit()) { // Search bottom character
            addNumber(lowerRow, index)
        }
        return 0
    }

    private fun searchUpperRow(upperRow: String, index: Int): Int {
        if (upperRow.isEmpty()) return -1
        if (index != 0 && upperRow[index - 1].isDigit()) {  // Search upper left character
            addNumber(upperRow, index - 1)
            return 0
        } else if (index != upperRow.lastIndex && upperRow[index + 1].isDigit()) {  // Search upper right character
            addNumber(upperRow, index + 1)
        } else if (upperRow[index].isDigit()) { // Search top character
            addNumber(upperRow, index)
        }
        return 0
    }

    private fun searchAround(row: String, index: Int) {
        if (index != 0 && row[index - 1].isDigit()) {
            addNumber(row, index - 1)
        }
        if (index != row.lastIndex && row[index + 1].isDigit()) {
            addNumber(row, index + 1)
        }
    }

    private fun addNumber(row: String, index: Int) {
        var ratio = ""
        if (index == 0 || !row[index - 1].isDigit()) {
            for (i in index..row.lastIndex) {
                if (row[i].isDigit()) ratio += row[i] else break
            }
        } else if (index == row.lastIndex || !row[index + 1].isDigit()) {
            for (i in index downTo 0) {
                if (row[i].isDigit()) ratio = row[i] + ratio else break
            }
        } else {
            for (i in index - 1..index + 1) {
                if (row[i].isDigit()) ratio += row[i] else break
            }

        }
        numbers.add(ratio.toInt())
    }

    private fun getUpperRow(index: Int): String {
        return input.getOrNull(index - 1) ?: ""
    }

    private fun getLowerRow(index: Int): String {
        return input.getOrNull(index + 1) ?: ""
    }
}

fun Char.isStar(): Boolean = this == '*'

fun main() {
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    Gear(testInput).findGearRatios().sum().println()


}