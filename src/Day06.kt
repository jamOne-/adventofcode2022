fun main() {
    fun searchForMarker(buffer: String, markerLength: Int): Int {
        val lettersCount = mutableMapOf<Char, Int>()
        for (i in buffer.indices) {
            if (i >= markerLength) {
                val letterToRemove = buffer[i - markerLength]
                if (lettersCount[letterToRemove] == 1) {
                    lettersCount.remove(letterToRemove)
                } else {
                    lettersCount[letterToRemove] = lettersCount[letterToRemove]!! - 1
                }
            }

            val newLetter = buffer[i]
            lettersCount[newLetter] = lettersCount.getOrDefault(newLetter, 0) + 1

            if (lettersCount.keys.size == markerLength) {
                return i + 1
            }
        }

        return -1
    }

    fun part1(input: List<String>): Int {
        return searchForMarker(input[0], 4)
    }

    fun part2(input: List<String>): Int {
        return searchForMarker(input[0], 14)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}