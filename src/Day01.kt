fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val left = it.getFirstDigitFromLeft()
            val right = it.reversed().getFirstDigitFromLeft()
            left * 10 + right
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val left = it.getFirstDigitOrWordDigitFromLeft()
            val right = it.reversed().getFirstDigitOrWordDigitFromLeft(reversed = true)
            left * 10 + right
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()


    val testPart2Input = readInput("Day01p2_test")
    check(part2(testPart2Input) == 281)

    val inputPart2 = readInput("Day01")
    part2(inputPart2).println()
}

fun String.getFirstDigitFromLeft(): Int {
    return if (this[0].isDigit()) {
        this[0].digitToInt()
    } else {
        this.drop(1).getFirstDigitFromLeft()
    }
}

fun String.getFirstDigitOrWordDigitFromLeft(reversed: Boolean = false): Int {
    val digits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    if (this[0].isDigit()) {
        return this[0].digitToInt()
    }

    return digits.keys.firstOrNull {
        if (reversed) {
            this.startsWith(it.reversed())
        } else {
            this.startsWith(it)
        }
    }?.let { digits[it] } ?: run { this.drop(1).getFirstDigitOrWordDigitFromLeft(reversed) }

}