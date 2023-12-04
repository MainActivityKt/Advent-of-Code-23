

fun main() {
    fun part1(input: List<String>): Int {
        return -1
    }

    fun part2(input: List<String>): Any {
        return -1
    }

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    check(part1(testInput) == 4361)

    part1(input).println()
//    part2(input).println()

}