
enum class Direction {
    NORTH, SOUTH, WEST, EAST

}

class Pipes(val input: List<String>) {
    private var animalRowIndex: Int = 0
    var maxSteps = 0
    var nextIndex = 0
    init {
        input.forEachIndexed { index, s -> if (s.contains('S')) animalRowIndex = index  }
    }

    fun calculateSteps(): Int {
        searchEast()
        searchWest()
        searchNorth()
        searchSouth()
        return maxSteps
    }

    fun searchEast() {
        var direction = Direction.EAST
        var currentIndex = input[animalRowIndex].indexOf('S') + 1
        var currentRow = animalRowIndex
        var currentPipe = input[currentRow][currentIndex]
        var steps = 0
        while (currentPipe != 'S') {
            direction = updateDirection(currentPipe, direction)
            currentRow = updateRow(currentRow, direction)
            currentIndex = updateIndex(currentIndex, direction)
            currentPipe = input[currentRow][currentIndex]
            steps += 1
        }
        if ((steps + 1) / 2 > maxSteps)  maxSteps = (steps + 1) / 2
    }

    fun searchWest() {
        var direction = Direction.WEST
        var currentIndex = input[animalRowIndex].indexOf('S') - 1
        var currentRow = animalRowIndex
        var currentPipe = input[currentRow][currentIndex]
        var steps = 0
        while (currentPipe != 'S') {
            direction = updateDirection(currentPipe, direction)
            currentRow = updateRow(currentRow, direction)
            currentIndex = updateIndex(currentIndex, direction)
            currentPipe = input[currentRow][currentIndex]
            steps += 1
        }
        if ((steps + 1) / 2 > maxSteps)  maxSteps = (steps + 1) / 2
    }

    fun searchNorth() {
        var direction = Direction.NORTH
        var currentIndex = input[animalRowIndex].indexOf('S')
        var currentRow = animalRowIndex - 1
        var currentPipe = input[currentRow][currentIndex]
        var steps = 0
        while (currentPipe != 'S') {
            direction = updateDirection(currentPipe, direction)
            currentRow = updateRow(currentRow, direction)
            currentIndex = updateIndex(currentIndex, direction)
            currentPipe = input[currentRow][currentIndex]
            steps += 1
        }
        if ((steps + 1) / 2 > maxSteps)  maxSteps = (steps + 1) / 2
    }

    fun searchSouth() {
        var direction = Direction.SOUTH
        var currentIndex = input[animalRowIndex].indexOf('S')
        var currentRow = animalRowIndex + 1
        var currentPipe = input[currentRow][currentIndex]
        var steps = 0
        while (currentPipe != 'S') {
            direction = updateDirection(currentPipe, direction)
            currentRow = updateRow(currentRow, direction)
            currentIndex = updateIndex(currentIndex, direction)
            currentPipe = input[currentRow][currentIndex]
            steps += 1
        }
        if ((steps + 1) / 2 > maxSteps)  maxSteps = (steps + 1) / 2
    }

    fun updateIndex(currentIndex: Int, direction: Direction): Int {
        return when(direction) {
            Direction.EAST -> currentIndex + 1
            Direction.WEST -> currentIndex - 1
            else -> currentIndex
        }
    }


    fun updateRow(currentRow: Int, direction: Direction): Int {
        return when(direction) {
            Direction.SOUTH -> currentRow + 1
            Direction.NORTH -> currentRow - 1
            else -> currentRow
        }
    }

    fun updateDirection(pipe: Char, direction: Direction): Direction {
        return when(pipe) {
            'L' -> {
                if (direction == Direction.WEST) Direction.NORTH else Direction.EAST
            }
            'J' ->  if (direction == Direction.SOUTH) Direction.WEST else Direction.NORTH
            '7' -> if (direction == Direction.EAST) Direction.SOUTH else Direction.WEST
            'F' -> if (direction == Direction.WEST) Direction.SOUTH else Direction.EAST
            else -> direction
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
//    check(part1(testInput) == 4)
    check(part2(testInput) == -1)

    part1(input).println()
//    part2(input).println()
}