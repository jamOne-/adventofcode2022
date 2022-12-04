enum class Shape(val value: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        fun fromChar(inputChar: Char): Shape {
            return when(inputChar) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
        }
    }
}

fun weakerShape(shape: Shape): Shape {
    return when(shape) {
        Shape.ROCK -> Shape.SCISSORS
        Shape.PAPER -> Shape.ROCK
        Shape.SCISSORS -> Shape.PAPER
    }
}

fun strongerShape(shape: Shape): Shape {
    return when(shape) {
        Shape.ROCK -> Shape.PAPER
        Shape.PAPER -> Shape.SCISSORS
        Shape.SCISSORS -> Shape.ROCK
    }
}

const val LOSE_POINTS = 0
const val DRAW_POINTS = 3
const val WIN_POINTS = 6

fun gameResult(me: Shape, opponent: Shape): Int {
    return if (me == opponent) DRAW_POINTS else if (weakerShape(me) == opponent) WIN_POINTS else LOSE_POINTS;
}

fun main() {
    fun part1(input: List<String>): Int {
        var totalScore = 0

        for (line in input) {
            val opponent = Shape.fromChar(line[0])
            val me = Shape.fromChar(line[2])

            totalScore += me.value + gameResult(me, opponent);
        }

        return totalScore;
    }

    fun part2(input: List<String>): Int {
        var totalScore = 0

        for (line in input) {
            val opponent = Shape.fromChar(line[0])
            val result = line[2]

            if (result == 'X') {
                // Lose
                val me = weakerShape(opponent)
                totalScore += me.value
            } else if (result == 'Y') {
                // Draw
                totalScore += opponent.value + DRAW_POINTS
            } else {
                // Win
                val me = strongerShape(opponent)
                totalScore += me.value + WIN_POINTS
            }
        }

        return totalScore
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
