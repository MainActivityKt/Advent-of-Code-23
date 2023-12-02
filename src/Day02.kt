
fun findValidIDs(input: List<String>): List<Int> {
    val validIDs = mutableListOf<Int>()
    input.forEach { if (isCubeSetValid(it)) { validIDs.add(parseID(it)) } }
    return validIDs
}

fun parseID(text: String): Int {
    return text.split(":").first().filter { it.isDigit() }.toInt()
}

fun isCubeSetValid(input: String): Boolean {
    val text = input.split(":").last().trim().split(";")
    text.forEach {
        val cube = mutableMapOf <String, Int> ("red" to 0, "green" to 0, "blue" to 0)
        it.split(",").forEach { s ->//  3 blue
            val color = s.filter { it.isLetter() }
            val amount = s.filter { it.isDigit() }.toInt()
            cube[color] = amount
        }
        if (!isCubeValid(cube)) return false
    }
    return true
}

fun isCubeValid(cube: Map<String, Int>): Boolean {
    return cube["red"]!! <= 12 && cube["green"]!! <= 13 && cube["blue"]!! <= 14
}

fun findMinSet(input: String): Map<String, Int> {
    val text = input.split(":").last().trim().split(";")
    val cube = mutableMapOf <String, Int> ("red" to 0, "green" to 0, "blue" to 0)
    text.forEach {
        it.split(",").forEach { s ->//  3 blue
            val color = s.filter { it.isLetter() }
            val amount = s.filter { it.isDigit() }.toInt()
            if (cube[color]!! < amount) {
                cube[color] = amount
            }
        }
    }
    return cube
}

fun calculateSumOfPowers(cubes: List<String>): Int {
    var totalPower = 0
    cubes.forEach {
        val minCube = findMinSet(it)
        totalPower += (minCube["red"]!! * minCube["green"]!! * minCube["blue"]!!)
    }
    return totalPower
}

fun main() {
    fun part1(input: List<String>): Int {
        return findValidIDs(input).sum()
    }
    fun part2(input: List<String>): Int {
        return calculateSumOfPowers(input)
    }

    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    part1(input).let(::println)
    part2(input).let(::println)


}