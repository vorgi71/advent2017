package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day12 {
  fun start() {
    val input1 = """0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5""".lines()

    val result1=checkGroups(input1,0)
    println("result1 = ${result1.size}")

    val input2= Utils.readInput("day12")
    val result2=checkGroups(input2,0)
    println("result2 = ${result2.size}")

    val result3 = countGroups(input1)
    println("result3 = ${result3}")

    val result4 = countGroups(input2)
    println("result4 = ${result4.size}")
  }

  private fun countGroups(input: List<String>): MutableMap<Int, Set<Int>> {
    val groups=parseGroups(input)

    val remainingKeys=groups.keys.toMutableSet()
    val theGroups=mutableMapOf<Int,Set<Int>>()

    while(remainingKeys.isNotEmpty()) {
      val key=remainingKeys.first()
      remainingKeys.remove(key)
      val checkGroups = checkGroups(groups, key)
      theGroups[key] = checkGroups
      remainingKeys.removeAll(checkGroups)
    }
    return theGroups
  }

  private fun checkGroups(input: List<String>, i: Int): Set<Int> {
    val groups = parseGroups(input)
    return checkGroups(groups,i)
  }

  private fun checkGroups(groups: Map<Int,List<Int>>, i:Int) : Set<Int> {
    val stack= mutableListOf(i)

    val visitedKeys= mutableSetOf<Int>()

    while(stack.isNotEmpty()) {
      val key = stack.removeFirst()
      if (!visitedKeys.contains(key)) {
        visitedKeys.add(key)
      }
      groups[key]?.forEach { value ->
        if (!stack.contains(value) && !visitedKeys.contains(value)) {
          stack.add(value)
        }
      }
    }

    return visitedKeys
  }


  private fun parseGroups(input: List<String>): Map<Int,List<Int>> {
    val programMap=input.associate { line ->
      val split = line.split(" <-> ")
      val key = split[0].toInt()
      val values = split[1].split(", ").map { it.toInt() }
      Pair(key, values)
    }

    return programMap
  }
}

fun main() {
  Day12().start()
}