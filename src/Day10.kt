fun main() {
    fun part1(input: List<String>): Int {
        val matrix: Array<CharArray> = input.map { it.toCharArray() }.toTypedArray()

        var moves = 1
        var direction = Direction.RIGHT
        var point = findStartingPoint(matrix) + direction

        while (matrix[point] != 'S') {
            direction = Direction.from(matrix[point], direction)
            point += direction
            moves++
        }

        return moves / 2
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day10_test")

    check(part1(testInput) == 78)
//    check(part2(testInput) == 10)

    val input = readInput("Day10")

    part1(input).println()
    part2(input).println()
}

private enum class Direction(val i: Int, val j: Int) {
    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0),
    DOWN(1, 0);

    companion object {
        fun from(c: Char, d: Direction): Direction {
            return when (c) {
                '|' -> if (d == UP) UP else DOWN
                '-' -> if (d == RIGHT) RIGHT else LEFT

                'F' -> if (d == LEFT) DOWN else RIGHT
                'L' -> if (d == LEFT) UP else RIGHT

                'J' -> if (d == RIGHT) UP else LEFT
                '7' -> if (d == RIGHT) DOWN else LEFT
                else -> RIGHT
            }
        }
    }

}



private fun findStartingPoint(matrix: Array<CharArray>): Pair<Int, Int> {
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (matrix[i][j] == 'S') {
                return Pair(i, j)
            }
        }
    }
    return Pair(0, 0)
}

private operator fun Array<CharArray>.get(pair: Pair<Int, Int>) = this[pair.first][pair.second]

private operator fun Pair<Int, Int>.plus(d: Direction) = Pair(this.first + d.i, this.second + d.j)

