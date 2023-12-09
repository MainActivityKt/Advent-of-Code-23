


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
        str += if (it == 'L') '0' else '1'
    }
    return str
}

fun calculateSteps(input: List<String>): Int {
    val map = parseValues(input)
    val directions = parseDirections(input)
    val firstKey = "AAA"

    var currentNode = map[firstKey]!!
    var key: String
    var found = false

    var steps = 0
    while (!found) {
        for (i in directions) {
            key = currentNode[i.digitToInt()]
            steps += 1
            if (key == "ZZZ") {
                found = true
                break
            }
            currentNode = map[key]!!
        }
    }
    return steps
}


fun main() {
    fun part1(input: List<String>): Int {
        return calculateSteps(input)
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day08")
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == -1)

    part1(input).println()
//    part1(input).println()
//    part2(input).println()
}