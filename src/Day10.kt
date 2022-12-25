sealed class Day10Command {
    object Noop : Day10Command()
    class AddX(val v: Int): Day10Command()

    companion object {
        fun fromString(string: String): Day10Command {
            return when {
                string == "noop" -> Noop
                string.startsWith("addx") -> AddX(string.split(" ")[1].toInt())
                else -> throw IllegalArgumentException()
            }
        }
    }
}

fun main() {
    class Device constructor (commands: List<Day10Command>) {
        var register = 1
        var queue = ArrayDeque(commands.map { command -> Pair(command, 0) })

        fun tick() {
            val (command, cycles) = queue[0]

            if (command is Day10Command.AddX) {
                if (cycles == 0) {
                    queue[0] = Pair(command, 1)
                    return
                } else {
                    register += command.v
                }
            }

            queue.removeFirst()
        }
    }

    fun part1(input: List<String>): Int {
        val cyclesToConsider = listOf(20, 60, 100, 140, 180, 220)
        var strength = 0
        val device = Device(input.map { line -> Day10Command.fromString(line) })

        for (cycle in 1..220) {
            if (cycle in cyclesToConsider) {
                strength += cycle * device.register
            }

            device.tick()
        }

        return strength
    }

    fun part2(input: List<String>): Int {
        val WIDTH = 40
        val HEIGHT = 6

        val device = Device(input.map { line -> Day10Command.fromString(line) })
        for (row in 0 until HEIGHT) {
            for (col in 0 until WIDTH) {
                if (col >= device.register - 1 && col <= device.register + 1) {
                    print("#")
                } else {
                    print(".")
                }

                device.tick()
            }

            print('\n')
        }

        return 0
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
