fun main() {
    val input = readFile("1.txt")
    solve2(input)
}

fun solve1(input: List<String>) {
    val answer = input.map { it[0] to it.substring(1).toInt() }
        .runningFold(50) { prev, (direction, steps) ->
            ((if (direction == 'L') prev - steps else prev + steps) + 100) % 100
        }
        .drop(0)
        .count { it == 0 }

    println(answer)
}

fun solve2(input: List<String>) {
    val answer = input.map { it[0] to it.substring(1).toInt() }
        .fold(0 to 50) { (counter, prev), (direction, steps) ->
            val (counterInThisLoop, current) = (0..<steps).fold(0 to prev) { (tmpCounter, current), _ ->
                val next = if (direction == 'L') current - 1 else current + 1
                val normalized = (next + 100) % 100
                if (normalized == 0) tmpCounter + 1 to 0 else tmpCounter to normalized
            }
            counter + counterInThisLoop to current
        }.first
    println(answer)
}
