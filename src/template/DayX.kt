import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput1 = readInput("DayX_test1")
    check(part1(testInput1) == 142)

    val input = readInput("DayX")
    measureTime {
        part1(input).println()
    }.println()

    val testInput2 = readInput("DayX_test2")
    check(part2(testInput2) == 281)

    measureTime {
        part2(input).println()
    }.println()
}
