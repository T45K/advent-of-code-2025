fun main() {
    val sampleInput = readFile("5_sample.txt")
    val input = readFile("5.txt")

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

private fun parseRanges(input: List<String>): List<LongRange> = input.takeWhile { it.isNotEmpty() }
    .map {
        val (begin, end) = it.split("-")
        begin.toLong()..end.toLong()
    }

private fun solve1(input: List<String>) {
    val ranges = parseRanges(input)
    val ingredients = input.dropWhile { it.isNotEmpty() }.drop(1).map { it.toLong() }

    val answer = ingredients.count { ranges.any { range -> it in range } }

    println(answer)
}

private fun solve2(input: List<String>) {
    val ranges = parseRanges(input)

    val answer = ranges
        .sortedBy { it.first }
        .fold(0L to 0L) { (total, prevEnd), range ->
            val begin = range.first
            val end = range.last
            when {
                /* prevBegin <= begin < */ end < prevEnd -> total to prevEnd
                begin <= prevEnd /* < end */ -> (total + end - prevEnd) to end
                else /* prevEnd < begin < end */ -> (total + end - begin + 1) to end
            }
        }.first

    println(answer)
}
