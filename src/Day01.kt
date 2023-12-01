
val wordToDigit = mapOf<String, Char>(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
)

fun findSum(input: List<String>, includeSpelled: Boolean = false): Int {
    var sum = 0
    input.forEach {
        var currentNumber = ""
        currentNumber += findFirstDigit(it, includeSpelled)
        currentNumber += findLastDigit(it, includeSpelled)
        sum += currentNumber.toInt()
    }
    return sum
}

fun findFirstDigit(word: String, includeSpelled: Boolean = false): Char {
    val firstDigit = word.firstOrNull { it.isDigit() }  //find first digit according to part1
    if (!includeSpelled) {
        return firstDigit!!
    }

    var firstSpelledDigit = "" to Int.MAX_VALUE  // store the first spelled digit and itx index in a Pair
    wordToDigit.keys.forEach {
        if (it in word) {
            if (word.indexOf(it) < firstSpelledDigit.second) {
                firstSpelledDigit = it to word.indexOf(it)
            }
        }
    }
    if (firstDigit != null && word.indexOf(firstDigit) < firstSpelledDigit.second) {
        return firstDigit
    }

    return wordToDigit[firstSpelledDigit.first]!!
}

fun findLastDigit(word: String, includeSpelled: Boolean = false): Char {
    val lastDigit = word.lastOrNull() { it.isDigit() }  //find the last digit according to part1
    if (!includeSpelled) {
        return lastDigit!!
    }

    var lastSpelledDigit =  "" to Int.MIN_VALUE
    wordToDigit.keys.forEach {// iterate over list of spelled digits from one to nine
        if (it in word) {
            if (word.lastIndexOf(it) > lastSpelledDigit.second) {
                lastSpelledDigit = it to word.lastIndexOf(it)
            }
        }
    }
    if (lastDigit != null && word.lastIndexOf(lastDigit) > lastSpelledDigit.second) {
        return lastDigit
    }

    return wordToDigit[lastSpelledDigit.first]!!
}


fun main() {
    fun part1(input: List<String>): Int {
        return findSum(input)
    }

    fun part2(input: List<String>): Int {
        return findSum(input, true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_1_test")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_2_test")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
