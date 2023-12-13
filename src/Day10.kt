
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun Char.isValidPipe(): Boolean = this in "|-L7F"

class Pipes(val input: List<String>) {
    private var animalRowIndex: Int = 0
    private var animalIndex: Int = 0
    private var maxSteps = 0
    init {
        input.forEachIndexed { index, s -> if (s.contains('S')) animalRowIndex = index  }
        animalIndex = input[animalRowIndex].indexOf('S')
    }

    fun calculateSteps(): Int {
        for (direction in Direction.entries) {
            searchDirection(direction, animalIndex, animalRowIndex)
        }
        return maxSteps
    }

    private fun searchDirection(startingDirection: Direction, animalIndex: Int, animalRowIndex: Int) {
        var direction = startingDirection
        var currentIndex = updateIndex(animalIndex, direction)
        var currentRow = updateRow(animalRowIndex, direction)
        var currentPipe = input[currentRow][currentIndex]
        var steps = 0
        if (currentPipe.isValidPipe()) {
            while (currentPipe != 'S') {
                direction = updateDirection(currentPipe, direction)
                currentRow = updateRow(currentRow, direction)
                currentIndex = updateIndex(currentIndex, direction)
                currentPipe = input[currentRow][currentIndex]
                steps += 1
            }
        }
        if ((steps + 1) / 2 > maxSteps)  maxSteps = (steps + 1) / 2
    }

    private fun updateDirection(pipe: Char, direction: Direction): Direction {
        return when(pipe) {
            'L' -> if (direction == Direction.WEST) Direction.NORTH else Direction.EAST
            'J' -> if (direction == Direction.SOUTH) Direction.WEST else Direction.NORTH
            '7' -> if (direction == Direction.EAST) Direction.SOUTH else Direction.WEST
            'F' -> if (direction == Direction.WEST) Direction.SOUTH else Direction.EAST
            else -> direction
        }
    }

    private fun updateIndex(currentIndex: Int, direction: Direction): Int {
        return when(direction) {
            Direction.EAST -> if (currentIndex != input[0].lastIndex) currentIndex + 1 else currentIndex
            Direction.WEST -> if (currentIndex != 0) currentIndex - 1 else currentIndex
            else -> currentIndex
        }
    }
    private fun updateRow(currentRow: Int, direction: Direction): Int {
        return when(direction) {
            Direction.SOUTH -> if (currentRow != input.lastIndex) currentRow + 1 else currentRow
            Direction.NORTH -> if (currentRow != 0) currentRow - 1 else currentRow
            else -> currentRow
        }
    }
}



fun main() {
    fun part1(input: List<String>): Int {
        return Pipes(input).calculateSteps()
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day10")
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == -1)

    part1(input).println()
//    part2(input).println()
}