package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

data class FileNode(val name:String, var weight:Int, var children:List<FileNode>) {
  fun calculateWeight() : Int {
    var sum=weight
    for(child in children) {
      sum+=child.weight
    }
    return sum
  }

  override fun toString(): String {
    return buildString {
      append("name: $name weight: ${weight} overallWeight: ${calculateWeight()} ${children.size}\n")
      for (child in children) {
        val childString=child.toString()
        childString.lines().forEach {
          append("  $it\n")
        }
      }
    }
  }
}

class Day7 {
  fun start() {
    val input1= Utils.readInput("day7_1")
    val fileNodes1 = parseTree(input1)
    val result1= findRoot(fileNodes1)
    println("result1 = ${result1}")

    val input2=Utils.readInput("day7_2")
    val fileNodes2 = parseTree(input2)
    val result2=findRoot(fileNodes2)
    println("result2 = ${result2}")

    val result3 = findImbalance(result1)
    println("result3 = ${result3}")

    val result4 = findImbalance(result2)
    println("result4 = ${result4}")
  }

  private fun findImbalance(root: FileNode): Pair<FileNode,Int>? {
    val stack:MutableList<FileNode> = mutableListOf(root)
    var diff=0

    while(stack.isNotEmpty()) {
      var currentNode = stack.removeFirst()
      val weights = currentNode.children.map { it.calculateWeight() }
      val weightMap = mutableMapOf<Int, Int>()
      for (weight in weights) {
        weightMap[weight] = (weightMap[weight] ?: 0) + 1
      }
      var majorWeight = weightMap.maxBy { it.value }.key

      val oddNodes = currentNode.children.filter { it.calculateWeight() != majorWeight }

      println("oddNode = ${oddNodes} (${oddNodes.size}")
      if (oddNodes.size > 1) {
        throw IllegalStateException("more odd found ${oddNodes.size}")
      }
      if(oddNodes.size==0) {
        return Pair(currentNode,diff)
      }

      val oddNode = oddNodes[0]
      diff = - (oddNode.calculateWeight() - majorWeight - oddNode.weight)
      if(oddNode.children.isEmpty()) {
        return Pair(oddNode,diff)
      }
      stack.add(oddNode)
    }
    return null
  }

  private fun findRoot(list:List<FileNode>): FileNode {

    val childNames=list.map { element -> element.children.map { child -> child.name } }.flatten().toSet()

    val filteredList=list.filter { !childNames.contains(it.name) }

    return filteredList[0]
  }

  private fun parseTree(input: List<String>): List<FileNode> {
    val result = mutableMapOf<String,FileNode>()
    val regex = Regex("""(\w+) \((\d+)\)( -> ([\w, ]+))?""")
    for(line in input) {
      val matchResult = regex.matchEntire(line) ?: throw IllegalStateException("no match $line")

      val name = matchResult.groupValues[1]
      val weight = matchResult.groupValues[2].toInt()
      val childrenNames=matchResult.groupValues[4].split ( Regex("""\s*,\s*""") )

      val childNodes= mutableListOf<FileNode>()

      for(childName in childrenNames) {
        if(childName.isBlank()) {
          break
        }
        childNodes += result.computeIfAbsent(childName) {
          FileNode(childName,0, listOf())
        }
      }
      val newFileNode=result.computeIfAbsent(name) {
        FileNode(name,weight,childNodes)
      }
      if(newFileNode.weight==0) {
        newFileNode.weight=weight
        newFileNode.children=childNodes
      }
    }

    return result.values.toList()
  }
}

fun main() {
  Day7().start()
}