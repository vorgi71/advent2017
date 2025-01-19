package org.example.org.vorgi.advent2017

import org.example.org.vorgi.CharGrid
import org.example.org.vorgi.Direction
import org.example.org.vorgi.Point
import org.example.org.vorgi.Utils

class Day19 {
  fun start() {
    val input1 = Utils.readInput("day19_1")
    val result1 = followPath(input1)
    println("result1 = $result1")

    val input2=Utils.readInput("day19_2")
    val result2=followPath(input2)
    println("result2 = $result2")
  }

  private fun followPath(input: List<String>): Pair<String,Int> {
    var output=""
    var steps=0

    val maxX=input.maxBy { line -> line.length }.length
    val paddedInput= input.map { line -> line.padEnd(maxX,' ') }

    val charGrid=CharGrid(paddedInput)
    var foundX=-1
    for(x in 0..<charGrid.width) {
      if(charGrid.getAt(x,0)=='|') {
        foundX=x
        break
      }
    }

    var currentPoint=Point(foundX,0)
    var currentDir=Direction.Down

    while(true) {
      currentPoint+=currentDir
      val c=charGrid.getAt(currentPoint)
      if(c=='?') {
        break
      }
      if (c.isLetter()) {
        output+=c
      }
      if(c=='+') {
        if(currentDir==Direction.Down || currentDir==Direction.Up) {
          var foundDir:Direction?=null
          for(dir in listOf(Direction.Left,Direction.Right)) {
            val nextChar=charGrid.getAt(currentPoint+dir)
            if(nextChar == '-' || nextChar.isLetter()) {
              foundDir=dir
              break
            }
          }
          if(foundDir==null) {
            break
          }
          currentDir=foundDir
        } else {
          var foundDir:Direction?=null
          for(dir in listOf(Direction.Up,Direction.Down)) {
            val nextChar=charGrid.getAt(currentPoint+dir)
            if(nextChar == '|' || nextChar.isLetter()) {
              foundDir=dir
              break
            }
          }
          if(foundDir==null) {
            break
          }
          currentDir=foundDir
        }
      }
      steps++
    }

    var charSum=0
    for(y in 0..<charGrid.height) {
      for(x in 0..<charGrid.width) {
        val c=charGrid.getAt(x,y)
        if(c==' ') {
          continue
        }
        if(c=='-') {
          if(charGrid.getAt(x,y-1)=='|' || charGrid.getAt(x,y+1)=='|') {
            charSum++
          }
        } else if(c=='|') {
          if(charGrid.getAt(x-1,y)=='-' || charGrid.getAt(x+1,y)=='-') {
            charSum++
          }
        }
        charSum++
      }
    }

    println("charSum = ${charSum}")

    return Pair(output,steps)
  }
}

fun main() {
  Day19().start()
}