



fun getValidDigits(txt: List<String>): MutableList<Int> {
    val digits = mutableListOf<Int>()
    var currentDigit = ""
    var isValidDigit = false
    txt.forEachIndexed { index, row ->
        row.forEachIndexed { i, c ->
            if (c.isDigit()) {
                if (index != 0) {  // if  the element is not in the first row, check upper row
                    if(isSymbolOnTop(txt[index - 1], i, currentDigit.isEmpty())) isValidDigit = true
                }
                if (isSymbolAround(row, i)) isValidDigit = true    // check right and left

                if (index != txt.lastIndex) {
                    if (isSymbolOnBottom(txt[index + 1], i, currentDigit.isEmpty())) {
                        isValidDigit = true
                    }
                }
                currentDigit += c
                if (i == row.lastIndex || !row[i + 1].isDigit()) {
                    if (isValidDigit) digits.add(currentDigit.toInt())
                    isValidDigit = false
                    currentDigit = ""
                }
            }
        }

    }
    return digits
}

fun isSymbolOnTop(topRow: String, index: Int, includeUpperRight: Boolean = false, includeUpperLeft: Boolean = true): Boolean {
    if (includeUpperRight) {
        if (index != 0 && topRow[index - 1].isSymbol()) {  // check upper right it's not at index 0
            return true
        }
    }
    if (includeUpperLeft) {  // check upper left if it's not the last item
        if (index != topRow.lastIndex && topRow[index + 1].isSymbol()) {
            return true
        }
    }
    return topRow[index].isSymbol()   // check the upper element
}

fun isSymbolAround(row: String, index: Int): Boolean {
    if (index != 0 && row[index - 1].isSymbol()) {
        return true
    }
    return index != row.lastIndex && row[index + 1].isSymbol()
}

fun isSymbolOnBottom(bottomRow: String, index: Int, includeLowerRight: Boolean = false, includeLowerLeft: Boolean = true): Boolean {
    if (includeLowerRight) {
        if (index != 0 && bottomRow[index - 1].isSymbol()) {  // check lower right if it's not at index 0
            return true
        }
    }
    if (includeLowerLeft) {  // check lower left if it's not the last item
        if (index != bottomRow.lastIndex && bottomRow[index + 1].isSymbol()) {
            return true
        }
    }
    return bottomRow[index].isSymbol()   // check the lower element
}

fun Char.isSymbol(): Boolean {
    return !this.isDigit() && this != '.'
}


fun main() {
    fun part1(input: List<String>): Int {
        return getValidDigits(input).sum()
    }

    fun part2(input: List<String>): Any {
        return 0

    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")
    check(part1(testInput) == 4361)

    part1(input).println()
//    part2(input).println()

}