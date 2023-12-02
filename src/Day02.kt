fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val game = Game.fromString(it)
            if (game.sets.any { t -> t.isGreater(Triple(12, 13, 14)) }) 0 else game.id
        }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            Game.fromString(it).sets.reduce { acc, triple -> acc.max(triple) }
        }.sumOf { it.first * it.second * it.third }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

class Game(val id: Int, val sets: List<Triple<Int, Int, Int>>) { //red,green,blue

    companion object {
        fun fromString(input: String): Game {
            val splits = input.split(": ", "; ")
            val id = splits[0].split(' ')[1].toInt()

            val sets = splits.drop(1).map {
                it.split(", ").associate { color ->
                    color.split(' ').let { c ->
                        c[1] to c[0].toInt()
                    }
                }
            }.toList()
                .map {
                    Triple(
                        it.getOrDefault("red", 0),
                        it.getOrDefault("green", 0),
                        it.getOrDefault("blue", 0)
                    )
                }
            return Game(id, sets)
        }
    }
}

fun Triple<Int, Int, Int>.isGreater(other: Triple<Int, Int, Int>): Boolean {
    return this.first > other.first || this.second > other.second || this.third > other.third
}

fun Triple<Int, Int, Int>.max(other: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(maxOf(this.first, other.first), maxOf(this.second, other.second), maxOf(this.third, other.third))
}