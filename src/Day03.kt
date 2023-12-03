fun main() {
    val numbersRegex = Regex("\\d+")
    fun part1(input: List<String>): Int {
        var answer = 0
        fun checkLine(s: String, range: IntRange): Boolean {
            val checkRange = IntRange(
                if (range.first > 0) range.first - 1 else range.first,
                if (range.last < s.length - 1) range.last + 1 else range.last
            )
            return s.subSequence(checkRange).any { char -> char != '.' && !char.isDigit() }
        }

        input.forEachIndexed { index, s ->
            numbersRegex.findAll(s).forEach {
                when {
                    it.range.first > 0 && s[it.range.first - 1] != '.' -> {
                        answer += it.value.toInt()
                    }

                    it.range.last < s.length - 1 && s[it.range.last + 1] != '.' -> {
                        answer += it.value.toInt()
                    }

                    index > 0 && checkLine(input[index - 1], it.range) -> {
                        answer += it.value.toInt()
                    }

                    index + 1 < input.size && checkLine(input[index + 1], it.range) -> {
                        answer += it.value.toInt()
                    }
                }
            }
        }
        return answer
    }

    val gearsRegex = Regex("\\*")
    fun part2(input: List<String>): Int {
        var answer = 0

        fun checkLine(s: String, range: IntRange): List<Int>? {
            val checkRange = IntRange(
                if (range.first > 0) range.first - 1 else range.first,
                if (range.last < s.length - 1) range.last + 1 else range.last
            )
            if (s.subSequence(checkRange).any { it.isDigit() }) {
                return numbersRegex.findAll(s)
                    .filter { num -> checkRange.contains(num.range.first) || checkRange.contains(num.range.last) }
                    .map { it.value.toInt() }.toList()
            }
            return null
        }

        input.forEachIndexed { index, s ->
            gearsRegex.findAll(s).forEach { gear ->
                val gearRatios = mutableListOf<Int>()
                val sameLineNums = numbersRegex.findAll(s)
                if (gear.range.first > 0 && s[gear.range.first - 1] != '.') {
                    sameLineNums.firstOrNull { num -> num.range.contains(gear.range.first - 1) }?.value?.toInt()
                        ?.let { match -> gearRatios.add(match) }
                }
                if (gear.range.last < s.length - 1 && s[gear.range.last + 1] != '.') {
                    sameLineNums.firstOrNull { num -> num.range.contains(gear.range.last + 1) }?.value?.toInt()
                        ?.let { match -> gearRatios.add(match) }
                }
                checkLine(input[index - 1], gear.range)?.let { matches ->
                    gearRatios.addAll(matches)
                }
                checkLine(input[index + 1], gear.range)?.let { matches ->
                    gearRatios.addAll(matches)
                }
                if (gearRatios.size == 2) {
                    answer += gearRatios[0] * gearRatios[1]
                }
            }
        }
        return answer
    }

    val testInput1 = readInput("Day03_test1")
    check(part1(testInput1) == 925)

    val input = readInput("Day03")
    part1(input).println()

    val testInput2 = readInput("Day03_test2")
    val test = part2(testInput2)
    test.println()
    check(test == 467835)
    part2(input).println()
}