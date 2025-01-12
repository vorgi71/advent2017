package org.example.org.vorgi.advent2017

class Day6 {
  fun start() {
    val input1="0  2  7     0"

    val result1= countLoop(input1)
    println("result1 = ${result1}")

    val input2="10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6"
    val result2= countLoop(input2)
    println("result2 = ${result2}")
  }

  private fun countLoop(input: String): Pair<Long, Long> {
    var steps=1L
    var banks= input.split(Regex("""\s+""")).map { it.toInt() }
    val history= mutableListOf(banks)
    var indexOfDouble=-1

    println("banks: $banks")
    do {
      val newBanks=banks.toMutableList()

      val max = banks.max()
      var maxIndex=banks.indexOf(max)

      newBanks[maxIndex] -= max

      maxIndex++

      val maxDiv=max / banks.size
      val maxMod=max % banks.size

      for(i in 0..<banks.size) {
        if(i<maxMod) {
          newBanks[(i + maxIndex) % banks.size] += maxDiv+1
        } else {
          newBanks[(i + maxIndex) % banks.size] += maxDiv
        }
      }

      if(history.contains(newBanks)) {
        indexOfDouble=history.indexOf(newBanks)
        break
      }
      history+=newBanks
      banks=newBanks
      println("banks: $banks")
      steps++
    } while(true)


    return Pair(steps,steps-indexOfDouble)
  }
}

fun main() {
  Day6().start()
}