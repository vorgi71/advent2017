package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Point
import org.example.org.vorgi.Utils
import java.util.*
import kotlin.math.abs

enum class HexDirections(val dir: Point) {
  N(Point(0,-2)),
  NE(Point(1,-1)),
  SE(Point(1,1)),
  S(Point(0,2)),
  SW(Point(-1,1)),
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
    val input1="se,sw,se,sw,sw"
    val result1=processDirections(input1)
    println("result1 = ${result1}")

    val input2= Utils.readInputAsText("day11_1")
    val result2=processDirections(input2)
    println("result2 = ${result2}")

    var resultPoint=Point(abs(result2.x), abs(result2.y))
    var yDist=(resultPoint.y-resultPoint.x)/2
    var diagDist=resultPoint.x
    println("yDist = ${yDist} diagDist $diagDist = ${yDist+diagDist}")
  }

  private fun processDirections(input1: String): Point {
    val hexPathToPoint = hexPathToPoint(input1.split(",").map {
      HexDirections.valueOf(it.toUpperCase(Locale.US))
    })
    return hexPathToPoint
  }
}

fun main() {
  Day11().start()
}