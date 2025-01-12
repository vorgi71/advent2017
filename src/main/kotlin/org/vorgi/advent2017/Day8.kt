package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils
import java.lang.IllegalArgumentException

data class Instruction(
  val register: String,
  val operation: String,
  val value: Int,
  val conditionRegister: String,
  val conditionOperator: String,
  val conditionValue: Int

) {
  companion object {
    fun parseInstruction(line: String): Instruction {
      val regex = Regex("""^(\w+) (inc|dec) (-?\d+) if (\w+) (==|!=|>|<|>=|<=) (-?\d+)$""")
      val matcher = regex.matchEntire(line) ?: throw IllegalArgumentException("syntax error $line")

      return Instruction(
        matcher.groupValues[1],
        matcher.groupValues[2],
        matcher.groupValues[3].toInt(),
        matcher.groupValues[4],
        matcher.groupValues[5],
        matcher.groupValues[6].toInt()
      )
    }
  }
}


class Day8 {
  fun start() {
    val input1 = Utils.readInput("day8_1")
    val instructions1 = parseInstructions(input1)
    val result1 = executeInstructions(instructions1)
    println("result1 = ${result1}")

    val input2 = Utils.readInput("day8_2")
    val instructions2 = parseInstructions(input2)
    val result2 = executeInstructions(instructions2)
    println("result2 = ${result2.first.values.max()} ${result2.second}")

  }

  private fun executeInstructions(instructions: List<Instruction>): Pair<Map<String, Int>,Int> {
    var registers = mutableMapOf<String, Int>()
    var maxRegisterValue=Int.MIN_VALUE

    for (instruction in instructions) {
      val conditionRegister = registers.getOrDefault(instruction.conditionRegister, 0)
      val conditionValue = instruction.conditionValue
      val check = when (instruction.conditionOperator) {
        "==" -> conditionRegister == conditionValue
        "!=" -> conditionRegister != conditionValue
        ">" -> conditionRegister > conditionValue
        "<" -> conditionRegister < conditionValue
        "<=" -> conditionRegister <= conditionValue
        ">=" -> conditionRegister >= conditionValue
        else -> {throw IllegalStateException("unknown condition operator ${instruction.conditionOperator}")}
      }
      if(check) {
        val incValue=if(instruction.operation=="inc") {instruction.value} else { -instruction.value }
        val registerValue=registers.getOrDefault(instruction.register,0)
        val newRegisterValue=registerValue+incValue
        if(maxRegisterValue<newRegisterValue) { maxRegisterValue=newRegisterValue}
        registers[instruction.register] = newRegisterValue
      }
    }
    return Pair(registers,maxRegisterValue)
  }

  private fun parseInstructions(input: List<String>): List<Instruction> {
    return input.map { Instruction.parseInstruction(it) }
  }
}

fun main() {
  Day8().start()
}