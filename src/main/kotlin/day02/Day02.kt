package day02

import println
import readInput
import kotlin.math.max

const val MAX_REDS = 12
const val MAX_GREENS = 13
const val MAX_BLUES = 14
val MAX_COUNTS_PER_COLOR = mapOf("red" to MAX_REDS, "green" to MAX_GREENS, "blue" to MAX_BLUES)
val COLOR_CUBE_REGEX = """(\d+) (\w+)[,;]?""".toRegex()
val GAME_ID_REGEX = """^Game (\d+):""".toRegex()
fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val gameId = GAME_ID_REGEX.find(game)!!.groups[1]!!.value.toInt()

            val cubes = game.substringAfter(":")
            val isPassed = COLOR_CUBE_REGEX.findAll(cubes)
                .all { matchResult ->
                    val (count, color) = matchResult.destructured
                    count.toInt() <= MAX_COUNTS_PER_COLOR[color]!!
                }

            if (isPassed) gameId else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            val maxCubesPerColor = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            val cubes = game.substringAfter(":")
            COLOR_CUBE_REGEX.findAll(cubes).forEach { matchResult ->
                val (count, color) = matchResult.destructured
                maxCubesPerColor.merge(color, count.toInt()) { oldValue, newValue -> max(oldValue, newValue) }
            }

            maxCubesPerColor["red"]!! * maxCubesPerColor["green"]!! * maxCubesPerColor["blue"]!!
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
