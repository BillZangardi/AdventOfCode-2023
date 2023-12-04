fun main() {
    fun part1(input: List<String>): Int {
        fun calculateScore(matches: Int): Int {
            if (matches == 0) return 0
            if (matches == 1) return 1
            var score = 1
            for (i in 2..matches) {
                score *= 2
            }
            return score
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
            var hasBeenProcessed = false
        }

        fun MutableList<Game>.getAdditionalTickets(): List<Game> {
            val additionalTickets = mutableListOf<Game>()
            this.forEach { game ->
                if (!game.hasBeenProcessed) {
                    val matches = game.cardNumbers.filter { game.winningNumbers.contains(it) }.size
                    for (i in 1..matches) {
                        additionalTickets.add(
                            this.first { it.id == game.id + i }.copy()
                                .apply { hasBeenProcessed = false })
                    }
                    game.hasBeenProcessed = true
                }
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
        while (games.any { !it.hasBeenProcessed }) {
            val additionalTickets = games.getAdditionalTickets()
            games.addAll(additionalTickets)
        }
        return games.size
    }

    val testInput1 = readInput("Day04_test1")
    check(part1(testInput1) == 13)

    val input = readInput("Day04")
    part1(input).println()

    val testInput2 = readInput("Day04_test2")
    check(part2(testInput2) == 30)
    part2(input).println()
}
