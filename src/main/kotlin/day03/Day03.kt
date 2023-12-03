package day03

import println
import readInput

fun main() {

    data class EnginePart(val amount: Int, val rowIndex: Int, val positionIndexStart: Int, val positionIndexEnd: Int)
    data class SymbolPosition(val rowIndex: Int, val positionIndex: Int)

    fun partCommon(input: List<String>): Pair<MutableList<SymbolPosition>, MutableList<EnginePart>> {
        val symbolPositions = mutableListOf<SymbolPosition>()
        val engineParts = mutableListOf<EnginePart>()

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
                engineParts.add(EnginePart(numberAsString.toInt(), rowIndex, colIndexStart, colIndex - 1))
            }
        }

        return Pair(symbolPositions, engineParts)
    }

    fun part1(input: List<String>): Int {
        val (symbols, engineParts) = partCommon(input)
        return engineParts.sumOf { np ->
            val (amount, rowIndex, positionIndexStart, positionIndexEnd) = np
            val isAdjacent = symbols.any { sp ->
                sp.rowIndex in rowIndex - 1..rowIndex + 1 && sp.positionIndex in positionIndexStart - 1..positionIndexEnd + 1
            }
            if (isAdjacent) amount else 0
        }
    }

    fun part2(input: List<String>): Int {
        val (symbolPositions, engineParts) = partCommon(input)
        return symbolPositions.sumOf { sp ->
            val (rowIndex, positionIndex) = sp
            val adjacentEngineParts =
                engineParts.filter { np -> rowIndex in np.rowIndex - 1..np.rowIndex + 1 && positionIndex in np.positionIndexStart - 1..np.positionIndexEnd + 1 }
                    .map { it.amount }
                    .take(2)
            if (adjacentEngineParts.size == 2) adjacentEngineParts[0] * adjacentEngineParts[1] else 0
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
