package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils
import org.example.org.vorgi.permutations
import kotlin.streams.toList

class Day4 {
  fun start() {
    val input1 = Utils.readInput("day4_1")
    val result1 = countValidPasswords(input1)
    println("result1 = $result1")

    val input2 = Utils.readInput("day4_2")
    val result2 = countValidPasswords(input2)
    println("result2 = $result2")

    val input3 = Utils.readInput("day4_3")
    val result3 = countValidPasswords2(input3)
    println("result3 = $result3")

    val result4 = countValidPasswords2(input2)
    println("result4 = ${result4}")
  }

  private fun countValidPasswords2(input: List<String>): Any {
    var sum = 0
    for (line in input) {
      println("line: $line")
      val split = line.split(" ")
      var valid = true

      outer@
      for (i in split.indices) {
        for (j in i+1..< split.size) {
          if (isAnagramOf(split[i],split[j])) {
            println("invalid: $i $j ${split[i]},${split[j]}")
            valid = false
            break@outer
          }
        }
      }
      if (valid) {
        sum++
      }
    }
    return sum
  }

  private fun isAnagramOf(a:String,b:String): Boolean {
    val aSorted = a.toList().sorted()
    val bSorted = b.toList().sorted()
    return aSorted == bSorted
  }

  private fun countValidPasswords(input: List<String>): Any {
    var sum = 0
    for (line in input) {
      val split = line.split(" ")
      var valid = true
      for (item in split) {
        if (split.count { item == it } != 1) {
          valid = false
          break
        }
      }
      if (valid) {
        println(line)
        sum++
      }
    }
    return sum
  }
}

fun main() {
  Day4().start()
}