package day15

import println
import readPuzzleInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

fun main() {

    val input = readPuzzleInput(PACKAGE_NAME).first().split(",")

    fun hash(step: String): Int =
        step.fold(0) { acc, value -> acc.plus(value.code).times(17).mod(256) }

    fun part1() = input.sumOf { step -> hash(step) }

    fun part2(): Int {
        val boxes = Array(256) { mutableMapOf<String, Pair<Int, Int>>() }
        val pos = IntArray(256)

        input.forEach { step ->
            if (step.last() == '-') step.dropLast(1).let { label -> boxes[hash(label)].remove(label) }
            else {
                val focalLength = step.last().digitToInt()
                val label = step.dropLast(2)
                val box = hash(label)
                boxes[box][label] = focalLength to (boxes[box][label]?.second ?: pos[box]++)
            }
        }

        return boxes.mapIndexed { boxIndex, box ->
            box.values.sortedBy(Pair<Int, Int>::second).mapIndexed { lensIndex, (focalLength, _) ->
                boxIndex.inc() * lensIndex.inc() * focalLength
            }.sum()
        }.sum()
    }

    part1().println()
    part2().println()
}
