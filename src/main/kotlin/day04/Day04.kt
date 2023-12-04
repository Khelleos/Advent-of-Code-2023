package day04

import println
import readInput
import kotlin.math.pow

fun main() {

    data class Card(val winningNumbers: List<Int>, val availableNumbers: List<Int>)

    fun parseNumbers(numbers: String): List<Int> {
        return numbers.split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
    }

    fun parseCard(card: String): Card {
        val parts = card.split("|")
        val winningNumbers = parseNumbers(parts[0])
        val availableNumbers = parseNumbers(parts[1])
        return Card(winningNumbers, availableNumbers)
    }

    fun part1(input: List<String>): Int {
        return input
            .map { card -> card.replace(Regex("Card\\s*\\d+:\\s*"), "") }
            .map { card -> parseCard(card) }
            .sumOf { card ->
                return@sumOf when (val countWinningNumbers =
                    card.winningNumbers.intersect(card.availableNumbers.toSet()).size) {
                    0 -> 0
                    1 -> 1
                    else -> 2.0.pow(countWinningNumbers.toDouble() - 1).toInt()
                }
            }
    }

    fun part2(input: List<String>): Int {
        val numberOfScratchCards = Array(input.size) { 1 }
        input
            .map { card -> card.replace(Regex("Card\\s*\\d+:\\s*"), "") }
            .map { card -> parseCard(card) }
            .forEachIndexed { index, card ->
                val matchingNumbers = card.availableNumbers.intersect(card.winningNumbers.toSet())
                for (i in index + 1..index + matchingNumbers.size) numberOfScratchCards[i] += numberOfScratchCards[index]
            }
        return numberOfScratchCards.sum()
    }

    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}
