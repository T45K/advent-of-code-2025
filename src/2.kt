fun main() {
    val sampleInput = readFile("2_sample.txt")
    val input = readFile("2.txt")

    println("Solve 1: Sample")
    solve1(sampleInput)
    println()

    println("Solve 1: Actual")
    solve1(input)
    println()

    println("Solve 2: Sample")
    solve2(sampleInput)
    println()

    println("Solve 2: Actual")
    solve2(input)
    println()
}

private fun solve1(input: List<String>) {
    val ranges = input[0].split(",").map { pair -> pair.split("-").let { it[0].toLong() to it[1].toLong() } }
    val answer = ranges.sumOf { (begin, end) ->
        (begin..end).filter { id ->
            val idString = id.toString()
            val length = idString.length
            length % 2 == 0 && idString.substring(0, length / 2) == idString.substring(length / 2)
        }.sum()
    }

    println(answer)
}

private fun solve2(input: List<String>) {
    val ranges = input[0].split(",").map { pair -> pair.split("-").let { it[0].toLong() to it[1].toLong() } }
    val answer = ranges.sumOf { (begin, end) ->
        (begin..end).filter { id ->
            val idString = id.toString()
            val length = idString.length
            (1..length / 2).any { base ->
                val div = length / base
                length % base == 0 && (0..<div).map { idString.substring(it * base, it * base + base) }.distinct().size == 1
            }
        }.sum()
    }

    println(answer)
}
