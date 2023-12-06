package day01

import day07.main
import println
import readPuzzleInput
import readTestInput

fun main() {

    val packageName = ::main.javaClass.name.substringBefore(".")

    fun part1(input: List<String>): Int {
        return input.map {
            it.filter { c -> c.isDigit() }.toList()
        }.sumOf { "${it.first()}${it.last()}".toInt() }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            it
                .replace("one", "o1e")
                .replace("two", "t2o")
                .replace("three", "t3e")
                .replace("four", "f4r")
                .replace("five", "f5e")
                .replace("six", "s6x")
                .replace("seven", "s7n")
                .replace("eight", "e8t")
                .replace("nine", "n9e")
                .toCharArray()
                .filter { c -> c.isDigit() }
        }.sumOf { "${it.first()}${it.last()}".toInt() }
    }

    val testInput = readTestInput(packageName)
    check(part1(testInput) == 142)
    check(part2(testInput) == 281)

    val input = readPuzzleInput(packageName)
    part1(input).println()
    part2(input).println()
}
