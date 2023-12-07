
class ToyBoat(val input: List<String>, private val handleKerning: Boolean = false) {
    private lateinit var errorMargin: Number

    fun getMargin(): Number {
        if (!handleKerning) {
            updateValues(input)
        } else {
            updateValues(input, true)
        }
        return errorMargin
    }

    private fun updateValues(input: List<String>, combineSpaces: Boolean = false) {
        if (!combineSpaces) {
            val listOfTimes = input[0].split(" ").mapNotNull { it.toIntOrNull() }
            val records = input[1].split(" ").mapNotNull { it.toIntOrNull() }
            errorMargin = combineMargins(listOfTimes, records)
        } else {
            val time = input.first().split(":").last().filter { it.isDigit() }
            val record = input[1].split(":").last().filter { it.isDigit() }
            errorMargin = getWinningWays(time.toLong(), record.toLong())
        }
    }

    private fun combineMargins(listOfTimes: List<Int>, records: List<Int>): Int {
        var current = 1
        listOfTimes.zip(records).forEach {
            val time = it.first
            val highest = it.second
            val num = getWinningWays(time, highest)
            if (num != 0) {
                current *= num.toInt()
            }
        }
        return current
    }

    private fun getWinningWays(time: Number, highest: Number): Number {
        var num = 0L
        for (i in 0..time.toLong()) {
            val currentHighest = (time.toLong() - i) * i
            if (currentHighest > highest.toLong()) {
                num +=1
            }
        }
        return num
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return ToyBoat(input).getMargin().toInt()
    }

    fun part2(input: List<String>): Long {
        return ToyBoat(input, true).getMargin().toLong()
    }

    val input = readInput("Day06")
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503L)

    part1(input).println()
    part2(input).println()
}