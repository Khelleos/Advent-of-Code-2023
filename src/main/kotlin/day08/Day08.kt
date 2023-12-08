package day08

import println
import readPuzzleInput
import readTestInput

val PACKAGE_NAME = ::main.javaClass.name.substringBefore(".")
val DIRECTION_REGEXP = Regex("""^(\w+) = \((\w+), (\w+)\)$""")

fun main() {

    data class Node(val current: String, val left: String, val right: String)

    fun findLCM(n1: Long, n2: Long): Long {
        var gcd = 1
        var i = 1
        while (i <= n1 && i <= n2) {
            if (n1 % i == 0L && n2 % i == 0L)
                gcd = i
            ++i
        }

        return n1 * n2 / gcd
    }

    fun parse(input: List<String>): Pair<String, List<Node>> {
        val direction = input[0]
        val nodes = input.filter { it.contains("=") && it.isNotEmpty() }
            .map {
                val (currentNode, leftNode, rightNode) = DIRECTION_REGEXP.find(it)!!.destructured
                Node(currentNode, leftNode, rightNode)
            }
        return Pair(direction, nodes)
    }

    fun solve(node: Node, direction: String, nodes: List<Node>, condition: (Node) -> Boolean): Long {
        var currentNode = node
        var steps = 0L
        while (condition(currentNode)) {
            val index = steps % direction.length
            currentNode = if (direction[index.toInt()] == 'L') {
                nodes.find { it.current == currentNode.left }!!
            } else {
                nodes.find { it.current == currentNode.right }!!
            }

            steps++
        }
        return steps
    }

    fun part1(input: List<String>): Long {
        val (direction, nodes) = parse(input)

        val currentNode = nodes.first { it.current == "AAA" }
        val condition: (Node) -> Boolean = { it.current != "ZZZ" }
        return solve(currentNode, direction, nodes, condition)
    }

    fun part2(input: List<String>): Long {
        val (direction, nodes) = parse(input)

        val currentNodes = nodes.filter { it.current.endsWith("A") }.toMutableList()
        val condition: (Node) -> Boolean = { it.current.endsWith("Z").not() }
        return currentNodes.map { solve(it, direction, nodes, condition) }
            .fold(1L) { acc, op -> findLCM(acc, op) }
    }

//    val testInput = readTestInput(PACKAGE_NAME)
//    check(part1(testInput) == 2L)
//    check(part2(testInput) == 6L)

    val input = readPuzzleInput(PACKAGE_NAME)
    part1(input).println()
    part2(input).println()
}
