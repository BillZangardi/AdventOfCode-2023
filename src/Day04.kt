import java.util.Date

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
        data class Game(val id: Int, val winningNumbers: List<Int>, val cardNumbers: List<Int>) {
            val matches = cardNumbers.filter { winningNumbers.contains(it) }.size
            val additionalTickets = mutableListOf<Game>()
            var initialized = false

            fun init(dictionary: List<Game>) {
                if (!initialized) {
                    if (matches > 0) {
                        for (i in 1..matches) {
                            val match = dictionary.first { it.id == id + i }
                            match.init(dictionary)
                            additionalTickets.add(match)
                        }
                    }

                    initialized = true
                }
            }
        }

        fun MutableList<Game>.getAdditionalTickets(): List<Game> {
            val additionalTickets = mutableListOf<Game>()
            this.forEach { game ->
                additionalTickets.addAll(game.additionalTickets)
            }
            return additionalTickets
        }

        val games = mutableListOf<Game>()
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
            games.add(Game(cardId, winningNumbers, cardNumbers))
        }
        var count = games.size
        games.forEach {
            it.init(games)
        }
        val queue = mutableListOf<Game>().apply { addAll(games) }
        while (queue.isNotEmpty()) {
            val additionalTickets = queue.getAdditionalTickets()
            count += additionalTickets.size
            queue.clear()
            queue.addAll(additionalTickets)
        }
        return count
    }

    val testInput1 = readInput("Day04_test1")
    check(part1(testInput1) == 13)

    val input = readInput("Day04")
    part1(input).println()

    val testInput2 = readInput("Day04_test2")
    check(part2(testInput2) == 30)
    val start = Date()
    part2(input).println() // 9236992
    val end = Date()
    println(end.time - start.time)
}
