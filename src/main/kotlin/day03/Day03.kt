package day03

import println
import readInput

fun main() {

    data class NumberPosition(val number: Int, val row: Int, val colIndexStart: Int, val colIndexEnd: Int)
    data class SymbolPosition(val row: Int, val col: Int)

    fun partCommon(input: List<String>): Pair<MutableList<SymbolPosition>, MutableList<NumberPosition>> {
        val symbolPositions = mutableListOf<SymbolPosition>()
        val numberPositions = mutableListOf<NumberPosition>()

        for (rowIndex in input.indices) {
            var colIndex = 0
            while (colIndex in input[rowIndex].indices) {
                val symbol = input[rowIndex][colIndex]
                if (symbol == '.') {
                    colIndex++
                    continue
                } else if (symbol.isDigit().not()) {
                    symbolPositions.add(SymbolPosition(rowIndex, colIndex++))
                    continue
                }
                var numberAsString: String = symbol.toString()
                val colIndexStart = colIndex++
                while (colIndex < input[rowIndex].length && input[rowIndex][colIndex].isDigit()) {
                    numberAsString += input[rowIndex][colIndex++]
                }
                numberPositions.add(NumberPosition(numberAsString.toInt(), rowIndex, colIndexStart, colIndex - 1))
            }
        }

        return Pair(symbolPositions, numberPositions)
    }

    fun part1(input: List<String>): Int {
        val (symbolPositions, numberPositions) = partCommon(input)
        return numberPositions.sumOf { np ->
            val (number, row, colIndexStart, colIndexEnd) = np
            val isFound = symbolPositions.any { sp ->
                sp.row in row - 1..row + 1 && sp.col in colIndexStart - 1..colIndexEnd + 1
            }
            if (isFound) number else 0
        }
    }

    fun part2(input: List<String>): Int {
        val (symbolPositions, numberPositions) = partCommon(input)
        return symbolPositions.sumOf { sp ->
            val numbers =
                numberPositions.filter { np -> sp.row in np.row - 1..np.row + 1 && sp.col in np.colIndexStart - 1..np.colIndexEnd + 1 }
                    .map { it.number }
                    .take(2)
            if (numbers.size == 2) numbers[0] * numbers[1] else 0
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")

    val part1 = part1(testInput)
    part1.println()
    check(part1 == 4361)

    val part2 = part2(testInput)
    part2.println()
    check(part2 == 467835)

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
