import kotlin.time.measureTime

enum class HandType {
    FIVE_OAK, FOUR_OAK, FULL, THREE_OAK, TWO_PAIR, ONE_PAIR, HIGH;

    fun afterJoker(jokers: Int): HandType {
        return when (this) {
            FIVE_OAK -> this
            FOUR_OAK -> if (jokers > 0) FIVE_OAK else this
            FULL -> this // No Jokers Possible
            THREE_OAK -> if (jokers > 0) FOUR_OAK.afterJoker(jokers - 1) else this
            TWO_PAIR -> if (jokers == 1) FULL else this // Only 1 or less possible
            ONE_PAIR -> if (jokers > 0) THREE_OAK.afterJoker(jokers - 1) else this
            HIGH -> if (jokers > 0) ONE_PAIR.afterJoker(jokers - 1) else this
        }
    }
}

fun main() {
    val rankWithoutJoker = setOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    val JOKER = 'J'
    val rankWithJoker = setOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', JOKER)

    data class Hand(val cards: CharArray, val bet: Int, val playJoker: Boolean = false) :
        Comparable<Hand> {
        val matches =
            if (playJoker) cards.filter { it != JOKER }.groupBy { it } else cards.groupBy { it }
        val jokers = if (playJoker) cards.count { it == JOKER } else 0

        val handType: HandType = when {
            matches.filter { it.value.size == 5 }.isNotEmpty() -> HandType.FIVE_OAK
            matches.filter { it.value.size == 4 }.isNotEmpty() -> HandType.FOUR_OAK
            matches.filter { it.value.size == 3 }.isNotEmpty() -> {
                if (matches.filter { it.value.size == 2 }.size == 1) {
                    HandType.FULL
                } else {
                    HandType.THREE_OAK
                }
            }

            matches.filter { it.value.size == 2 }.size == 2 -> HandType.TWO_PAIR
            matches.filter { it.value.size == 2 }.size == 1 -> HandType.ONE_PAIR
            else -> HandType.HIGH
        }.afterJoker(jokers)

        override fun compareTo(other: Hand): Int {
            if (handType.ordinal < other.handType.ordinal) return -1 // Higher hand
            if (handType.ordinal > other.handType.ordinal) return 1 // Lower hand
            if (handType == other.handType) {
                cards.forEachIndexed { index, c ->
                    if (playJoker) {
                        if (rankWithJoker.indexOf(c) < rankWithJoker.indexOf(other.cards[index])) {
                            return -1 // Higher kicker
                        } else if (rankWithJoker.indexOf(c) > rankWithJoker.indexOf(other.cards[index])) {
                            return 1 // Lower kicker
                        }
                    } else {
                        if (rankWithoutJoker.indexOf(c) < rankWithoutJoker.indexOf(other.cards[index])) {
                            return -1 // Higher kicker
                        } else if (rankWithoutJoker.indexOf(c) > rankWithoutJoker.indexOf(other.cards[index])) {
                            return 1 // Lower kicker
                        }
                    }
                }
            }
            return 0
        }
    }

    fun part1(input: List<String>): Int {
        val hands = mutableListOf<Hand>()
        input.forEach { line ->
            val (cards, bet) = line.split(" ")
            hands.add(Hand(cards.toCharArray(), bet.toInt()))
        }
        hands.sortBy { it }
        return hands.sumOf { it.bet * (hands.size - hands.indexOf(it)) }
    }

    fun part2(input: List<String>): Int {
        val hands = mutableListOf<Hand>()
        input.forEach { line ->
            val (cards, bet) = line.split(" ")
            hands.add(Hand(cards.toCharArray(), bet.toInt(), true))
        }
        hands.sortBy { it }
        return hands.sumOf { it.bet * (hands.size - hands.indexOf(it)) }
    }

    val testInput1 = readInput("Day07_test1")
    check(part1(testInput1) == 6440)

    val input = readInput("Day07")
    measureTime {
        part1(input).println() // 248217452
    }.println()

    val testInput2 = readInput("Day07_test1")
    check(part2(testInput2) == 5905)

    measureTime {
        part2(input).println() // 245576185
    }.println()
}
