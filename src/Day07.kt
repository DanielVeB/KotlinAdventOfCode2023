fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map {
                val (hand, bid) = it.split(" ")
                CamelCard(hand, bid.toInt())
            }
            .sorted()
            .map { it.bid }
            .reduceIndexed { index, acc, i -> acc + i * (index + 1) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map {
                val (hand, bid) = it.split(" ")
                CamelCard(hand, bid.toInt(), true)
            }
            .sorted()
            .map { it.bid }
            .reduceIndexed { index, acc, i -> acc + i * (index + 1) }

    }

    val testInput = readInput("Day07_test")

    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")

    part1(input).println()
    part2(input).println()
}

class CamelCard(private val cards: String, val bid: Int, private val jokerRule: Boolean = false) :
    Comparable<CamelCard> {

    override fun compareTo(other: CamelCard): Int {
        if (this.cards.typeStrength(jokerRule) == other.cards.typeStrength(jokerRule)) {
            return this.cards.zip(other.cards).map {
                it.first.cardStrength(jokerRule) - it.second.cardStrength(jokerRule)
            }.firstOrNull { it != 0 } ?: run { 0 }
        }
        return this.cards.typeStrength(jokerRule) - other.cards.typeStrength(jokerRule)
    }

}

fun String.typeStrength(jokerRule: Boolean = false): Int {
    val (jokers, occurrences) = countOccurrences(this, jokerRule)
    if (occurrences.getOrElse(0) { 0 } + jokers == 5) {
        return 6
    }
    if (occurrences[0] + jokers == 4) {
        return 5
    }
    if (occurrences[0] + jokers == 3) {
        if (occurrences[1] == 2) {
            return 4
        }
        return 3
    }
    if (occurrences[0] + jokers == 2) {
        if (occurrences[1] == 2) {
            return 2
        }
        return 1
    }
    return 0
}

private fun countOccurrences(hand: String, jokerRule: Boolean = false): Pair<Int, List<Int>> {
    val occurrencesMap = mutableMapOf<Char, Int>()

    hand.filter { !jokerRule || it != 'J' }.forEach {
        occurrencesMap.putIfAbsent(it, 0)
        occurrencesMap[it] = occurrencesMap[it]!! + 1
    }

    val jokers = if (jokerRule) hand.count { it == 'J' } else 0

    return Pair(jokers, occurrencesMap.values.sortedDescending())

}

fun Char.cardStrength(jokerRule: Boolean = false): Int {
    if (this.isDigit()) return this.digitToInt()
    return when {
        isDigit() -> digitToInt()
        equals('T') -> 10
        equals('J') -> if (jokerRule) 1 else 11
        equals('Q') -> 12
        equals('K') -> 13
        equals('A') -> 14
        else -> 0
    }
}