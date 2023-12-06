fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].split(" ").filter { it.isNotEmpty() }.drop(1).map { it.toLong() }
        val distances = input[1].split(" ").filter { it.isNotEmpty() }.drop(1).map { it.toLong() }

        return times.zip(distances).map { BoatRace(it.first, it.second) }
            .map { it.getWins().size }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split(" ").filter { it.isNotEmpty() }.drop(1).reduce { acc, s -> acc + s  }.toLong()
        val distance = input[1].split(" ").filter { it.isNotEmpty() }.drop(1).reduce { acc, s -> acc + s  }.toLong()

        val firstWinIndex = BoatRace(time,distance).getFirstWinFromLeft()

        return (time - 2 * firstWinIndex + 1).toInt()
    }

    val testInput = readInput("Day06_test")

    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")

    part1(input).println()
    part2(input).println()
}

class BoatRace(private val time: Long, private val record: Long){

    fun getWins(): List<Long>{
       return 1.rangeTo(this.time).filter {
            (this.time - it) * it > this.record
        }
    }

    fun getFirstWinFromLeft() : Long{
        return 1.rangeTo(this.time).first {
            (this.time - it) * it > this.record
        }
    }

}