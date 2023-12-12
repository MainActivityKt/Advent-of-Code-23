

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

fun extrapolateNextValue(sequence: MutableList<MutableList<Long>>, extrapolateBackwards: Boolean = false): Long {
    if (!extrapolateBackwards) {
        sequence[sequence.lastIndex] += 0
        for (i in sequence.lastIndex - 1 downTo  0) {
            val nextValue = sequence[i].last() + sequence[i + 1].last()
            sequence[i].add(nextValue)
        }
        return (sequence.first().last())
    } else {
        sequence.last().add(0, 0)
        for (i in sequence.lastIndex - 1 downTo 0) {
            val nextValue = sequence[i].first() - sequence[i + 1].first()
            sequence[i].add(0, nextValue)
        }
        return sequence.first().first()
    }

}

fun getSumOfExtrapolatedValues(input: List<String>, getMoreHistory: Boolean = false): Long {
    var sum = 0L
    input.forEach { row ->
        val currentSequence = findSequence(row)
        sum += extrapolateNextValue(currentSequence, getMoreHistory)
    }
    return sum
}

fun main() {
    fun part1(input: List<String>): Long {
        return getSumOfExtrapolatedValues(input)
    }

    fun part2(input: List<String>): Long {
        return getSumOfExtrapolatedValues(input, true)
    }

    val input = readInput("Day09")
    val testInput = readInput("Day09_test")

    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)

    part1(input).println()
    part2(input).println()
}