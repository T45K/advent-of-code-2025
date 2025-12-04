fun main() {
    val sampleInput = readFile("3_sample.txt")
    val input = readFile("3.txt")

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
    val answer = input.sumOf { line ->
        val numbers = line.map { it.digitToInt() }
        (0..<numbers.size - 1).flatMap { i ->
            (i + 1..<numbers.size).map { j ->
                numbers[i] * 10 + numbers[j]
            }
        }.max()
    }

    println(answer)
}

private fun solve2(input: List<String>) {
    val answer = input.sumOf { line ->
        val numbers = line.map { it.digitToInt() }
        generateSequence(numbers) { tmp ->
            val index = (0..<12).firstOrNull {
                tmp[it] < tmp[it + 1]
            } ?: 12
            tmp.subList(0, index) + tmp.subList(index + 1, tmp.size)
        }
            .dropWhile { it.size > 12 }
            .first()
            .joinToString("")
            .toLong()
    }
    println(answer)
}
