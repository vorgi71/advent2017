package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Point
import org.example.org.vorgi.Point3D
import org.example.org.vorgi.Utils
import org.example.org.vorgi.plus
import java.util.*
import kotlin.math.abs

enum class HexDirections(val dir: Point3D) {
  N(Point3D(-1, 0, 1)),
  NE(Point3D(0, 1, 1)),
  SE(Point3D(1, 1, 0)),
  S(Point3D(1, 0, -1)),
  SW(Point3D(0, -1, -1)),
  NW(Point3D(-1, -1, 0));
}

fun hexPathToPoint(hexList: List<HexDirections>): Point3D {
  var point = Point3D(0, 0,0)
  for (hex in hexList) {
    point += hex.dir
  }
  return point
}

class Day11 {
  fun start() {
    val input1 = "se,sw,se,sw,sw"
    val result1 = processDirections(input1)
    println("result1 = ${result1}")

    val input2 = Utils.readInputAsText("day11_1")
    val result2 = processDirections(input2)
    println("result2 = ${result2}")

    val result3 = maxDirection("ne,ne,sw,sw")
    println("result3 = ${result3}")

    val result4 = maxDirection(input2)
    println("result4 = ${result4}")

  }

  private fun maxDirection(input: String): Long {
    val hexPath=input.split(",").map {
      HexDirections.valueOf(it.uppercase(Locale.US))
    }

    var maxDist=-1L

    var point = Point3D(0, 0,0)
    for (hex in hexPath) {
      point += hex.dir
      val dist=(abs(point.x) + abs(point.y) + abs(point.z) )/ 2
      if(dist > maxDist) {
        maxDist=dist
      }
    }


    return maxDist
  }

  private fun processDirections(input1: String): Long {
    val hexPathToPoint = hexPathToPoint(input1.split(",").map {
      HexDirections.valueOf(it.uppercase(Locale.US))
    })

    val dist=(abs(hexPathToPoint.x) + abs(hexPathToPoint.y) + abs(hexPathToPoint.z) )/ 2

    return dist
  }
}

fun main() {
  Day11().start()
}