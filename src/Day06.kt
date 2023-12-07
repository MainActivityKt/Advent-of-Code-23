
class ToyBoat(input: List<String>) {
    private val speeds = mutableListOf<Int>()
    private val records = mutableListOf<Int>()
    private var errorMargin: Int = 1

    init {
        input.forEachIndexed { index: Int, s: String ->
            val values = s.split(" ").mapNotNull { it.trim().toIntOrNull() }
            if (index == 0) speeds.addAll(values) else records.addAll(values)
        }
    }

    fun getMargin(): Int {
        speeds.zip(records).forEach {
            val time = it.first
            val highest = it.second
            var num = 0

            for (i in 0..time) {
                val currentHighest = (time - i) * i
                if (currentHighest > highest) {
                    num +=1
                }
            }
            if (num != 0) {
                errorMargin *= num
            }
        }
        return errorMargin
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return ToyBoat(input).getMargin()
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day06")
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == -1)

    part1(input).println()
//    part2(input).println()
}