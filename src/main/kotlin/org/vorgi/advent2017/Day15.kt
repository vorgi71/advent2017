package org.example.org.vorgi.advent2017

class Day15 {
  fun start() {
    val result1=countMatches(16807L,48271L,65L,8921L,5)
    println("result1 = ${result1}")
    val result2=countMatches(16807L,48271L,65L,8921L,40_000_000)
    println("result2 = ${result2}")
    val result3=countMatches(16807L,48271L,883L,879L,40_000_000)
    println("result3 = ${result3}")

    val result4=countMatches2(16807L,48271L,65L,8921L,1056)
    println("result4 = ${result4}")
    val result5=countMatches2(16807L,48271L,883L,879L,5_000_000)
    println("result5 = ${result5}")
  }

  private fun countMatches2(aMul: Long, bMul: Long, aOffset: Long, bOffset: Long, iterations: Int): Int {
    var aValue=aOffset
    var bValue=bOffset

    var sum=0

    for(i in 0..<iterations) {
      do {
        aValue *= aMul
        aValue %= 2147483647L
      } while(aValue%4!=0L)
      do {
        bValue *= bMul
        bValue %= 2147483647L
      } while(bValue%8!=0L)

      if(aValue and 0xFFFF == bValue and 0xFFFF) {
        sum++
      }

    }

    return sum
  }

  private fun countMatches(aMul: Long, bMul: Long, aOffset: Long, bOffset: Long, iterations: Int): Any {
    var aValue=aOffset
    var bValue=bOffset

    var sum=0
    
    for(i in 0..<iterations) {
      aValue*=aMul
      aValue%=2147483647L
      bValue*=bMul
      bValue%=2147483647L
      if(aValue and 0xFFFF == bValue and 0xFFFF) {
        sum++
      }
    }

    return sum
  }
}

fun main() {
  Day15().start()
}