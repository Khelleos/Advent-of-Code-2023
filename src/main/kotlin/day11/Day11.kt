package day11

import Position
import println
import readPuzzleInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")

fun main() {

    fun getDistance(
        galaxy1: Pair<Int, Int>,
        galaxy2: Pair<Int, Int>,
        emptySize: Long,
        emptyRows: List<Int>,
        emptyColumns: List<Int>
    ): Long {
        val (x1, y1) = galaxy1
        val (x2, y2) = galaxy2
        val x = (x1.coerceAtMost(x2).inc()..x1.coerceAtLeast(x2)).sumOf { i ->
            if (i in emptyRows) emptySize else 1
        }
        val y = (y1.coerceAtMost(y2).inc()..y1.coerceAtLeast(y2)).sumOf { i ->
            if (i in emptyColumns) emptySize else 1
        }
        return x + y
    }

    fun getTotalDistance(emptySize: Long, galaxies: List<Position>, emptyRows: List<Int>, emptyColumns: List<Int>): Long =
        (0..<galaxies.size - 1).sumOf { i ->
            (i.inc()..<galaxies.size).sumOf { j ->
                getDistance(galaxies[i], galaxies[j], emptySize, emptyRows, emptyColumns)
            }
        }

    fun part1(input: List<String>): Long {
        val galaxies: List<Position> = input.flatMapIndexed { x, line ->
            line.indices.filter { y -> line[y] == '#' }.map { y -> x to y }
        }

        val emptyRows: List<Int> = galaxies.map(Position::first).toSet().let { set ->
            (set.min()..set.max()).filter { i -> i !in set }
        }
        val emptyColumns: List<Int> = galaxies.map(Position::second).toSet().let { set ->
            (set.min()..set.max()).filter { i -> i !in set }
        }

        return getTotalDistance(2L, galaxies, emptyRows, emptyColumns)
    }

    fun part2(input: List<String>): Long {
        val galaxies: List<Position> = input.flatMapIndexed { x, line ->
            line.indices.filter { y -> line[y] == '#' }.map { y -> x to y }
        }

        val emptyRows: List<Int> = galaxies.map(Position::first).toSet().let { set ->
            (set.min()..set.max()).filter { i -> i !in set }
        }
        val emptyColumns: List<Int> = galaxies.map(Position::second).toSet().let { set ->
            (set.min()..set.max()).filter { i -> i !in set }
        }

        return getTotalDistance(1000000L, galaxies, emptyRows, emptyColumns)
    }

    val input = readPuzzleInput(PACKAGE_NAME)

//    part1(input).println()
    part2(input).println()
}
