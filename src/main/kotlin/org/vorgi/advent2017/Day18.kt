package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils

class SoundMachine(val instructions: List<String>) {
  private val registers = mutableMapOf<String, Int>()
  private var lastSoundPlayed: Int? = null
  var recoveredSound:Int? = null
  private var pc = 0

  fun executeInstruction() {
    val instruction=instructions[pc]
    val parts = instruction.split(" ")
    val registerName = parts[1]
    val valueName = parts[2]
    when (parts[0]) {
      "snd" -> {
        lastSoundPlayed = getValue(registerName)
        pc++
      }
      "set" -> {
        registers[registerName] = getValue(valueName)
        pc++
      }
      "add" -> {
        registers[registerName] = getValue(registerName) + getValue(valueName)
        pc++
      }
      "mul" -> {
        registers[registerName] = getValue(registerName) * getValue(valueName)
        pc++
      }
      "mod" -> {
        registers[registerName] = getValue(registerName) % getValue(valueName)
        pc++
      }
      "rcv" -> {
        if (getValue(registerName)!= 0) {
          println("Recovered sound: ${lastSoundPlayed}")
          recoveredSound=getValue(registerName)
        }
        pc++
      }
      "jgz" -> {
        if (getValue(registerName) > 0) {
          pc=getValue(valueName)
        }
      }
      else -> throw IllegalArgumentException("Invalid instruction: $instruction")
    }
  }

  private fun getValue(registerName: String): Int {
    if(registerName.matches(Regex("""\w"""))) {
      return registers.getOrDefault(registerName, 0)
    } else {
      return registerName.toInt()
    }
  }
}

class Day18 {
  fun start() {
    var input1= Utils.readInput("day18_1")
    var result1=findSound(input1)
    println("result1 = ${result1}")
  }

  private fun findSound(input: List<String>): Int? {
    val soundMachine=SoundMachine(input)
    var recoveredSound: Int? =null
    do {
      soundMachine.executeInstruction()
      recoveredSound = soundMachine.recoveredSound
    } while (recoveredSound==null)
    
    
    return recoveredSound
  }

}

fun main() {
  Day18().start()
}