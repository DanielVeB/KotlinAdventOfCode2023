fun main() {
    fun part1(input: List<String>): Int {
        val (moves, nodes) = parseInput(input)

        var currentPoint = Pair("AAA", nodes["AAA"])

        0.rangeTo(Int.MAX_VALUE).forEach {
            if (moves[it % (moves.length)] == 'L') {
                currentPoint = Pair(currentPoint.second!!.first, nodes[currentPoint.second!!.first])
            }
            if (moves[it % (moves.length)] == 'R') {
                currentPoint = Pair(currentPoint.second!!.second, nodes[currentPoint.second!!.second])
            }
            if (currentPoint.first == "ZZZ") {
                return it + 1
            }
        }
        return 0
    }

    fun part2(input: List<String>): Long {
        val (moves, nodes) = parseInput(input)

        return nodes.keys.asSequence().filter { it.endsWith("A") }.map { getFirstEndingWithZ(it, moves, nodes) }.map { it.toLong() }.toList().reduce { acc, next -> lcm(acc, next) }
    }


    val testInput = readInput("Day08_test")

    check(part1(testInput) == 6)

    val testInput2 = readInput("Day08p2test")
    check(part2(testInput2) == 6L)

    val input = readInput("Day08")

    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
    val moves = input[0]

    val nodes = input.drop(2).map {
        val regex = Regex("""(\w+)\s*=\s*\((\w+),\s*(\w+)\)""")
        regex.matchEntire(it)?.let { m ->
            val (firstString, secondString, thirdString) = m.destructured
            Triple(firstString, secondString, thirdString)
        }
    }.associate { it!!.first to Pair(it.second, it.third) }

    return Pair(moves, nodes)
}

fun getFirstEndingWithZ(starting: String, moves: String, nodes: Map<String, Pair<String, String>>): Int {
    var currentPoint = Pair(starting, nodes[starting])

    0.rangeTo(Int.MAX_VALUE).forEach {
        if (moves[it % (moves.length)] == 'L') {
            currentPoint = Pair(currentPoint.second!!.first, nodes[currentPoint.second!!.first])
        }
        if (moves[it % (moves.length)] == 'R') {
            currentPoint = Pair(currentPoint.second!!.second, nodes[currentPoint.second!!.second])
        }
        if (currentPoint.first.endsWith("Z")) {
            return it + 1
        }
    }
    return 0
}
fun lcm(a: Long, b: Long): Long {
    return (a * b) / gcd(a, b)
}
fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}


