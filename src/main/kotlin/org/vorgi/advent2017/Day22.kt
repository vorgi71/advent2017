package org.example.org.vorgi.advent2017

import org.example.org.vorgi.CharGrid
import org.example.org.vorgi.Direction
import org.example.org.vorgi.Point
import org.example.org.vorgi.Utils
import kotlin.math.max
import kotlin.math.min

class Virus(val infected:MutableList<Point>) {
  var pos = Point(0,0)
  var dir = Direction.Up

  fun move() {
    if(infected.contains(pos)) {
      dir = dir.turnRight()
      infected.remove(pos)
    } else {
      dir = dir.turnLeft()
      infected.add(pos)
    }
    pos+=dir
  }

  override fun toString(): String {
    val minX= min(infected.minBy { it.x }.x,pos.x)
    val minY=min(infected.minBy { it.y }.y,pos.y)
    val maxX= max(infected.maxBy { it.x }.x,pos.x)
    val maxY=max(infected.maxBy { it.y }.y,pos.y)
    val width=maxX-minX+1
    val height=maxY-minY+1
    val charGrid=CharGrid(width,height,'.')
    for(point in infected) {
      charGrid.setAt(point.x-minX,height-1-(point.y-minY),'#')
    }
    if(infected.contains(pos)) {
      charGrid.setAt(pos.x-minX,height-1-(pos.y-minY),'X')
    } else {
      charGrid.setAt(pos.x-minX,height-1-(pos.y-minY),'O')
    }
    return charGrid.toString()
  }

}

class Day22 {
  fun start() {
    val input1=Utils.readInput("day22_1")
    val result1=infect(input1,41)
    println("result1 = ${result1}")
  }

  private fun infect(input: List<String>, bursts: Int): Any {
    val charGrid=CharGrid(input)
    val infected:MutableList<Point> = mutableListOf()

    val height=charGrid.height
    val width=charGrid.width

    for(y in 0..<height ) {
      for(x in 0..<width) {
        if(charGrid.getAt(x,y)=='#') {
          infected+=Point(x-width/2,height/2-y)
        }
      }
    }

    var virus=Virus(infected)
    println(virus)
    for(i in 0..<bursts) {
      virus.move()
      println(virus)
    }

    return virus.infected.size
  }
}

fun main() {
  Day22().start()
}