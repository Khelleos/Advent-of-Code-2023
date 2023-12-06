package day06

import println
import readInput

const val SPEED_UP = 1

fun main() {

    fun part1(input: List<String>): Int {
        val time = input[0].substringAfter(":").trim().split(" ").filter { it.isNotEmpty() }.map(String::toLong)
        val distance = input[1].substringAfter(":").trim().split(" ").filter { it.isNotEmpty() }.map(String::toLong)

        return time.mapIndexed { index, t ->
            var count = 0
            var chargeTime = 0
            while (chargeTime <= t) {
                val speed = chargeTime * SPEED_UP
                val leftTime = t - chargeTime

                if (speed * leftTime > distance[index]) {
                    count++
                }

                chargeTime++
            }

            count
        }.reduce { acc, count -> acc * count }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].substringAfter(":").filter { it.isDigit() }.toLong()
        val distance = input[1].substringAfter(":").filter { it.isDigit() }.toLong()

        var count = 0
        var chargeTime = 0
        while (chargeTime <= time) {
            val speed = chargeTime * SPEED_UP
            val leftTime = time - chargeTime

            if (speed * leftTime > distance) {
                count++
            }

            chargeTime++
        }

        return count
    }

    val testInput = readInput("day06/Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("day06/Day06")
    part1(input).println()
    part2(input).println()
}
