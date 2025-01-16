package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day9 {
  fun start() {
    var input1 = "{{{},{},{{}}}}"
    val result1 = countGroups(input1)
    println("result1 = ${result1}")

    val input2 = Utils.readInputAsText("day9_2")
    val result2 = countGroups(input2)
    println("result2 = ${result2}")

    val input3 = "<{o\"i!a,<{i<a>"
    val result3 = countGarbage(input3)
    println("result3 = ${result3}")

    val result4 = countGarbage(input2)
    println("result4 = ${result4}")
  }

  private fun countGarbage(input: String): Int {
    var sum=0

    val charList=input.toList()

    var index=0
    var state=States.NORMAL

    while(index<charList.size) {
      val c=charList[index]
      if(c=='!') {
        index+=2
        continue
      }

      if(state==States.NORMAL) {
        if(c=='<') {
          state=States.IN_GARBAGE
        }
      } else if(state==States.IN_GARBAGE) {
        if(c=='>') {
          state=States.NORMAL
        } else {
          print(c)
          sum++
        }
      }

      index++
    }
    println()

    return sum
  }

  enum class States {
    NORMAL, IN_GARBAGE, IN_CANCEL
  }

  private fun countGroups(input: String): Int {
    val charList = input.toList()

    var index = 0
    var sum = 0
    var currentLevel = 1
    var state = States.NORMAL
    var previousState = States.NORMAL

    while (index < charList.size) {
      var c = charList[index]

      when (state) {
        States.NORMAL -> {
          print(c)
          when (c) {
            '{' -> {
              currentLevel++
            }

            '}' -> {
              currentLevel--
              sum += currentLevel
            }

            '<' -> state = States.IN_GARBAGE
            else -> {

            }
          }
        }

        States.IN_GARBAGE -> {
          if (c == '>') {
            state = States.NORMAL
          }
        }

        States.IN_CANCEL -> {
          state=previousState
          c='.'
        }
      }

      if(state!=States.IN_CANCEL && c=='!') {
        previousState=state
        state=States.IN_CANCEL
      }

      index++
    }

    println()
    return sum

  }

}


fun main() {
  Day9().start()

}