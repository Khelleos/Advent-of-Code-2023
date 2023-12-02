package day02

import println
import readInput

const val MAX_REDS = 12
const val MAX_GREENS = 13
const val MAX_BLUES = 14
val COLOR_CUBE_REGEX = """(\d+) (\w+)[,;]?""".toRegex()
val GAME_ID_REGEX = """^Game (\d+):""".toRegex()
fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val gameId = GAME_ID_REGEX.find(game)!!.groups[1]!!.value.toInt()

            val cubes = game.substringAfter(":")
            val isPassed = COLOR_CUBE_REGEX.findAll(cubes).all { matchResult ->
                val (count, color) = matchResult.destructured
                when (color) {
                    "red" -> count.toInt() <= MAX_REDS
                    "green" -> count.toInt() <= MAX_GREENS
                    "blue" -> count.toInt() <= MAX_BLUES
                    else -> throw IllegalArgumentException("Unknown color $color")
                }
            }

            if (isPassed) gameId else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            var reds = 0
            var greens = 0
            var blues = 0

            val cubes = game.substringAfter(":")
            COLOR_CUBE_REGEX.findAll(cubes).forEach { matchResult ->
                val (count, color) = matchResult.destructured
                when (color) {
                    "red" -> reds = maxOf(count.toInt(), reds)
                    "green" -> greens = maxOf(count.toInt(), greens)
                    "blue" -> blues = maxOf(count.toInt(), blues)
                    else -> throw IllegalArgumentException("Unknown color $color")
                }
            }

            reds * greens * blues
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")

    val part1 = part1(testInput)
    part1.println()
    check(part1 == 8)

    val part2 = part2(testInput)
    part2.println()
    check(part2 == 2286)

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
