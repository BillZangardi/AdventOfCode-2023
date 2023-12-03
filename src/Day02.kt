fun main() {
    fun part1(input: List<String>): Int {
        var answer = 0
        input.forEach { line ->
            var id = 0
            var valid = true
            line.split(":", ",", ";").forEachIndexed { index, s ->
                val split = s.trim().split(" ")
                if (index == 0) {
                    id = split[1].toInt()
                } else {
                    when (split[1]) {
                        "red" -> if (split[0].toInt() > 12) valid = false
                        "green" -> if (split[0].toInt() > 13) valid = false
                        "blue" -> if (split[0].toInt() > 14) valid = false
                    }
                }
            }
            if (valid) answer += id
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        input.forEach { line ->
            var game = 0
            var requiredRed = 0
            var requiredGreen = 0
            var requiredBlue = 0
            line.split(":", ",", ";").forEachIndexed { index, s ->
                val split = s.trim().split(" ")
                if (index == 0) {
                    game = split[1].toInt()
                } else {
                    val count = split[0].toInt()
                    when (split[1]) {
                        "red" -> if (count > requiredRed) requiredRed = count
                        "green" -> if (count > requiredGreen) requiredGreen = count
                        "blue" -> if (count > requiredBlue) requiredBlue = count
                    }
                }
            }
            answer += (requiredRed * requiredGreen * requiredBlue)
        }
        return answer
    }

    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1) == 8)

    val input = readInput("Day02")
    part1(input).println()

    val testInput2 = readInput("Day02_test2")
    check(part2(testInput2) == 2286)
    part2(input).println()
}
