package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day1 {
  fun start() {
    println("user.dir: ${System.getProperty("user.dir")}")
    val input1 = Utils.readInput("day1_1")
    val result1 = processCaptcha(input1)
    println("result1 = $result1")

    val input2 = Utils.readInput("day1_2")
    val result2 = processCaptcha(input2)
    println("result2 = $result2")

    val wrapFunction = { line: String, index: Int ->
      val wrappedIndex = (index + (line.length / 2)) % line.length
      line[wrappedIndex]
    }

    val result3 = processCaptcha(input1,wrapFunction)
    println("result3 = $result3")

    val result4 = processCaptcha(input2,wrapFunction)
    println("result4 = $result4")
  }

  private fun processCaptcha(
    input: List<String>,
    prevCharFunction: ((line: String, index: Int) -> Char) = { line, index ->
      if (index == 0) {
        line[line.length - 1]
      } else {
        line[index - 1]
      }
    }
  ): MutableList<Int> {

    val result: MutableList<Int> = mutableListOf()

    for (line in input) {
      var index = 0
      var sum = 0
      while (index < line.length) {
        val previousChar = prevCharFunction(line, index)
        val currentChar = line[index]
        if (previousChar == currentChar) {
          val charValue = "$currentChar".toInt()
          sum += charValue
        }

        index++
      }
      result += sum
    }

    return result
  }
}

fun main() {
  Day1().start()
}