import kotlin.math.min

fun main() {
    val sampleInput = readFile("10_sample.txt")
    val input = readFile("10.txt")

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
        val split = line.split(" ")
        val indicatorLights = split[0].removePrefix("[").removeSuffix("]")
        val toggleButtons = split.subList(1, split.size - 1).map { it.removePrefix("(").removeSuffix(")") }.map { it.split(",").map(String::toInt) }
        val tmp = String(CharArray(indicatorLights.length) { '.' })
        toggle(0, tmp, toggleButtons, indicatorLights, 0)
    }
    println(answer)
}

private fun toggle(index: Int, tmp: String, toggleButtons: List<List<Int>>, indicatorLights: String, count: Int): Int {
    return if (index < toggleButtons.size) {
        val countWithoutFlip = toggle(index + 1, tmp, toggleButtons, indicatorLights, count)
        val charArray = tmp.toCharArray()
        toggleButtons[index].forEach { toggleButton ->
            charArray.flip(toggleButton)
        }
        val countWithFlip = toggle(index + 1, String(charArray), toggleButtons, indicatorLights, count + 1)
        min(countWithoutFlip, countWithFlip)
    } else {
        if (tmp == indicatorLights) count else Int.MAX_VALUE
    }
}

private fun CharArray.flip(index: Int) {
    if (this[index] == '.') this[index] = '#'
    else this[index] = '.'
}

private fun solve2(input: List<String>) {
}
