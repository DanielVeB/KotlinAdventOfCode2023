import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val symbols = input.flatMapIndexed { row, s ->
            s.mapIndexedNotNull { column, c ->
                if (!(c.isDigit() || c == '.')) {
                    Pair(row, column)
                } else null
            }.toList()
        }

        val numbers = getNumbers(input)

        return numbers.filter { n -> symbols.any { s -> n.isAdjacentToSymbol(s) } }.sumOf { it.value.toInt() }
    }

    fun part2(input: List<String>): Int {
        val gears = input.flatMapIndexed { row, s ->
            s.mapIndexedNotNull { column, c ->
                if (c == '*') {
                    Pair(row, column)
                } else null
            }.toList()
        }

        val numbers = getNumbers(input)

        return gears
            .map { it.getAdjacentGearNumbers(numbers) }
            .filter { it.size > 1 }
            .sumOf {
                it.map { n -> n.value.toInt() }.reduce { acc, i -> acc * i }
            }

    }

    val testInput = readInput("Day03_test")

    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")

    part1(input).println()
    part2(input).println()
}


private fun getNumbers(input: List<String>): List<Number> {
    return input.flatMapIndexed { row, s ->
        val rows = s.foldIndexed(mutableListOf<Pair<Int, String>>()) { index, acc, c ->
            when {
                c.isDigit() -> {
                    if (acc.isEmpty() || acc.last().second.isEmpty()) {
                        acc.add(index to c.toString())
                    } else {
                        val updatedNumber = acc.last().second + c
                        acc[acc.lastIndex] = index to updatedNumber
                    }
                }

                else -> {
                    if (acc.isNotEmpty() && acc.last().second.isNotEmpty()) {
                        acc.add(index to "")
                    }
                }
            }
            acc
        }.filter {
            it.second.isNotEmpty()
        }.map {
            Pair(row, it.first) to it.second
            Number(it.second, row, it.first - it.second.length + 1)
        }

        rows
    }
}

fun Pair<Int, Int>.getAdjacentGearNumbers(numbers: List<Number>): List<Number> {
    return numbers.filter { it.isAdjacentToSymbol(this) }
}

class Number(val value: String, val row: Int, val column: Int) {

    fun isAdjacentToSymbol(symbol: Pair<Int, Int>): Boolean {
        if ((row - symbol.first).absoluteValue > 1) {
            return false
        }
        return (column - 1).rangeTo(column + value.length).contains(symbol.second)
    }
}