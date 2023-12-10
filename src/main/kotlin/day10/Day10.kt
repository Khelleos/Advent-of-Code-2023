package day10

import Position
import println
import readPuzzleInput
import kotlin.math.abs

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

private operator fun Position.plus(other: Position): Position =
    first + other.first to second + other.second

private enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

private fun Direction.toPos(): Position = when (this) {
    Direction.NORTH -> -1 to 0
    Direction.EAST -> 0 to 1
    Direction.SOUTH -> 1 to 0
    Direction.WEST -> 0 to -1
}

private fun Direction.opposite(): Direction = when (this) {
    Direction.NORTH -> Direction.SOUTH
    Direction.EAST -> Direction.WEST
    Direction.SOUTH -> Direction.NORTH
    Direction.WEST -> Direction.EAST
}

fun main() {

    fun getDirections(position: Position, input: List<String>): List<Direction> = when (input[position.first][position.second]) {
        '|' -> listOf(Direction.NORTH, Direction.SOUTH)
        '-' -> listOf(Direction.EAST, Direction.WEST)
        'L' -> listOf(Direction.NORTH, Direction.EAST)
        'J' -> listOf(Direction.NORTH, Direction.WEST)
        '7' -> listOf(Direction.SOUTH, Direction.WEST)
        'F' -> listOf(Direction.SOUTH, Direction.EAST)
        'S' -> listOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
        else -> emptyList() // '.'
    }

    fun partCommon(input: List<String>): List<Position> {
        val x = input.indexOfFirst { s -> 'S' in s }
        val startPosition = x to input[x].indexOf('S')

        fun getNext(position: Position, dir: Direction): Pair<Position, Direction>? {
            val nextPos = position + dir.toPos()
            val nextDirs = getDirections(nextPos, input)
            if (dir.opposite() !in nextDirs) return null
            return nextPos to nextDirs.minus(dir.opposite()).first()
        }

        val loop = mutableListOf<Position>()
        Direction.entries.forEach { startDir ->
            loop.clear()
            loop.add(startPosition)
            var (curPos, curDir) = getNext(startPosition, startDir) ?: return@forEach
            while (input[curPos.first][curPos.second] != 'S') {
                loop.add(curPos)
                val (nextPos, nextDir) = getNext(curPos, curDir) ?: break
                curPos = nextPos
                curDir = nextDir
            }
            if (input[curPos.first][curPos.second] == 'S') return@forEach
        }

        return loop
    }

    fun part1(input: List<String>): Int {
        return (partCommon(input).size / 2)
    }

    fun part2(input: List<String>): Int {
        val loop = partCommon(input)
        return (1 until input.size - 1).sumOf { x ->
            val idx = input[x].indices.filter { y ->
                val i1 = loop.indexOf(x to y)
                val i2 = loop.indexOf(x + 1 to y)
                i1 != -1 && i2 != -1 && (
                        abs(i1 - i2) == 1
                                || i1 in listOf(0, loop.lastIndex) && i2 in listOf(0, loop.lastIndex)
                        )
            }
            (idx.indices step 2).sumOf { i ->
                (idx[i]..idx[i + 1]).count { y -> x to y !in loop }
            }
        }
    }

    val input = readPuzzleInput(PACKAGE_NAME)
    part1(input).println()
    part2(input).println()
}
