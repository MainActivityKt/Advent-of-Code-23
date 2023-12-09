


fun parseValues(input: List<String>): Map<String, List<String>> {
    val map = mutableMapOf<String, List<String>>()
    input.subList(2, input.size).forEach {
        val key = it.split("=").first().trim()
        val values = mutableListOf<String>()
        it.split("=").last().trim().split(",").forEach { str ->
            values.add(str.trim().filter { it.isLetter() })
        }
        map[key] = values
    }
    return map
}

fun parseDirections(input: List<String>): String {
    var str = ""
    input.first().forEach {
        if (it == 'L') str += '0' else str += '1'
    }
    return str
}

fun main() {
    fun part1(input: List<String>): Int {

        return -1
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day08")
    val testInput = readInput("Day08_test")
    check(part1(testInput) == -1)
    check(part2(testInput) == -1)

    parseDirections(input).println()
    parseValues(input).size.println()
//    part1(input).println()
//    part2(input).println()
}