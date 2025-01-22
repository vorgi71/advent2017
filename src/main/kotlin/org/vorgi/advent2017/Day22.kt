package org.example.org.vorgi.advent2017

import org.example.org.vorgi.CharGrid
import org.example.org.vorgi.Direction
import org.example.org.vorgi.Point
import org.example.org.vorgi.Utils
import kotlin.math.max
import kotlin.math.min

open class Virus(val infected:MutableList<Point>) {
  var pos = Point(0,0)
  var dir = Direction.Up
  var infections=0

  open fun move() {
    if(infected.contains(pos)) {
      dir = dir.turnRight()
      infected.remove(pos)
    } else {
      dir = dir.turnLeft()
      infected.add(pos)
      infections++
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
      charGrid.setAt(point.x-minX,(point.y-minY),'#')
    }
    if(infected.contains(pos)) {
      val virusChar=when(dir) { Direction.Up -> '▲'; Direction.Right -> '►';Direction.Left->'◄';Direction.Down -> '▼'}
      charGrid.setAt(pos.x-minX, pos.y-minY,virusChar)
    } else {
      val virusChar=when(dir) { Direction.Up -> '↑'; Direction.Right -> '→';Direction.Left->'←';Direction.Down -> '↓'}
      charGrid.setAt(pos.x-minX, pos.y-minY,virusChar)
    }
    return charGrid.toString()
  }
}

class AdvancedVirus(
  infected: MutableList<Point>
) : Virus(infected) {
  var weakened= mutableListOf<Point>()
  var flagged= mutableListOf<Point>()

  override fun move() {
    if(weakened.contains(pos)) {
      weakened.remove(pos)
      infected.add(pos)
      infections++
    } else if(infected.contains(pos)) {
      dir = dir.turnRight()
      infected.remove(pos)
      flagged.add(pos)
    } else if(flagged.contains(pos)) {
      dir = dir.turnRight().turnRight()
      flagged.remove(pos)
    } else {
      dir = dir.turnLeft()
      weakened.add(pos)
    }
    pos+=dir
  }

  override fun toString(): String {
    var minX= min(infected.minBy { it.x }.x,pos.x)
    var minY= min(infected.minBy { it.y }.y,pos.y)
    var maxX= max(infected.maxBy { it.x }.x,pos.x)
    var maxY=max(infected.maxBy { it.y }.y,pos.y)
    if(weakened.isNotEmpty()) {
      minX = min(minX, weakened.minBy { it.x }.x)
      maxX = max(maxX, weakened.maxBy { it.x }.x)
      minY = min(minY, weakened.minBy { it.y }.y)
      maxY = max(maxY, weakened.maxBy { it.y }.y)
    }
    if(flagged.isNotEmpty()) {
      minX = min(minX, flagged.minBy { it.x }.x)
      maxX = max(maxX, flagged.maxBy { it.x }.x)
      minY = min(minY, flagged.minBy { it.y }.y)
      maxY = max(maxY, flagged.maxBy { it.y }.y)
    }

    val width=maxX-minX+1
    val height=maxY-minY+1
    val charGrid=CharGrid(width,height,'.')
    for(point in infected) {
      charGrid.setAt(point.x-minX,(point.y-minY),'#')
    }
    for(point in weakened) {
      charGrid.setAt(point.x-minX,(point.y-minY),'w')
    }
    for(point in flagged) {
      charGrid.setAt(point.x-minX,(point.y-minY),'f')
    }

    if(infected.contains(pos)) {
      val virusChar=when(dir) { Direction.Up -> '▲'; Direction.Right -> '►';Direction.Left->'◄';Direction.Down -> '▼'}
      charGrid.setAt(pos.x-minX, pos.y-minY,virusChar)
    } else {
      val virusChar=when(dir) { Direction.Up -> '↑'; Direction.Right -> '→';Direction.Left->'←';Direction.Down -> '↓'}
      charGrid.setAt(pos.x-minX, pos.y-minY,virusChar)
    }
    return charGrid.toString()
  }
}

class Day22 {
  fun start() {
    val input1=Utils.readInput("day22_1")
    val result1=infect(input1,10000)
    println("result1 = ${result1}")

    val input2=Utils.readInput("day22_2")
    val result2=infect(input2,10000)
    println("result2 = ${result2}")

    val result3=infect2(input2,10_000_000)
    println("result3 = ${result3}")
  }

  private fun infect2(input: List<String>, bursts: Int): Any {
    val charGrid=CharGrid(input)
    val infected:MutableList<Point> = mutableListOf()

    val height=charGrid.height
    val width=charGrid.width

    for(y in 0..<height ) {
      for(x in 0..<width) {
        if(charGrid.getAt(x,y)=='#') {
          infected+=Point(x-width/2,y-height/2)
        }
      }
    }

    var virus=AdvancedVirus(infected)
    //println(virus)
    for(i in 0..<bursts) {
      if(i%100_000==0) {
        println("bursts: $i")
      }
      virus.move()
      //println(virus)
    }

    return virus.infections
  }

  private fun infect(input: List<String>, bursts: Int): Any {
    val charGrid=CharGrid(input)
    val infected:MutableList<Point> = mutableListOf()

    val height=charGrid.height
    val width=charGrid.width

    for(y in 0..<height ) {
      for(x in 0..<width) {
        if(charGrid.getAt(x,y)=='#') {
          infected+=Point(x-width/2,y-height/2)
        }
      }
    }

    var virus=Virus(infected)
    for(i in 0..<bursts) {
      virus.move()
    }

    return virus.infections
  }
}

fun main() {
  Day22().start()
}