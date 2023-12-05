import kotlin.time.measureTime

fun main() {
    class Mapping(val range1: LongRange, val range2: LongRange)

    fun part1(input: String): Long {
        val seeds = mutableListOf<Long>()
        val seedToSoil = mutableListOf<Mapping>()
        val soilToFertilizer = mutableListOf<Mapping>()
        val fertilizerToWater = mutableListOf<Mapping>()
        val waterToLight = mutableListOf<Mapping>()
        val lightToTemperature = mutableListOf<Mapping>()
        val temperatureToHumidity = mutableListOf<Mapping>()
        val humidityToLocation = mutableListOf<Mapping>()
        val mapList = listOf(
            seedToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemperature,
            temperatureToHumidity,
            humidityToLocation
        )

        input.split("(.+):".toRegex()).filter { it.isNotEmpty() }.forEachIndexed { index, s ->
            val longs =
                s.trim().replace("\n", " ").split(" ").mapNotNull { it.trim().toLongOrNull() }
            if (index == 0) seeds.addAll(longs)
            else {
                longs.chunked(3).forEach { coords ->
                    mapList[index - 1].add(
                        Mapping(
                            LongRange(coords[0], coords[0] + coords[2] - 1),
                            LongRange(coords[1], coords[1] + coords[2] - 1)
                        )
                    )
                }
            }
        }
        var lowestLocation: Long? = null
        seeds.forEach { seed ->
            var currentCoord = seed
            mapList.forEach { map ->
                map.firstOrNull { it.range2.contains(currentCoord) }?.let {
                    val index = currentCoord - it.range2.first
                    currentCoord = it.range1.first + index
                }
            }
            if (lowestLocation == null || currentCoord < lowestLocation!!) {
                lowestLocation = currentCoord
            }
        }
        return lowestLocation!!
    }

    fun part2(input: String): Long {
        val seedRanges = mutableListOf<LongRange>()
        val seedToSoil = mutableListOf<Mapping>()
        val soilToFertilizer = mutableListOf<Mapping>()
        val fertilizerToWater = mutableListOf<Mapping>()
        val waterToLight = mutableListOf<Mapping>()
        val lightToTemperature = mutableListOf<Mapping>()
        val temperatureToHumidity = mutableListOf<Mapping>()
        val humidityToLocation = mutableListOf<Mapping>()
        val mapList = listOf(
            seedToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemperature,
            temperatureToHumidity,
            humidityToLocation
        )

        input.split("(.+):".toRegex()).filter { it.isNotEmpty() }.forEachIndexed { index, s ->
            val longs =
                s.trim().replace("\n", " ").split(" ").mapNotNull { it.trim().toLongOrNull() }
            if (index == 0) {
                longs.chunked(2).forEach { ranges ->
                    seedRanges.add(LongRange(ranges[0], ranges[0] + ranges[1]))
                }
            } else {
                longs.chunked(3).forEach { coords ->
                    mapList[index - 1].add(
                        Mapping(
                            LongRange(coords[0], coords[0] + coords[2] - 1),
                            LongRange(coords[1], coords[1] + coords[2] - 1)
                        )
                    )
                }
            }
        }
        seedRanges.sortBy { it.first }
        val copy = mutableListOf<LongRange>().apply {
            addAll(seedRanges)
        }.sortBy { it.last }
        var lowestLocation: Long? = null
        seedRanges.forEach { seeds ->
            seeds.forEach {  seed ->
                var currentCoord = seed
                mapList.forEach { map ->
                    map.firstOrNull { it.range2.contains(currentCoord) }?.let {
                        val index = currentCoord - it.range2.first
                        currentCoord = it.range1.first + index
                    }
                }
                if (lowestLocation == null || currentCoord < lowestLocation!!) {
                    lowestLocation = currentCoord
                }
            }
        }
        println("Answer: $lowestLocation")
        return lowestLocation!!
    }

    val testInput1 = readFullInput("Day05_test1")
    check(part1(testInput1) == 35L)

    val input = readFullInput("Day05")
    measureTime {
        part1(input).println()
    }.println()

    val testInput2 = readFullInput("Day05_test1")
    check(part2(testInput2) == 46L)
    measureTime {
        part2(input).println()
    }.println()
}
