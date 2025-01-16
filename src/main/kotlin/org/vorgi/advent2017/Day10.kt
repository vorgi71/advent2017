package org.example.org.vorgi.advent2017

class Day10 {
  fun start() {
    val result1 = createHash(5, listOf(3, 4, 1, 5))
    println("result1 = ${result1}")

    val result2 = createHash(256, listOf(34, 88, 2, 222, 254, 93, 150, 0, 199, 255, 39, 32, 137, 136, 1, 167))
    println("result2 = ${result2}: ${result2.first[0] * result2.first[1]}")

    check(result2.first[0] * result2.first[1] == 54675)

    var result3 = createHash2(256 ,"1,2,3")
    println("result3 = ${result3}")

  }

  private fun stringToAsciiList(input: String): List<Int> {
    val output = input.map { it.code }

    return output
  }

  private fun createHash2(size:Int,input: String): String {
    var currentString = input
    var currentIndexAndSkipSize = Pair(0, 0)
    var currentHash=MutableList(size) { it }

    for (index in 1..64) {
      var currentList = stringToAsciiList(currentString)
      println("cycle: $index $currentIndexAndSkipSize")
      currentList = currentList + listOf(17, 31, 73, 47, 23)
      val nextHashAndIS = createHash(currentHash, currentList, currentIndexAndSkipSize)
      currentHash = nextHashAndIS.first.toMutableList()
      currentIndexAndSkipSize = nextHashAndIS.second
      currentString = currentList.joinToString(", ")
    }


    val resultArray=MutableList<Int>(16) { 0 }

    for(i in 0..<16) {
      var digit=0
      for(j in 0..<16) {
        digit=digit.xor(currentHash[i*16+j])
      }
      resultArray[i]=digit
    }

    return resultArray.joinToString("") { it.toString(16).padStart(2, '0') }
  }



  private fun createHash(
    size: Int,
    instructions: List<Int>,
    indexAndSkipSize: Pair<Int, Int> = Pair(0, 0)
  ): Pair<List<Int>, Pair<Int, Int>> {
    val inputHash = MutableList(size) { index ->
      index
    }
    return createHash(inputHash,instructions,indexAndSkipSize)
  }

  private fun createHash(
    inputHash: List<Int>,
    instructions: List<Int>,
    indexAndSkipSize: Pair<Int, Int> = Pair(0, 0)
  ): Pair<List<Int>, Pair<Int, Int>> {

    val string = inputHash.toMutableList()

    // println(string)

    var index = indexAndSkipSize.first
    var skipSize = indexAndSkipSize.second

    for (instruction in instructions) {
      for (i in 0..<instruction / 2) {
        string[(i + index) % string.size] = string[(index + instruction - 1 - i) % string.size].also {
          string[(index + instruction - 1 - i) % string.size] = string[(i + index) % string.size]
        }
      }
      index = (index + skipSize + instruction) % string.size
      // println(index)

      skipSize=(skipSize+1)%string.size
      // println(string)
    }


    return Pair(string, Pair(index, skipSize))
  }
}

fun main() {
  Day10().start()
}