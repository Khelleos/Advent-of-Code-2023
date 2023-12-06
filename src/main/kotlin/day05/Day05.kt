package day05

import day07.main
import println
import readPuzzleInput
import readTestInput
import kotlin.math.min


fun main() {

    val packageName = ::main.javaClass.name.substringBefore(".")

    class Range(val destination: Long, val source: Long, val range: Long)
    class RangeMap(ranges: List<Range>) {
        private val starts: List<Long>
        private val ends: List<Long>
        private val betweens: List<Long>

        init {
            starts = ranges.map { it.source }
            ends = ranges.map { it.destination }
            betweens = ranges.map { it.range }
        }

        fun convert(value: Long): Long {
            for (i in starts.indices) {
                if (starts[i] <= value && starts[i] + betweens[i] > value) {
                    return ends[i] + (value - starts[i])
                }
            }
            return value
        }

        fun convertPart2(value: Long): LongArray {
            var nextStart = 10000000000L
            for (i in starts.indices) {
                if (starts[i] > value) {
                    nextStart = nextStart.coerceAtMost(starts[i] - value - 1)
                }
                if (starts[i] <= value && starts[i] + betweens[i] > value) {
                    return longArrayOf(
                        ends[i] + (value - starts[i]),
                        betweens[i] - (value - starts[i]) - 1
                    )
                }
            }
            return longArrayOf(value, if (nextStart == 10000000000L) 0 else nextStart)
        }
    }

    fun returnValAndBound(value: Long, rangeMaps: List<RangeMap>): LongArray {
        var bound = 10000000000L
        var answer = value
        for (rangeMap in rangeMaps) {
            bound = min(bound.toDouble(), rangeMap.convertPart2(answer)[1].toDouble()).toLong()
            answer = rangeMap.convertPart2(answer)[0]
        }
        return longArrayOf(answer, bound)
    }

    fun part1(input: List<String>): Long {
        var answer = 10000000000L
        val seeds = input.first().substringAfter(":").trim().split(" ").map(String::toLong)

        val rangeMaps = mutableListOf<RangeMap>()
        var tmp = mutableListOf<Range>()

        input.drop(2)
            .filter { line -> line.isNotEmpty() }
            .forEach { line ->
                if (line.contains("map")) {
                    if (tmp.isNotEmpty()) {
                        rangeMaps.add(RangeMap(tmp))
                    }
                    tmp = mutableListOf()
                } else {
                    val (dest, source, length) = line.split(" ")
                    tmp.add(Range(dest.toLong(), source.toLong(), length.toLong()))
                }
            }
        rangeMaps.add(RangeMap(tmp))

        for (seed in seeds) {
            var value = seed
            for (rangeMap in rangeMaps) {
                value = rangeMap.convert(value)
            }
            if (value < answer) {
                answer = value
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        var answer = 10000000000L
        val seeds = input.first().substringAfter(":").trim().split(" ").map(String::toLong)

        val rangeMaps = mutableListOf<RangeMap>()
        var tmp = mutableListOf<Range>()

        input.drop(2)
            .filter { line -> line.isNotEmpty() }
            .forEach { line ->
                if (line.contains("map")) {
                    if (tmp.isNotEmpty()) {
                        rangeMaps.add(RangeMap(tmp))
                    }
                    tmp = mutableListOf()
                } else {
                    val (dest, source, length) = line.split(" ")
                    tmp.add(Range(dest.toLong(), source.toLong(), length.toLong()))
                }
            }
        rangeMaps.add(RangeMap(tmp))
        for (i in seeds.indices step 2) {
            var j = seeds[i]
            while (j < seeds[i] + seeds[i + 1]) {
                val ret = returnValAndBound(j, rangeMaps)
                if (ret[0] < answer) {
                    answer = ret[0]
                }
                j += ret[1]
                j++
            }
        }

        return answer
    }

    val testInput = readTestInput(packageName)
    val input = readPuzzleInput(packageName)

    check(part1(testInput) == 35L)
    part1(input).println()

    check(part2(testInput) == 46L)
    part2(input).println()
}
