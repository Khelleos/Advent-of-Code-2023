package day13

import println
import readPuzzleInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

fun main() {

    val patterns: List<List<String>> = buildList {
        val input = readPuzzleInput(PACKAGE_NAME)
        var offset = 0
        while (offset < input.size) {
            add(input.drop(offset).takeWhile(String::isNotBlank))
            offset += last().size.inc()
        }
    }

    fun findReflection(max: Int, count: Int, fn: (Int, Int) -> (Int)): Int? =
        (0..<max).find { i ->
            (0..i.coerceAtMost(max - i - 1))
                .sumOf { offset -> fn(i - offset, i + 1 + offset) } == count
        }?.inc()

    fun findHorizontalReflection(pattern: List<String>, count: Int): Int? =
        findReflection(
            pattern.lastIndex,
            count
        ) { i, j -> pattern[i].indices.count { y -> pattern[i][y] != pattern[j][y] } }

    fun findVerticalReflection(pattern: List<String>, count: Int): Int? =
        findReflection(
            pattern[0].lastIndex,
            count
        ) { i, j -> pattern.indices.count { x -> pattern[x][i] != pattern[x][j] } }

    fun scoreReflectionLines(count: Int): Int =
        patterns.sumOf { pattern ->
            findHorizontalReflection(pattern, count)?.let { n -> 100 * n }
                ?: findVerticalReflection(pattern, count)
                ?: error("No reflection found")
        }

    fun part1(): String =
        scoreReflectionLines(0).toString()

    fun part2(): String =
        scoreReflectionLines(1).toString()

    part1().println()
    part2().println()
}
