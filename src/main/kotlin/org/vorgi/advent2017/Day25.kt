package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day25 {
  fun start() {
    val input1 = Utils.readInput("day25_1")
    val result1 = runTouring(input1)
  }

  private fun runTouring(input: List<String>): Int {
    val lines = input.toMutableList()
    val line1 = lines.removeFirst()
    val startState = Regex("""Begin in state (A|B)\.""").find(line1)?.groupValues?.get(1)?:"A"
    val line2 = lines.removeFirst()
    val iterations = Regex("""Perform a diagnostic checksum after (\d*) steps.""").find(line2)?.groupValues?.get(1)?:"1"

    return 0
  }
}

fun main() {
  Day25().start()
}