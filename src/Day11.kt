import kotlin.math.abs


class Image(private val data: List<String>) {
    private val expandedRows = mutableListOf<Int>()
    private val expandedColumns = mutableListOf<Int>()
    private val galaxies = mutableListOf<Pair<Int, Int>>()
    init {
        updateExpandedColumns()
        updateGalaxiesAndExpandedColumns()
    }

    private fun updateExpandedColumns() {
        for (index in data.indices) {
            var isExpanded = true
            for (row in 0..data.lastIndex) {
                if (data[row][index] == '#') {
                    isExpanded = false
                    break
                }
            }
            if (isExpanded) expandedColumns.add(index)
        }
    }

    private fun updateGalaxiesAndExpandedColumns() {
        data.forEachIndexed{ rowIndex: Int, row: String ->
            if (!row.contains('#')) expandedRows.add(rowIndex)
            else {
                row.forEachIndexed { index, c -> if (c == '#') galaxies.add(rowIndex to index) }
            }
        }
    }

    fun getSumOfShortestPaths(expansionAmount: Long = 1): Number {
        val minDistances = mutableListOf<Long>()
        for (i in galaxies.indices) {
            val firstGalaxy = galaxies[i]
            for (secondGalaxy in galaxies.subList(i + 1, galaxies.size)) {
                var currentShortestPath = 0L
                val rows = listOf(firstGalaxy.first, secondGalaxy.first).sorted()
                val columns = listOf(firstGalaxy.second, secondGalaxy.second).sorted()
                currentShortestPath += expandedRows.count { it in rows.first()..rows.last() } * expansionAmount
                currentShortestPath += expandedColumns.count { it in columns.first()..columns.last() } * expansionAmount
                currentShortestPath += abs(secondGalaxy.first - firstGalaxy.first)
                currentShortestPath += abs(secondGalaxy.second - firstGalaxy.second)
                minDistances.add(currentShortestPath)
            }
        }
        return minDistances.sum()
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        return Image(input).getSumOfShortestPaths().toInt()
    }

    fun part2(input: List<String>, expansionAmount: Long): Long {
        return Image(input).getSumOfShortestPaths(expansionAmount - 1).toLong()
    }

    val input = readInput("Day11")
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374)
    check(part2(testInput, 10) == 1030L)
    check(part2(testInput, 100) == 8410L)

    part1(input).println()
    part2(input, 1000000).println()
}