package day07

import println
import readPuzzleInput
import readTestInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

fun main() {

    data class CamelCard(val hand: String, val bid: Long, val power: Int)

    fun calculatePower(hand: String): Int {
        val charsByCount = hand.groupingBy { it }.eachCount()

        val countOneCard = charsByCount.count { it.value == 1 }
        val countTwoCard = charsByCount.count { it.value == 2 }
        val countThreeCard = charsByCount.count { it.value == 3 }
        val countFourCard = charsByCount.count { it.value == 4 }
        val countFiveCard = charsByCount.count { it.value == 5 }

        return when {
            countFiveCard == 1 -> 6//Five of a kind
            countFourCard == 1 && countOneCard == 1 -> 5//Four of a kind
            countThreeCard == 1 && countTwoCard == 1 -> 4//Full house
            countThreeCard == 1 && countOneCard == 2 -> 3//Three of a kind
            countTwoCard == 2 && countOneCard == 1 -> 2//Two pair
            countTwoCard == 1 && countOneCard == 3 -> 1//One pair
            else -> 0//High card
        }
    }

    fun calculatePowerForJoker(hand: String): Int {
        val jokerCount = hand.count { it == 'J' }
        if (jokerCount == 5 || jokerCount == 0) { //Five of a kind
            return calculatePower(hand)
        }

        val mostCommonChar = hand.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key

        val handWithoutJokers = hand.replace('J', mostCommonChar!!)
        return calculatePower(handWithoutJokers)
    }

    fun cardComparator(withJoker: Boolean): Comparator<CamelCard> {
        val charOrder = if (withJoker) "AKQJT98765432" else "AKQT98765432J"
        return Comparator { card1, card2 ->
            if (card1.power != card2.power) {
                card1.power - card2.power
            } else {
                card1.hand.zip(card2.hand)
                    .map { (char1, char2) -> charOrder.indexOf(char2) - charOrder.indexOf(char1) }
                    .find { it != 0 } ?: (card1.hand.length - card2.hand.length)
            }
        }
    }

    fun part1(input: List<String>): Long {
        return input.map {
            val (hand, bid) = it.split(" ")
            val power = calculatePower(hand)
            CamelCard(hand, bid.toLong(), power)
        }.sortedWith(cardComparator(false))
            .mapIndexed { index, card -> card.bid * (index + 1) }
            .sum()
    }

    fun part2(input: List<String>): Long {
        return input.map {
            val (hand, bid) = it.split(" ")
            val power = calculatePowerForJoker(hand)
            CamelCard(hand, bid.toLong(), power)
        }.sortedWith(cardComparator(true))
            .mapIndexed { index, card -> card.bid * (index + 1) }
            .sum()
    }

    val testInput = readTestInput(PACKAGE_NAME)
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)

    val input = readPuzzleInput(PACKAGE_NAME)
    part1(input).println()
    part2(input).println()
}
