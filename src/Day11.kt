import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return calculateDistance(input, 1).toInt()
    }

    fun part2(input: List<String>, expansion: Int): Long {
        return calculateDistance(input, expansion)
    }

    val testInput = readInput("Day11_test")

    check(part1(testInput) == 374)
    check(part2(testInput, 9) == 1030L)
    check(part2(testInput, 99) == 8410L)

    val input = readInput("Day11")

    part1(input).println()
    part2(input, 1000000 - 1).println()
}

private fun calculateDistance(input: List<String>, expansion: Int): Long {
    val galaxies = input.mapIndexed { i, row ->
        row.mapIndexed { j, column ->
            if (column == '#') {
                Pair(i, j)
            } else null
        }.filterNotNull()
    }.flatten()

    val columnsToExpand = 0.rangeTo(galaxies.map { it.second }.max()).filter {
        !galaxies.map { g -> g.second }.distinct().contains(it)
    }

    val rowsToExpand = 0.rangeTo(galaxies.map { it.first }.max()).filter {
        !galaxies.map { g -> g.first }.distinct().contains(it)
    }

    val expandedGalaxies = galaxies
        .map { g ->
            g.expandColumn(columnsToExpand.count { c -> g.second > c } * expansion)

        }.map { g ->
            g.expandRow(rowsToExpand.count { r -> g.first > r } * expansion)
        }

    return countDistance(expandedGalaxies)
}

private fun Pair<Int, Int>.expandColumn(expand: Int): Pair<Int, Int> {
    return Pair(this.first, this.second + expand)
}

private fun Pair<Int, Int>.expandRow(expand: Int): Pair<Int, Int> {
    return Pair(this.first + expand, this.second)
}

private fun countDistance(galaxies: List<Pair<Int, Int>>): Long {
    if (galaxies.size == 2) {
        return abs(galaxies[0].first - galaxies[1].first).toLong() + abs(galaxies[0].second - galaxies[1].second).toLong()
    }
    return galaxies.sumOf {
        abs(galaxies[0].first - it.first).toLong() + abs(galaxies[0].second - it.second).toLong()
    } + countDistance(galaxies.drop(1))
}