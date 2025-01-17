package org.example.org.vorgi.advent2017

class Day17 {
  fun spinlock(iterations:Int,moves:Int): MutableList<Int> {
    val buffer= mutableListOf(0)
    var index=0

    for(iteration in 1..iterations) {
      if(iteration%100_000==0) {
        println("iteration: $iteration")
      }
      index+=moves
      index%=buffer.size
      buffer.add(index+1,iteration)
      index+=1
      index%=buffer.size
    }

    return buffer
  }

  fun start() {
    val result1=spinlock(2017,3)
    println("result1 = ${result1[result1.indexOf(2017)+1]}")
    val result2=spinlock(2017,380)
    println("result2 = ${result2[result2.indexOf(2017)+1]}")

    val result3=spinlock(50_000_000,380)
    println("result3: ${result3[result3.indexOf(0)]}")
  }
}

fun main() {
  Day17().start()
}