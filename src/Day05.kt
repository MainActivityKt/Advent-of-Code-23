

fun main() {
    fun part1(input: List<String>): Int {
        return -1
    }
    fun part2(input: List<String>): Any {
        return -2
    }
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    check(part1(testInput) == -1)
    check(part2(testInput) == -1)

    part1(input).println()
    part2(input).println()

}