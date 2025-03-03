package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day13 {
  fun start() {
    var input1="""0: 3
1: 2
4: 4
6: 4""".lines()
    var result1=calculateSeverity(input1)
    println("result1 = $result1")

    val input2= Utils.readInput("day13")
    val result2=calculateSeverity(input2)
    println("result2 = $result2")

    val result3=findSaveTime(input1)
    println("result3 = $result3")

    val result4=findSaveTime(input2)
    println("result4 = $result4")
  }

  private fun findSaveTime(input: List<String>): Long {
    val firewalls = input.map { line ->
      val split = line.split(": ")
      Pair(split[0].toLong(), split[1].toLong())
    }

    var time=0L

    while(true) {
      if (firewalls.none { (index, height) ->
          (index+time) % (2 * height - 2) == 0L
        }) {
        return time
      }
      time++
    }

  }

  private fun calculateSeverity(input: List<String>): Int {
    val firewalls = input.map { line ->
      val split = line.split(": ")
      Pair(split[0].toInt(), split[1].toInt())
    }

    val collisions = firewalls.filter { (index, height) ->
      index % (2 * height - 2) == 0
    }

    return collisions.sumOf { pair ->
      pair.first*pair.second
    }
  }
}

fun main() {
  Day13().start()
}