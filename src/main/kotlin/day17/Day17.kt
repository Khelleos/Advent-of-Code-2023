package day17

import println
import readPuzzleInput
import java.util.*

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

fun main() {

    val input = readPuzzleInput(PACKAGE_NAME).map { line -> line.map(Char::digitToInt) }

    data class State(
        val heatLoss: Int,
        val x: Int,
        val y: Int,
        val isHorizontal: Boolean
    )

    fun State.next(min: Int, max: Int): List<State> =
        buildList {
            val (xo, yo) = if (isHorizontal) 0 to 1 else 1 to 0
            var state = this@next.copy(isHorizontal = !isHorizontal)
            fun process(x: Int, y: Int): Boolean {
                if (x !in input.indices || y !in input[0].indices) return false
                state = state.copy(x = x, y = y, heatLoss = state.heatLoss + input[x][y])
                return true
            }
            for (i in 1..max) {
                if (!process(state.x + xo, state.y + yo)) break
                if (i >= min) add(state)
            }
            state = this@next.copy(isHorizontal = !isHorizontal)
            for (i in 1..max) {
                if (!process(state.x - xo, state.y - yo)) break
                if (i >= min) add(state)
            }
        }

    fun findMinimumHeatLoss(min: Int, max: Int): Int {
        val treeSet = TreeSet(compareBy(State::heatLoss).thenBy(State::x).thenBy(State::y).thenBy(State::isHorizontal))
        val seen = mutableSetOf<Triple<Int, Int, Boolean>>()
        treeSet.add(State(0, 0, 0, true))
        treeSet.add(State(0, 0, 0, false))
        while (treeSet.isNotEmpty()) {
            val state = treeSet.pollFirst()!!
            if (state.x == input.lastIndex && state.y == input[0].lastIndex) return state.heatLoss
            val triple = Triple(state.x, state.y, state.isHorizontal)
            if (triple in seen) continue else seen.add(triple)
            treeSet.addAll(state.next(min, max))
        }
        return 0
    }

    fun part1() = findMinimumHeatLoss(1, 3)

    fun part2() = findMinimumHeatLoss(4, 10)

    part1().println()
    part2().println()
}
