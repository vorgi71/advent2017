package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils
import kotlin.time.measureTime

class Day16 {
  fun start() {
    val input1 = "s1,x3/4,pe/b"
    val result1 = dance("abcde", input1)
    println("result1 = ${result1}")

    val result2 = dance("abcdefghijklmnop", Utils.readInputAsText("day16"))
    println("result2 = ${result2}")
    check(result2=="jcobhadfnmpkglie")

    val elapsed=measureTime {
      val result3 = dance("abcdefghijklmnop", Utils.readInputAsText("day16"), 10_000)
      println("result3 = ${result3}")
    }
    println("elapsed = ${elapsed}")
  }

  private fun dance(programs: String, input: String, iterations: Int = 1): String {
    val programList = programs.toMutableList()
    val instructions = input.split(",")

    val results= mutableListOf<String>()


    var iteration=0
    while(iteration < iterations) {
      for (instruction in instructions) {
        val opcode = instruction.first()
        when (opcode) {
          's' -> {
            val size = instruction.substring(1).toInt()
            for (i in 0..<size) {
              programList.addFirst(programList.removeLast())
            }
          }

          'x' -> {
            val split = instruction.substring(1).split("/").map { it.toInt() }
            programList[split[0]] = programList[split[1]].also {
              programList[split[1]] = programList[split[0]]
            }
          }

          'p' -> {
            val split = instruction.substring(1).split("/")
            val indexOf0 = programList.indexOf(split[0].first())
            val indexOf1 = programList.indexOf(split[1].first())
            programList[indexOf0] = split[1].first()
            programList[indexOf1] = split[0].first()
          }
        }
      }
      val element = programList.joinToString("")
      if (results.contains(element)) {
        iteration=iterations-(iterations%iteration)
      } else {
        results.add(element)
      }
      iteration++
    }


    return programList.joinToString("")
  }
}

fun main() {
  Day16().start()
}