package org.example.org.vorgi.advent2017

import org.example.org.vorgi.CharGrid
import org.example.org.vorgi.Direction
import org.example.org.vorgi.Point
import kotlin.experimental.and

class Day14 {

  @OptIn(ExperimentalStdlibApi::class)
  fun start() {
    val test1=knotHash("")
    println("test1 = ${test1}")

    val input1="flqrgnkx"
    val result1=createGrid(input1).toString().count { c -> c=='#' }
    println("$result1")

    val input2="ugkiagan"
    val result2=createGrid(input2).toString().count {c -> c=='#'}
    println("result2 = ${result2}")

    val result3=countRegions(input1)
    println("result3 = ${result3}")

    val result4=countRegions(input2)
    println("result4 = ${result4}")
  }

  private fun countRegions(input: String): Int {
    val grid=createGrid(input)

    var sum=0

    for(y in 0..<grid.height) {
      for(x in 0..<grid.width) {
        if(checkRegion(grid,x,y)) {
          sum++
        }
      }
    }

    return sum
  }

  private fun checkRegion(grid: CharGrid, x: Int, y: Int): Boolean {
    if(grid.getAt(x,y)!='#') {
      return false
    }

    val stack= mutableListOf(Point(x,y))

    while(stack.isNotEmpty()) {
      val point = stack.removeFirst()
      grid.setAt(point,'.')
      for(direction in Direction.entries) {
        val newPoint=point+direction
        if(grid.getAt(newPoint)=='#') {
          stack.add(newPoint)
        }
      }
    }

    return true
  }

  private fun createGrid(input: String): CharGrid {
    val charGrid:CharGrid=CharGrid(128,128,'.')
    for(y in 0..<128) {
      val lineHash=knotHash("$input-$y")
      var x=0
      for(byte in lineHash) {
        var bVal=128
        for(byteIndex in 0..<8) {
          if(byte.and(bVal) ==bVal) {
            charGrid.setAt(x,y,'#')
          }
          x++
          bVal /= 2
        }
      }
    }

    return charGrid
  }

  fun knotHash(input:String): Array<Int> {
    val lengths = input.toByteArray().map {it.toInt()}.toMutableList()
    lengths.addAll(listOf(17, 31, 73, 47, 23))
    val chunks = IntArray(256) {it}
      .knot(lengths, 64)
      .asIterable()
      .chunked(16)
    val output = Array(16) {0}
    var index=0
    chunks.forEach {chunk ->
      var xored = 0
      chunk.forEach {number ->
        xored = xored xor number
      }
      output[index++]=xored
    }
    return output
  }

  private fun IntArray.knot(lengths: List<Int>, rounds: Int): IntArray {
    val list = this.copyOf()
    var currentPosition = 0
    var skipSize = 0
    var tmp: Int
    repeat (rounds) {
      for (number in lengths) {
        if (number > list.size) continue
        for (curr in 0 until number/2) {
          tmp = list[(currentPosition + curr) % list.size]
          list[(currentPosition + curr) % list.size] = list[(currentPosition + number - 1 - curr) % list.size]
          list[(currentPosition + number - 1 - curr) % list.size] = tmp
        }
        currentPosition = (currentPosition + number + skipSize++) % list.size
      }
    }
    return list
  }
}

fun main() {
  Day14().start()
}