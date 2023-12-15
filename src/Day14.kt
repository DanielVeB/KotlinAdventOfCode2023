fun main() {
    fun part1(input: List<String>): Int {
        val eachColumnSum = input[0].indices.map { i ->
            input.map { it[i] }.joinToString("")
        }.map { it.toCharArray().toList() }.map { it.countTotalNorthLoad(it.size) }

        return eachColumnSum.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day14_test")

    check(part1(testInput) == 136)
    check(part2(testInput) == 0)

    val input = readInput("Day14")

    part1(input).println()
    part2(input).println()
}


fun List<Char>.countTotalNorthLoad(lastBlock: Int): Int {
    if (this.size == 1) {
        return if (this[0] == 'O') lastBlock else 0
    }
    return when (this.first()) {
        '#' -> 0 + this.drop(1).countTotalNorthLoad(this.size -1)
        'O' -> lastBlock + this.drop(1).countTotalNorthLoad(lastBlock - 1)
        else -> 0 + this.drop(1).countTotalNorthLoad(lastBlock)
    }
}