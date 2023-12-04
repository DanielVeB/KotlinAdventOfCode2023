import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            ScratchcardsGame.fromString(it).getWinningPriceSize()
        }.filter { it >= 1 }.sumOf { 2.0.pow((it - 1).toDouble()) }.roundToInt()

    }

    fun part2(input: List<String>): Int {
        return 0

    }

    val testInput = readInput("Day04_test")

    check(part1(testInput) == 13)
    check(part2(testInput) == 0)

    val input = readInput("Day04")

    part1(input).println()
    part2(input).println()
}

private class ScratchcardsGame(val id: Int, val bets: List<Int>, val winningNumbers: List<Int>) {

    companion object {
        fun fromString(input: String): ScratchcardsGame {
            val splits = input.split(':', '|')
//            val id = splits[0].split(' ')[1].toInt()
            val bets = splits[1]
                .split(' ')
                .map { it.replace(" ", "") }
                .filter { it.isNotEmpty() }
                .map { it.toInt() }.toList()

            val winningNumbers = splits[2]
                .split(' ')
                .map { it.replace(" ", "") }
                .filter { it.isNotEmpty() }
                .map { it.toInt() }.toList()

            return ScratchcardsGame(0, bets, winningNumbers)
        }
    }


    fun getWinningPriceSize(): Int {
        return this.bets.filter { this.winningNumbers.contains(it) }.size
    }
}

