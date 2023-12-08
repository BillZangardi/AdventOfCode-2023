import kotlin.time.measureTime

fun main() {
    val coordRegex = "\\((.{3}), (.{3})\\)".toRegex()
    fun part1(input: String): Int {
        val map = mutableMapOf<String, Pair<String, String>>()
        val (instructions, coordinatesPortion) = input.split("\r\n\r\n")
        val coords = coordinatesPortion.split("\n")
        coords.forEach { line ->
            val split = line.split("=")
            coordRegex.find(split[1].trim())?.let {
                map[split[0].trim()] = Pair(it.groupValues[1], it.groupValues[2])
            }
        }
        var current = "AAA"
        var steps = 0
        while (current != "ZZZ") {
            instructions.toCharArray().forEach { dir ->
                steps++
                when (dir) {
                    'L' -> map[current]?.first?.let { current = it }

                    'R' -> map[current]?.second?.let { current = it }
                }
                if (current == "ZZZ") {
                    return@forEach
                }
            }
        }
        return steps
    }

    fun part2(input: String): Long {
        val map = mutableMapOf<String, Pair<String, String>>()
        val (instructions, coordinatesPortion) = input.split("\r\n\r\n")
        val coords = coordinatesPortion.split("\n")
        coords.forEach { line ->
            val split = line.split("=")
            coordRegex.find(split[1].trim())?.let {
                map[split[0].trim()] = Pair(it.groupValues[1], it.groupValues[2])
            }
        }
        val startingNodes = map.filter { it.key[2] == 'A' }
        val currentNodes = mutableListOf<String>().apply {
            addAll(startingNodes.map { it.key })
        }
        val stepList = mutableListOf<Long>()
        currentNodes.forEach outer@{
            var steps = 0L
            var current = it
            while (current[2] != 'Z') {
                instructions.toCharArray().forEach { dir ->
                    steps++
                    when (dir) {
                        'L' -> map[current]?.first?.let { coord -> current = coord }

                        'R' -> map[current]?.second?.let { coord -> current = coord }
                    }
                    if (current[2] == 'Z') {
                        stepList.add(steps)
                        return@outer
                    }
                }
            }
        }
        return findLCMOfListOfNumbers(stepList)
    }

    val testInput1 = readFullInput("Day08_test1")
    check(part1(testInput1) == 2)

    val input = readFullInput("Day08")
    measureTime {
        part1(input).println()
    }.println()

    val testInput2 = readFullInput("Day08_test2")
    check(part2(testInput2) == 6L)

    measureTime {
        part2(input).println() // 14616363770447
    }.println()
}
