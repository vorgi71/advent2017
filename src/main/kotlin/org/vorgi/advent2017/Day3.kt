package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Direction
import org.example.org.vorgi.Direction.*
import org.example.org.vorgi.Point
import kotlin.math.abs

class Day3 {
  fun start() {
    val pointList=createSpiral(277678)
    println("endPos = $pointList ${abs(pointList.last().x)+abs(pointList.last().y)}")
    val result2=calculateGeometricFibonacci(pointList,277678)
  }

  private fun calculateGeometricFibonacci( points: List<Point>, maxValue:Int) : Int {
    var value=1
    var index=0
    var pos=points[index]

  }

  private fun createSpiral(endIndex: Int) : List<Point> {
    var pos = Point(0, 0)
    var index = 1
    var direction: Direction = Right
    var steps = 1

    val posList= mutableListOf(pos)


    while (index < endIndex) {
      for (step in 1..steps) {
        pos += direction
        posList += pos

        index++
        if(index>= endIndex) {
          break
        }
      }
      when (direction) {
        Right -> {
          direction = Up
        }
        Up -> {
          direction = Left
          steps+=1
        }

        Left ->  {
          direction = Down
        }
        Down ->  {
          direction = Right
          steps+=1
        }
      }
    }

    return posList
  }

  private fun printPosList(posList: MutableList<Point>) {
    var minX=posList.minBy { it.x }.x
    var minY=posList.minBy { it.y }.y
    var maxX=posList.maxBy { it.x }.x
    var maxY=posList.maxBy { it.y }.y

    for(y in minY..maxY) {
      for(x in minX..maxX) {
        if(posList.contains(Point(x,y))) {
          print("*")
        } else {
          print(".")
        }
      }
      println()
    }
  }
}

fun main() {
  Day3().start()
}