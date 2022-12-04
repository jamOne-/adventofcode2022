fun main() {
    fun part1(input: List<String>): Int {
        val elves = readElves(input)
        return elves.max()
    }

    fun part2(input: List<String>): Int {
        val elves = readElves(input)
        val sortedElves = elves.sortedDescending()

        return sortedElves[0] + sortedElves[1] + sortedElves[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
fun readElves(input: List<String>): MutableList<Int> {
    val elves = mutableListOf<Int>();
    var currentElf = 0;
    for (line in input) {
		if (line.isEmpty()) {
            elves.add(currentElf);
            currentElf = 0;
        } else {
            currentElf += line.toInt();
        }
    }
    elves.add(currentElf);
    
    return elves;
} 