fun main() {
    val sampleInput = readFile("6_sample.txt")
    val input = readFile("6.txt")

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
    val numbers = input.take(input.size - 1).map {
        it.split("\\s+".toRegex())
            .filter(String::isNotEmpty)
            .map(String::toLong)
    }
    val symbols = input.last().split("\\s+".toRegex())
    val answer = numbers[0].indices.sumOf { index ->
        if (symbols[index] == "+") {
            numbers.sumOf { it[index] }
        } else {
            numbers.map { it[index] }.reduce { a, b -> a * b }
        }
    }
    println(answer)
}

private fun transport(input: List<String>): List<String> {
    val col = input.size
    val row = input[0].length
    return (0..<row).map { i ->
        (0..<col).joinToString("") { j -> input[j][row - i - 1].toString() }.trim()
    }
}

private fun solve2(input: List<String>) {
    val symbols = input.last().reversed().toCharArray().toList()
    val transportation = transport(input.dropLast(1))

    val answer = transportation.zip(symbols).chunkedBy { (numberLine, _) -> numberLine.isEmpty() }
        .also { println(it) }
        .sumOf { numberLineAndSymbol ->
            val numbers = numberLineAndSymbol.map { (numberLine, _) -> numberLine.toLong() }
            val symbol = numberLineAndSymbol.last().let { (_, symbol) -> symbol }
            if (symbol == '+') numbers.sum() else numbers.reduce { a, b -> a * b }
        }

    println(answer)
}
