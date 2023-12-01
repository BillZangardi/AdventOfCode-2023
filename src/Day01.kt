fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            val digits = line.filter { it.isDigit() }
            total += "${digits.first()}${digits.last()}".toInt()
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val numberStrings = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
        var total = 0
        input.forEach { line ->
            var newLine = line
            numberStrings.forEach { (s, i) ->
                newLine = newLine.replace(s, "$s$i$s")
            }
            val digits = newLine.filter { it.isDigit() }
            total += "${digits.first()}${digits.last()}".toInt()
        }
        return total
    }

    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)

    val input = readInput("Day01")
    part1(input).println()

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)
    part2(input).println()
}
