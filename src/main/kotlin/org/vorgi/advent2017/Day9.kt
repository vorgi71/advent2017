package org.example.org.vorgi.advent2017

class Day9 {
  fun start() {
    var input1 = "{{<!!>},{<!!>},{<!!>},{<!!>}}"
    val result1 = countGroups(input1)
    println("result1 = ${result1}")
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

    while (index < charList.size) {
      val c = charList[index]

      when (state) {
        States.NORMAL -> {
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

        }
      }

      if(state!=States.IN_CANCEL && c=='!') {
        state=States.IN_CANCEL
      }

      index++
    }
    return sum

  }

}


fun main() {
  Day9().start()

}