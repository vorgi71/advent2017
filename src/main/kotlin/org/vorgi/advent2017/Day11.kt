package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Point
import java.util.*
import kotlin.coroutines.coroutineContext

enum class HexDirections(val dir: Point) {
  NE(Point(1,-1)),
  E(Point(1,0)),
  SE(Point(1,1)),
  SW(Point(-1,1)),
  W(Point(-1,0)),
  NW(Point(-1,-1));
}

fun hexPathToPoint(hexList:List<HexDirections>) : Point {
  var point = Point(0, 0)
  for(hex in hexList) {
    point+=hex.dir
  }
  return point
}

class Day11 {
  fun start() {
    val input1="ne,ne,ne"
    val result1=processDirections(input1)
    println("result1 = ${result1}")
  }

  private fun processDirections(input1: String): Any {
    val hexPathToPoint = hexPathToPoint(input1.split(",").map {
      HexDirections.valueOf(it.toUpperCase(Locale.US))
    })
    return hexPathToPoint
  }
}

fun main() {
  Day11().start()
}