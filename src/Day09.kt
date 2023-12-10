

fun findSequence(row:String): MutableList<MutableList<Long>> {
    val sequences = mutableListOf<MutableList<Long>>()
    sequences.add(row.split(" ").map { it.toLong() }.toMutableList())
    var lastSequence = sequences.last()

    while (!lastSequence.all { it == 0L }) {
        val newSeq = mutableListOf<Long>()
        for (i in 0..<sequences.last().lastIndex) {
            newSeq.add(sequences.last()[i + 1] - sequences.last()[i])
        }
        sequences.add(newSeq)
        lastSequence = sequences.last()
    }
    return sequences
}

fun findNextValue(sequence: MutableList<MutableList<Long>>): Long {
    sequence[sequence.lastIndex] += 0

    for (i in sequence.lastIndex - 1 downTo  0) {
        val nextValue = sequence[i].last() + sequence[i + 1].last()
        sequence[i].add(nextValue)
    }

    return (sequence.first().last())
}

fun sumNextValues(input: List<String>): Long {
    var sum = 0L
    input.forEach { row ->
        val currentSequence = findSequence(row)
        sum += findNextValue(currentSequence)
    }


    return sum
}

fun main() {
    fun part1(input: List<String>): Int {

        return -1
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day09")
    val testInput = readInput("Day09_test")
//    check(part1(testInput) == -1)
//    check(part2(testInput) == -1)
//    part1(input).println()
//    part2(input).println()
    sumNextValues(input).println()
}