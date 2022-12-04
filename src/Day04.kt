fun main() {
    fun part1(input: List<String>): Int {
        var count = 0

        for (assignment in input) {
            val pairs = assignment.split(',')
            val elf1 = pairs[0].split('-').map { it.toInt() }
            val elf2 = pairs[1].split('-').map { it.toInt() }

            if (elf1[0] <= elf2[0] && elf1[1] >= elf2[1] || elf2[0] <= elf1[0] && elf2[1] >= elf1[1]) {
                count += 1
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0

        for (assignment in input) {
            val pairs = assignment.split(',')
            val elf1 = pairs[0].split('-').map { it.toInt() }
            val elf2 = pairs[1].split('-').map { it.toInt() }

            if (!(elf1[0] > elf2[1] || elf2[0] > elf1[1])) {
                count += 1
            }
        }

        return count    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
