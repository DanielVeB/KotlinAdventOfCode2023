fun main() {
    fun part1(input: List<String>): Int {
        val sensorValues = input.map { it.split(" ").map { d -> d.toInt() } }

        return sensorValues.sumOf { sensor -> extrapolateNextValue(sensor) }
    }

    fun part2(input: List<String>): Int {
        val sensorValues = input.map { it.split(" ").map { d -> d.toInt() } }

        return sensorValues.sumOf { sensor -> extrapolatePreviousValue(sensor) }

    }

    val testInput = readInput("Day09_test")

    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")

    part1(input).println()
    part2(input).println()
}


private fun extrapolateNextValue(input: List<Int>): Int {
    val differences = (1 until input.size).map {
        input[it] - input[it - 1]
    }
    return if (differences.all { it == 0 }) input.last()
    else input.last() + extrapolateNextValue(differences)
}

private fun extrapolatePreviousValue(input: List<Int>): Int {
    val differences = (1 until input.size).map {
        input[it] - input[it - 1]
    }
    return if (differences.all { it == 0 }) input.first()
    else  input.first() - extrapolatePreviousValue(differences)
}

