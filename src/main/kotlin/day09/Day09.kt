package day09

import println
import readPuzzleInput
import readTestInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

fun main() {

    fun calculateDifferences(numbers: List<Int>): List<Int> {
        return (1 until numbers.size).map { numbers[it] - numbers[it - 1] }
    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { number -> number.toInt() } }
            .sumOf {
                var total = it.last()
                var currentDifferences = it
                while (currentDifferences.any { diff -> diff != 0 }) {
                    currentDifferences = calculateDifferences(currentDifferences)
                    total += currentDifferences.last()
                }
                total
            }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ").map { number -> number.toInt() } }
            .sumOf {
                val firstNumbers = mutableListOf<Int>().apply { add(it.first()) }

                var currentDifferences = it
                while (currentDifferences.any { diff -> diff != 0 }) {
                    currentDifferences = calculateDifferences(currentDifferences)
                    firstNumbers += currentDifferences.first()
                }

                firstNumbers.reverse()
                firstNumbers.drop(1).fold(firstNumbers.first()) { acc, num -> num - acc }
            }
    }

    val testInput = readTestInput(PACKAGE_NAME)
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readPuzzleInput(PACKAGE_NAME)
    part1(input).println()
    part2(input).println()
}
