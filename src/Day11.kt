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

    fun getSumOfShortestPaths(): Int {
        val minDistances = mutableListOf<Int>()
        for (i in galaxies.indices) {
            val firstGalaxy = galaxies[i]
            for (secondGalaxy in galaxies.subList(i + 1, galaxies.size)) {
                var minDistance = 0
                val rows = listOf(firstGalaxy.first, secondGalaxy.first).sorted()
                val columns = listOf(firstGalaxy.second, secondGalaxy.second).sorted()
                minDistance += expandedRows.count { it in rows.first()..rows.last() }
                minDistance += expandedColumns.count { it in columns.first()..columns.last() }
                minDistance += abs(secondGalaxy.first - firstGalaxy.first)
                minDistance += abs(secondGalaxy.second - firstGalaxy.second)
                minDistances.add(minDistance)
            }
        }
        return minDistances.sum()
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        return Image(input).getSumOfShortestPaths()
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day11")
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374)
    check(part2(testInput) == -1)

    part1(input).println()
    part2(input).println()
}