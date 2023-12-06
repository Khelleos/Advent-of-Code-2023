package day02

import day07.main
import println
import readPuzzleInput
import readTestInput

import kotlin.math.max


const val MAX_REDS = 12
const val MAX_GREENS = 13
const val MAX_BLUES = 14
val MAX_COUNTS_PER_COLOR = mapOf("red" to MAX_REDS, "green" to MAX_GREENS, "blue" to MAX_BLUES)
val COLOR_CUBE_REGEX = """(\d+) (\w+)[,;]?""".toRegex()
val GAME_ID_REGEX = """^Game (\d+):""".toRegex()
fun main() {

    val packageName = ::main.javaClass.name.substringBefore(".")

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

    val testInput = readTestInput(packageName)
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readPuzzleInput(packageName)
    part1(input).println()
    part2(input).println()
}
