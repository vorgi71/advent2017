package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day5 {
  fun start() {
    val input1=Utils.readInput("day5_1")
    val result1=jump(input1)
    println("result1 = ${result1}")

    val input2=Utils.readInput("day5_2")
    val result2=jump(input2)
    println("result2 = ${result2}")

    val result3=jump2(input1)
    println("result3 = ${result3}")

    val result4=jump2(input2)
    println("result4 = ${result4}")
  }

  private fun jump2(input: List<String>): Long {
    var steps=0L
    var pc=0
    val instructions = input.map { it.toInt() }.toMutableList()

    while(input.indices.contains(pc)) {
      val instruction=instructions[pc]
      instructions[pc] = if(instruction >= 3) { instruction - 1 } else { instruction + 1 }
      pc+=instruction
      steps++
    }

    return steps
  }

  fun jump(input:List<String>) : Int {
    var steps=0
    var pc=0
    val instructions:MutableList<Int> = input.map { it.toInt() }.toMutableList()

    while(input.indices.contains(pc)) {
      val instruction=instructions[pc]
      instructions[pc]=instruction+1
      pc+=instruction
      steps++
    }

    return steps
  }
}

fun main() {
  Day5().start()
}