package day14

import println
import readPuzzleInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")


enum class Direction {
    NORTH, EAST, SOUTH, WEST;
}

fun main() {
    val input = readPuzzleInput(PACKAGE_NAME)

    fun moveRocks(grid: List<CharArray>, direction: Direction): List<CharArray> {
        val n = grid.size
        val m = grid[0].size
        val adjustedGrid = Array(n) { CharArray(m) { '.' } }

        when (direction) {
            Direction.WEST -> {
                for (i in 0 until n) {
                    var index = 0
                    for (j in 0 until m) {
                        if (grid[i][j] == 'O') {
                            adjustedGrid[i][index++] = 'O'
                        } else if (grid[i][j] == '#') {
                            adjustedGrid[i][j] = '#'
                            index = j + 1
                        }
                    }
                }
            }

            Direction.EAST -> {
                for (i in 0 until n) {
                    var index = m - 1
                    for (j in m - 1 downTo 0) {
                        if (grid[i][j] == 'O') {
                            adjustedGrid[i][index--] = 'O'
                        } else if (grid[i][j] == '#') {
                            adjustedGrid[i][j] = '#'
                            index = j - 1
                        }
                    }
                }
            }

            Direction.NORTH -> {
                for (j in 0 until m) {
                    var index = 0
                    for (i in 0 until n) {
                        if (grid[i][j] == 'O') {
                            adjustedGrid[index++][j] = 'O'
                        } else if (grid[i][j] == '#') {
                            adjustedGrid[i][j] = '#'
                            index = i + 1
                        }
                    }
                }
            }

            Direction.SOUTH -> {
                for (j in 0 until m) {
                    var index = n - 1
                    for (i in n - 1 downTo 0) {
                        if (grid[i][j] == 'O') {
                            adjustedGrid[index--][j] = 'O'
                        } else if (grid[i][j] == '#') {
                            adjustedGrid[i][j] = '#'
                            index = i - 1
                        }
                    }
                }
            }

        }
        return adjustedGrid.toList()
    }

    fun calculateResult(grid: List<CharArray>): Any {
        return grid.withIndex().sumOf { (i, row) ->
            row.count { it == 'O' } * (grid.size - i)
        }
    }

    fun part1(): Any {
        var grid = input.map { it.toCharArray() }
        grid = moveRocks(grid, Direction.NORTH)
        return calculateResult(grid)
    }

    fun part2(): Any {
        var grid = input.map { it.toCharArray() }
        val seen = mutableMapOf<List<String>, Int>()
        var cycle = 0
        val total = 1_000_000_000
        var length = 0

        while (cycle < total) {
            val cur = grid.map { it.concatToString() }
            if (cur in seen) {
                length = cycle - seen[cur]!!
                break
            }
            seen[cur] = cycle

            grid = moveRocks(grid, Direction.NORTH)
            grid = moveRocks(grid, Direction.WEST)
            grid = moveRocks(grid, Direction.SOUTH)
            grid = moveRocks(grid, Direction.EAST)
            cycle++
        }

        if (length > 0) {
            val remainingCycles = (total - cycle) % length
            for (i in 0 until remainingCycles) {
                grid = moveRocks(grid, Direction.NORTH)
                grid = moveRocks(grid, Direction.WEST)
                grid = moveRocks(grid, Direction.SOUTH)
                grid = moveRocks(grid, Direction.EAST)
            }
        }

        return calculateResult(grid)
    }

    part1().println() //107430
    part2().println() //96317
}
