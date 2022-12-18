import java.util.ArrayDeque

data class Day5Command(val count: Int, val from: Int, val to: Int)
data class Day5Data(val stacks: List<ArrayDeque<Char>>, val commands: List<Day5Command>)
val COMMAND_REGEX = """^move (\d+) from (\d+) to (\d+)$""".toRegex()

fun main() {
    fun part1(input: List<String>): String {
        val (stacks, commands) = parseInput(input)

        for (command in commands) {
            val (count, from, to) = command
            for (i in 1..count) {
                val el = stacks[from - 1].pop()
                stacks[to - 1].push(el)
            }
        }

        return stacks.map { stack -> stack.peek() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val (stacks, commands) = parseInput(input)

        for (command in commands) {
            val (count, from, to) = command
            val tempStack = ArrayDeque<Char>()
            for (i in 1..count) {
                tempStack.push(stacks[from - 1].pop())
            }
            for (i in 1..count) {
                stacks[to - 1].push(tempStack.pop())
            }
        }

        return stacks.map { stack -> stack.peek() }.joinToString("")
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun parseInput(input: List<String>): Day5Data {
    val numberOfStacks = (input[0].length + 1) / 4;
    val stacks = List(numberOfStacks) { ArrayDeque<Char>() };

    var lineIndex = 0
    while (input[lineIndex][1] != '1') {
        for (i in 0 until numberOfStacks) {
            if (input[lineIndex][1 + i*4] != ' ') {
                stacks[i].addLast(input[lineIndex][1 + i*4])
            }
        }

        lineIndex += 1
    }
    lineIndex += 2

    val commands = mutableListOf<Day5Command>()
    while (lineIndex < input.size) {
        val match = COMMAND_REGEX.matchEntire(input[lineIndex])!!
        val (count, from, to) = match.destructured

        commands.add(Day5Command(count.toInt(), from.toInt(), to.toInt()))
        lineIndex += 1
    }

    return Day5Data(stacks, commands)
}