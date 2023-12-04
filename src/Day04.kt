
class ScratchCards(private val input: List<String>) {
    private val points = mutableListOf<Int>()
    private var luckyNumbers = listOf<Int>()
    private var numbers = listOf<Int>()

    fun calculatePoints(): MutableList<Int> {
        input.forEach { row ->
            var currentPoint = 0
            parseRow(row)
            luckyNumbers.forEach {
                if (it in numbers) {
                    currentPoint = updatePoints(currentPoint, currentPoint == 0)
                }
            }
            points.add(currentPoint)
        }
        return points
    }

    private fun parseRow(row: String) {
        updateLuckyNumbers(row.split("|").first())
        updateOwnedNumbers(row.split("|").last())

    }

    private fun updateLuckyNumbers(row: String) {
        luckyNumbers =  row.split(":").last().split(" ").mapNotNull { it.toIntOrNull() }
    }

    private fun updateOwnedNumbers(row: String) {
        numbers = row.split(" ").mapNotNull { it.toIntOrNull() }
    }

    private fun updatePoints(currentPoints: Int, isFirst: Boolean = false): Int {  //debug
        return if (isFirst) 1 else currentPoints * 2
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        return ScratchCards(input).calculatePoints().sum()
    }
    fun part2(input: List<String>): Any {
        return -1
    }
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    check(part1(testInput) == 13)
    check(part2(testInput) == -1)

    part1(input).println()

}