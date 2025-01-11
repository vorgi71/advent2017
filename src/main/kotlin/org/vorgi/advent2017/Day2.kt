package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils
import org.example.org.vorgi.combinations

class Day2 {
  fun start() {
    val input1 = Utils.readInput("day2_1")
    val result1 = calcChecksum(input1)
    println("result1 = $result1")

    val input2 = Utils.readInput("day2_2")
    val result2 = calcChecksum(input2)
    println("result2 = $result2")

    val result3 = calcChecksum2(input1)
    println("result3 = $result3")

    val result4 = calcChecksum2(input2)
    println("result4 = $result4")
  }

  private fun calcChecksum(input: List<String>): Any {
    var returnValue = 0L
    for (line in input) {
      val split = line.split(Regex("""\s+""")).map { it.toInt() }
      val max = split.maxBy { value -> value }
      val min = split.minBy { value -> value }

      returnValue += (max - min)
    }

    return returnValue
  }

  private fun calcChecksum2(input: List<String>): Any {
    var returnValue = 0L

    for (line in input) {
      val split = line.split(Regex("""\s+""")).map { it.toInt() }
      val combinations = split.combinations(2)

      val filteredCombinations = combinations.filter { pair ->
        pair[0] > pair[1]
            && pair[0] % pair[1] == 0
      }
      if (filteredCombinations.size != 1) {
        throw IllegalStateException("Found more than one combination $filteredCombinations")
      }
      returnValue += filteredCombinations[0][0] / filteredCombinations[0][1]
    }

    return returnValue
  }
}

fun main() {
  Day2().start()
}