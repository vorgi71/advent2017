package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day24 {

  fun start() {
    val input1 = Utils.readInput("day24_1")
    val result1 = strongestPath(input1)
    println("result1 = ${result1}")

    val input2 = Utils.readInput("day24_2")
    val result2 = strongestPath(input2)
    println("result2 = $result2")

    val result3 = strongestPath(input1,::lengthOrPartValue)
    println("result3 = ${result3}")

    val result4 = strongestPath(input2,::lengthOrPartValue)
    println("result4 = ${result4}")
  }

  private fun strongestPath(input: List<String>, maxFunction:(List<Pair<Int, Int>>) -> Int = ::partValue): Int {
    val availableParts = input.map {
      val split = it.split("/").map { it.toInt() }
      Pair(split[0], split[1])
    }
    val foundCombinations = mutableListOf<List<Pair<Int, Int>>>()
    val stack = mutableListOf(Pair(0, listOf<Pair<Int, Int>>()))
    while (stack.isNotEmpty()) {
      val (partValue, visited) = stack.removeFirst()
      val nextParts = availableParts.filter {
        (it.first == partValue || it.second == partValue) && !visited.contains(it)
      }
      for (nextPart in nextParts) {
        var nextPartValue = -1
        if (nextPart.first == partValue) {
          nextPartValue = nextPart.second
        } else {
          nextPartValue = nextPart.first
        }
        val nextVisited = visited.toMutableList()
        nextVisited.add(nextPart)
        stack.add(Pair(nextPartValue, nextVisited))
      }
      if (nextParts.isEmpty()) {
        foundCombinations.add(visited)
      }
    }

    val maxCombo=  foundCombinations.maxBy {
      maxFunction(it)
    }

    return partValue(maxCombo)
  }

  private fun partValue(it: List<Pair<Int, Int>>) =
    it.sumOf { part -> part.first + part.second }

  private fun lengthOrPartValue(part: List<Pair<Int, Int>>) =
    part.size*100_000+partValue(part)


}

fun main() {
  Day24().start()
}