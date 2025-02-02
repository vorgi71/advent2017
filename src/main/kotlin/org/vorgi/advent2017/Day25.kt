package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class Day25 {
  fun start() {
    val input1 = Utils.readInput("day25_1")
    val result1 = runTouring(input1)
    println("result1 = $result1")
    val input2 = Utils.readInput("day25_2")
    val result2 = runTouring(input2)
    println("result2 = $result2")
  }

  data class Transition(
    val writeValue: Int,
    val move: Int,
    val nextState: String
  ) {
    constructor(writeValue: Int, move: String, nextState: String) : this(
      writeValue, if (move == "right") {
        1
      } else {
        -1
      }, nextState
    )
  }

  data class State(
    val name: String,
    val zeroTransition: Transition,
    val oneTransition: Transition
  )

  class StateMachine(
    val startState: String,
    val iterations: Int,
  ) {
    val states = mutableMapOf<String, State>()
    var state:State?=null
    val tape = mutableListOf(0)
    var tapePosition = 0

    fun addState(state: State) {
      states[state.name] = state
    }

    override fun toString(): String {
      val positionString = " ".repeat(tapePosition) + "v"
      val tapeString = tape.map { if (it == 0) "0" else "1" }.joinToString("")
      return "$positionString\n$tapeString"
    }

    fun executeStep() {
      if(state==null) {
        state=states[startState]
      }
      val value = tape[tapePosition]
      val transition =
        (if (value == 0) state?.zeroTransition else state?.oneTransition) ?:
        throw IllegalStateException("failed $state $ value $value")
      state = states[transition.nextState]
      tape[tapePosition] = transition.writeValue
      if (transition.move == 1) {
        tapePosition++
        if(tapePosition>=tape.size) {
          tape.add(tapePosition,0)
        }
      } else {
        tapePosition--
        if (tapePosition < 0) {
          tape.add(0, 0)
          tapePosition++
        }
      }
    }
  }

  private fun runTouring(input: List<String>): Int {
    val stateMachine = parseTouring(input)

    println(stateMachine)

    for (i in 0..<stateMachine.iterations) {
      stateMachine.executeStep()
      // println(stateMachine)
    }

    return stateMachine.tape.sum()
  }

  private fun parseTouring(input: List<String>): StateMachine {
    val lines = input.toMutableList()
    val line1 = lines.removeFirst()
    val startState = Regex("""Begin in state (A|B)\.""").find(line1)?.groupValues?.get(1) ?: "A"
    val line2 = lines.removeFirst()
    val iterations =
      Regex("""Perform a diagnostic checksum after (\d*) steps.""").find(line2)?.groupValues?.get(1) ?: "1"

    val stateMachine: StateMachine = StateMachine(startState, iterations.toInt())

    while (lines.isNotEmpty()) {
      lines.removeFirst()
      val stateLine = lines.removeFirst()
      val state = Regex("""In state (\w*):""").find(stateLine)?.groupValues?.get(1) ?: "?"
      lines.removeFirst()
      val zeroWriteValue =
        Regex("""    - Write the value (\d*).""").find(lines.removeFirst())?.groupValues?.get(1)?.toInt() ?: -1
      val zeroMove =
        Regex("""    - Move one slot to the (\w*).""").find(lines.removeFirst())?.groupValues?.get(1) ?: "down"
      val zeroState =
        Regex("""    - Continue with state (\w*).""").find(lines.removeFirst())?.groupValues?.get(1) ?: "?"
      lines.removeFirst()
      val oneWriteValue =
        Regex("""    - Write the value (\d*).""").find(lines.removeFirst())?.groupValues?.get(1)?.toInt() ?: -1
      val oneMove =
        Regex("""    - Move one slot to the (\w*).""").find(lines.removeFirst())?.groupValues?.get(1) ?: "down"
      val oneState = Regex("""    - Continue with state (\w*).""").find(lines.removeFirst())?.groupValues?.get(1) ?: "?"

      stateMachine.addState(
        State(
          state, Transition(zeroWriteValue, zeroMove, zeroState),
          Transition(oneWriteValue, oneMove, oneState)
        )
      )

    }

    return stateMachine
  }
}

fun main() {
  Day25().start()
}