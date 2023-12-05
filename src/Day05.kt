fun main() {
    fun part1(input: List<String>): Int {
        val seedsData = Seeds.fromString(input)
        return seedsData.seeds.map {
            seedsData.maps.fold(it) { acc, triples ->
                triples.firstOrNull { t -> t.first <= acc && t.first + t.third > acc }
                    ?.let { t -> t.second + acc - t.first } ?: run { acc }
            }
        }.min().toInt()
    }

    fun part2(input: List<String>): Int {
        val seedsData = Seeds.fromString(input)
        val seeds = seedsData.seeds
            .chunked(2) { (first, second) -> Pair(first, second) }

        val d = 0.rangeTo(Int.MAX_VALUE).first {
            val v = seedsData.maps.foldRight(it.toLong()) { triples, acc  ->
                val t = triples.firstOrNull { t -> t.second <= acc && t.second + t.third >= acc }
                    ?.let { t -> t.first + (acc - t.second) } ?: run { acc }
                t
            }
            seeds.any { p -> p.first <= v && p.first + p.second >=v  }
        }

        return d

    }

    val testInput = readInput("Day05_test")

    check(part1(testInput) == 35)
    check(part2(testInput) == 46)

    val input = readInput("Day05")

    part1(input).println()
    part2(input).println()
}

class Seeds(val seeds: List<Long>, val maps: List<MutableList<Triple<Long, Long, Long>>>) {

    companion object {
        fun fromString(input: List<String>): Seeds {
            val (_, seedsT) = input[0].split(": ")
            val seeds = seedsT.split(' ').map { it.toLong() }

            val maps = mutableListOf<MutableList<Triple<Long, Long, Long>>>()

            input.drop(2).forEach {
                if (it.contains("map")) {
                    maps.add(mutableListOf())
                } else if (it.isNotEmpty()) {
                    maps.last().add(it.toMap())
                }
            }
            return Seeds(seeds, maps)
        }

        private fun String.toMap(): Triple<Long, Long, Long> {
            val (destination, source, range) = this.split(" ").map { it.toLong() }
            return Triple(source, destination, range)

        }

    }


}