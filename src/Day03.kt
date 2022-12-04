fun letterValue(char: Char): Int {
    return if (char.isLowerCase()) char.minus('a') + 1 else char.minus('A') + 27
}

fun main() {
    fun part1(input: List<String>): Int {
        var totalPriority = 0

        for (rucksack in input) {
            val compartment1 = rucksack.substring(0, rucksack.length / 2)
            val compartment2 = rucksack.substring(rucksack.length / 2)

            val commonLetters = compartment1.asIterable().intersect(compartment2.asIterable().toSet())
            check(commonLetters.size == 1)
            val commonLetter = commonLetters.first()

            totalPriority += letterValue(commonLetter)
        }

        return totalPriority
    }

    fun part2(input: List<String>): Int {
        var totalPriority = 0
        val groups = input.chunked(3)

        for (group in groups) {
            val commonLetters = group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet())
            check(commonLetters.size == 1)
            val commonLetter = commonLetters.first()

            totalPriority += letterValue(commonLetter)
        }

        return totalPriority
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
