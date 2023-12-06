fun main() {
    fun part1(input: List<String>): Int {
        val boatRaces = BoatRace.fromString(input)
        return boatRaces.map { it.getWins().size }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        return 0

    }

    val testInput = readInput("Day06_test")

    check(part1(testInput) == 288)
    check(part2(testInput) == 0)

    val input = readInput("Day06")

    part1(input).println()
    part2(input).println()
}

class BoatRace(private val time: Int, private val record: Int){
    companion object{
        fun fromString(input: List<String>): List<BoatRace>{
            val times = input[0].split(" ").filter { it.isNotEmpty() }.drop(1).map { it.toInt() }
            val distances = input[1].split(" ").filter { it.isNotEmpty() }.drop(1).map { it.toInt() }

            return times.zip(distances).map { BoatRace(it.first, it.second) }
        }
    }

    fun getWins(): List<Int>{
       return 1.rangeTo(this.time).filter {
            (this.time - it) * it > this.record
        }
    }
}