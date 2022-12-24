import kotlin.math.abs
import kotlin.math.max

fun main() {
    fun move(position: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
        val (x, y) = position
        return when (direction) {
            Direction.UP -> Pair(x, y - 1)
            Direction.DOWN -> Pair(x, y + 1)
            Direction.LEFT -> Pair(x - 1, y)
            Direction.RIGHT -> Pair(x + 1, y)
        }
    }

    fun charToDirection(char: Char): Direction {
        return when (char) {
            'U' -> Direction.UP
            'D' -> Direction.DOWN
            'R' -> Direction.RIGHT
            'L' -> Direction.LEFT
            else -> throw IllegalArgumentException()
        }
    }

    fun distance(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Int {
        return max(abs(pos1.first - pos2.first), abs(pos1.second - pos2.second))
    }

    fun sign(x: Int): Int {
        return if (x > 0) 1
            else if (x < 0) -1
            else 0
    }

    fun updateTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        if (distance(head, tail) < 2) {
            return tail
        }

        return Pair(tail.first + 1 * sign(head.first - tail.first), tail.second + 1 * sign(head.second - tail.second))
    }

    fun part1(input: List<String>): Int {
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        var tailPositions = mutableSetOf(tail)

        for (row in input) {
            var (char, steps) = row.split(" ")

            for (i in 0 until steps.toInt()) {
                head = move(head, charToDirection(char[0]))
                tail = updateTail(head, tail)
                tailPositions.add(tail)
            }
        }

        return tailPositions.size
    }

    fun part2(input: List<String>): Int {
        var rope = Array(10) { Pair(0, 0) }
        var tailPositions = mutableSetOf(rope[9])

        for (row in input) {
            var (char, steps) = row.split(" ")

            for (i in 0 until steps.toInt()) {
                rope[0] = move(rope[0], charToDirection(char[0]))

                for (knot in 1 until 10) {
                    rope[knot] = updateTail(rope[knot - 1], rope[knot])
                }

                tailPositions.add(rope[9])
            }
        }

        return tailPositions.size
    }

    val testInput = readInput("Day09_test")
    println(part1(testInput))
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}