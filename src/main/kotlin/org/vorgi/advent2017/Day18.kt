package org.example.org.vorgi.advent2017

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.example.org.vorgi.Utils
import java.util.concurrent.atomic.AtomicInteger

open class SoundMachine(val instructions: List<String>) {
  val registers = mutableMapOf<String, Long>()
  var pc = 0

  var lastSoundPlayed: Long? = null
  var recoveredSound: Long? = null

  suspend fun executeInstruction() {
    val instruction = instructions[pc]
    val parts = instruction.split(" ")
    val registerName = parts[1]
    val valueName = if (parts.size >= 3) parts[2] else ""
    when (parts[0]) {
      "snd" -> {
        send(registerName)
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
        receive(registerName)
        pc++
      }

      "jgz" -> {
        if (getValue(registerName) > 0) {
          val value = getValue(valueName)
          pc += value.toInt()
        } else {
          pc++
        }
      }

      else -> throw IllegalArgumentException("Invalid instruction: $instruction")
    }
  }

  protected open suspend fun receive(registerName: String) {
    if (getValue(registerName) != 0L) {
      println("Recovered sound: ${lastSoundPlayed}")
      recoveredSound = lastSoundPlayed
    }
  }

  protected open suspend fun send(registerName: String) {
    lastSoundPlayed = getValue(registerName)
  }

  protected fun getValue(registerName: String): Long {
    if (!registerName.matches(Regex("""[-\d]*"""))) {
      return registers.getOrDefault(registerName, 0)
    } else {
      return registerName.toLong()
    }
  }
}

class SynchronizedSoundMachine(instructions: List<String>, val programID: Int) : SoundMachine(instructions) {

  companion object {
    val channels: Array<Channel<Long>> = Array(2) { Channel(Channel.UNLIMITED) }
    val receivingChannels = AtomicInteger(0)
    val sendsSend = AtomicInteger(0)
  }

  fun otherChannel(): Int {
    return (programID + 1) % 2
  }

  override suspend fun send(registerName: String) {
    if(programID==1) {
      sendsSend.getAndIncrement()
    }
    channels[otherChannel()].send(getValue(registerName))
  }

  override suspend fun receive(registerName: String) {
    val waitingChannels = receivingChannels.getAndIncrement()

    channels[otherChannel()].receive()
    receivingChannels.getAndDecrement()
  }
}

class Day18 {
  suspend fun start() {
    val input1 = Utils.readInput("day18_1")
    val result1 = findSound(input1)
    println("result1 = $result1")

    val input2 = Utils.readInput("day18_2")
    val result2 = findSound(input2)
    println("result2 = $result2")

    val input3 = Utils.readInput("day18_3")
    val result3 = countSends(input3)

    println("result3 = ${result3}")

    val result4 = countSends(input2)
    println("result4 = ${result4}")
  }

  private suspend fun countSends(input: List<String>): Int = runBlocking {
    val soundMachines = Array(2) { id -> SynchronizedSoundMachine(input, id) }

    val jobs: MutableList<Job> = mutableListOf()
    var cancelled = false
    soundMachines.forEach { soundMachine ->
      val job: Job
      job = launch {
        while (!cancelled) {
          println("execute ${soundMachine.programID}")
          soundMachine.executeInstruction()
          println("receivingChannels: ${SynchronizedSoundMachine.receivingChannels}")
        }
      }
      jobs += job
    }

    println("here")

    while (SynchronizedSoundMachine.receivingChannels.get() != 2) {
      delay(500)
    }

    jobs.forEach { job ->
      job.cancelAndJoin()
    }

    println("done")
    return@runBlocking SynchronizedSoundMachine.sendsSend.get()
  }

  private suspend fun findSound(input: List<String>): Long? {
    val soundMachine = SoundMachine(input)
    var recoveredSound: Long? = null
    do {
      soundMachine.executeInstruction()
      recoveredSound = soundMachine.recoveredSound
      println(soundMachine.registers)
    } while (recoveredSound == null)


    return recoveredSound
  }

}

suspend fun main() {
  Day18().start()
}