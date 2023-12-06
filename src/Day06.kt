import kotlin.time.measureTime

fun main() {
    fun calculateScore(time: Long, distance: Long): Int {
        var ways = 0
        for (mmps in 1..time) {
            if (mmps * (time - mmps) > distance) {
                ways++
            }
        }
        return ways
    }

    fun part1(input: List<String>): Int {
        val times = mutableListOf<Int>()
        val distances = mutableListOf<Int>()
        input.forEach { line ->
            val splits = line.split(":")
            val values =
                splits[1].trim().split(" ").filter { it.toIntOrNull() != null }.map { it.toInt() }
            when (splits[0]) {
                "Time" -> times.addAll(values)
                "Distance" -> distances.addAll(values)
            }
        }
        var answer = 0
        times.forEachIndexed { index, time ->
            val distance = distances[index]
            val ways = calculateScore(time.toLong(), distance.toLong())
            if (answer == 0) {
                answer = ways
            } else {
                answer *= ways
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var time = 0L
        var distance = 0L
        input.forEach { line ->
            val splits = line.split(":")
            when (splits[0]) {
                "Time" -> time = splits[1].trim().replace(" ", "").toLong()
                "Distance" -> distance = splits[1].trim().replace(" ", "").toLong()
            }
        }
        return calculateScore(time, distance)
    }

    val testInput1 = readInput("Day06_test1")
    check(part1(testInput1) == 288)

    val input = readInput("Day06")

    measureTime {
        part1(input).println()
    }.println()

    val testInput2 = readInput("Day06_test1")
    check(part2(testInput2) == 71503)

    measureTime {
        part2(input).println()
    }.println()
}
