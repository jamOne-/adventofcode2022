enum class Direction {
    LEFT, RIGHT, DOWN, UP
}

fun main() {
    fun getNextPosition(position: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
        val (x, y) = position
        return when (direction) {
            Direction.UP -> Pair(x, y - 1)
            Direction.DOWN -> Pair(x, y + 1)
            Direction.LEFT -> Pair(x - 1, y)
            Direction.RIGHT -> Pair(x + 1, y)
        }
    }

    fun isValidPosition(grid: List<String>, position: Pair<Int, Int>): Boolean {
        return position.first >= 0 && position.first < grid[0].length && position.second >= 0 && position.second < grid.size
    }

    fun getHeight(grid: List<String>, position: Pair<Int, Int>): Char {
        return grid[position.first][position.second]
    }

    fun addToSetWhenHigher(grid: List<String>, start: Pair<Int, Int>, direction: Direction, result: MutableSet<Pair<Int, Int>>) {
        result.add(start)

        var maxHeight = getHeight(grid, start)
        var currentPosition = getNextPosition(start, direction)

        while (isValidPosition(grid, currentPosition)) {
            val height = getHeight(grid, currentPosition)

            if (height > maxHeight) {
                result.add(currentPosition)
                maxHeight = height
            }

            currentPosition = getNextPosition(currentPosition, direction)
        }
    }

    fun part1(input: List<String>): Int {
        var result = mutableSetOf<Pair<Int, Int>>()
        val rows = input.size
        val cols = input[0].length

        for (y in 0 until rows) {
            addToSetWhenHigher(input, Pair(0, y), Direction.RIGHT, result)
            addToSetWhenHigher(input, Pair(cols - 1, y), Direction.LEFT, result)
        }

        for (x in 0 until cols) {
            addToSetWhenHigher(input, Pair(x, 0), Direction.DOWN, result)
            addToSetWhenHigher(input, Pair(x, rows - 1), Direction.UP, result)
        }

        return result.size
    }

    fun visibleTrees(grid: List<String>, start: Pair<Int, Int>, direction: Direction): Int {
        val startHeight = getHeight(grid, start)
        var trees = 0
        var currentPosition = getNextPosition(start, direction)

        while(isValidPosition(grid, currentPosition)) {
            trees += 1

            if (getHeight(grid, currentPosition) >= startHeight) {
                break
            } else {
                currentPosition = getNextPosition(currentPosition, direction)
            }
        }

        return trees
    }

    fun getScenicScore(grid: List<String>, position: Pair<Int, Int>): Int {
        var score = 1

        for (direction in listOf(Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP)) {
            score *= visibleTrees(grid, position, direction)
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var bestScore = 1

        for (y in 0 until input.size) {
            for (x in 0 until input[0].length) {
                bestScore = Math.max(bestScore, getScenicScore(input, Pair(x, y)))
            }
        }

        return bestScore
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}