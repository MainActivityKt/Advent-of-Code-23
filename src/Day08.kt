
fun parseValues(input: List<String>): Map<String, List<String>> {
    val map = mutableMapOf<String, List<String>>()
    input.subList(2, input.size).forEach { row ->
        val key = row.split("=").first().trim()
        val values = mutableListOf<String>()
        row.split("=").last().trim().split(",").forEach { str ->
            values.add(str.trim().filter { it.isLetterOrDigit() })
        }
        map[key] = values
    }
    return map
}

fun parseDirections(input: List<String>): String {
    var str = ""
    input.first().forEach { str += if (it == 'L') '0' else '1' }
    return str
}

fun getSteps(nodesMap: Map<String, List<String>>, directions: String, firstKey: String, isFound: (String) -> Boolean): Int {
    var currentNode = nodesMap[firstKey]!!
    var key: String
    var found = false
    var steps = 0
    while (!found) {
        for (i in directions) {
            key = currentNode[i.digitToInt()]
            steps += 1
            if (isFound(key)) {
                found = true
                break
            }
            currentNode = nodesMap[key]!!
        }
    }
    return steps
}

fun calculateSteps(input: List<String>, forGhosts: Boolean = false): Long {
    fun findLCM(a: Long, b: Long): Long {   //https://www.baeldung.com/kotlin/lcm
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun findLCMOfListOfNumbers(numbers: List<Int>): Long {
        var result = numbers[0].toLong()
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i].toLong())
        }
        return result
    }

    val map = parseValues(input)
    val directions = parseDirections(input)
    val results = mutableListOf<Int>()
    if (!forGhosts) {
        return getSteps(map, directions, "AAA") { it == "ZZZ" }.toLong()
    } else {
        val keys = map.keys.filter { it.endsWith('A') }.toMutableList()
        keys.forEach {  key ->
            results.add(getSteps(map, directions, key) { it.endsWith("Z") })
        }
    }
    return findLCMOfListOfNumbers(results)
}



fun main() {
    fun part1(input: List<String>): Int {
        return calculateSteps(input).toInt()
    }

    fun part2(input: List<String>): Long {
        return calculateSteps(input, true)
    }

    val input = readInput("Day08")
    val testInput1 = readInput("Day08_test")
    val testInput2 = readInput("Day08_2_test")
    check(part1(testInput1) == 6)
    check(part2(testInput2) == 6L)

    part1(input).println()
    part2(input).println()

}