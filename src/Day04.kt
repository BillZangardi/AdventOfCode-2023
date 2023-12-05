import java.util.LinkedList
import java.util.Queue
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        fun calculateScore(matches: Int): Int {
            if (matches == 0) return 0
            return 1 shl (matches - 1)
        }

        var answer = 0
        input.forEach { line ->
            var cardId = 0
            val winningNumbers = mutableListOf<Int>()
            val cardNumbers = mutableListOf<Int>()
            line.split(":", "|").forEachIndexed { index, s ->
                val split = s.trim().split(" ")
                when (index) {
                    0 -> {
                        cardId = split.first { it.toIntOrNull() != null }.toInt()
                    }

                    1 -> {
                        winningNumbers.addAll(split.filter { it.toIntOrNull() != null }
                            .map { it.toInt() })
                    }

                    2 -> {
                        cardNumbers.addAll(split.filter { it.toIntOrNull() != null }
                            .map { it.toInt() })
                    }
                }
            }

            answer += calculateScore(cardNumbers.filter { winningNumbers.contains(it) }.size)
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        data class Game(val id: Int, val winningNumbers: Set<Int>, val cardNumbers: Set<Int>) {
            val matches = winningNumbers.intersect(cardNumbers).size
            val additionalTickets = mutableSetOf<Game>()
            var initialized = false

            fun init(dictionary: Set<Game>) {
                if (!initialized) {
                    if (matches > 0) {
                        for (i in 1..matches) {
                            val match = dictionary.first { it.id == id + i }
                            additionalTickets.add(match)
                        }
                    }

                    initialized = true
                }
            }
        }

        fun Queue<Game>.getAdditionalTickets(): List<Game> {
            val additionalTickets = mutableListOf<Game>()
            while (isNotEmpty()) {
                additionalTickets.addAll(poll().additionalTickets)
            }
            return additionalTickets
        }

        val games = mutableSetOf<Game>()
        input.forEach { line ->
            var cardId = 0
            val winningNumbers = mutableSetOf<Int>()
            val cardNumbers = mutableSetOf<Int>()
            line.split(":", "|").forEachIndexed { index, s ->
                val split = s.trim().split(" ")
                when (index) {
                    0 -> {
                        cardId = split.first { it.toIntOrNull() != null }.toInt()
                    }

                    1 -> {
                        winningNumbers.addAll(split.filter { it.toIntOrNull() != null }
                            .map { it.toInt() })
                    }

                    2 -> {
                        cardNumbers.addAll(split.filter { it.toIntOrNull() != null }
                            .map { it.toInt() })
                    }
                }
            }
            games.add(Game(cardId, winningNumbers, cardNumbers))
        }
        var count = games.size
        games.forEach {
            it.init(games)
        }
        val queue: Queue<Game> = LinkedList<Game>().apply { addAll(games) }
        while (queue.isNotEmpty()) {
            val additionalTickets = queue.getAdditionalTickets()
            count += additionalTickets.size
            queue.addAll(additionalTickets)
        }
        return count
    }

    val testInput1 = readInput("Day04_test1")
    check(part1(testInput1) == 13)

    val input = readInput("Day04")
    measureTime {
        part1(input).println()
    }.println()

    val testInput2 = readInput("Day04_test2")
    check(part2(testInput2) == 30)
    measureTime {
        part2(input).println() // 9236992
    }.println()
}
