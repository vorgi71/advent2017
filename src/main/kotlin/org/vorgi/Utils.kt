package org.example.org.vorgi

import java.io.File
import kotlin.time.measureTime


object Utils {
  fun readInput(name: String) = File("src/test/resources", "$name.txt")
    .readLines()

  fun readInputAsText(name: String) = File("src/test/resources", "$name.txt")
    .readText()
}

fun <T> List<T>.fullPermutations(): List<List<T>> {
  if (this.isEmpty()) return listOf(emptyList())
  return this.flatMap {
    this.minusElement(it).fullPermutations().map { subPermutation ->
      listOf(it) + subPermutation
    }
  }
}

fun <T> List<T>.permutations(): List<List<T>> {
  if (this.size == 1) return listOf(this)
  val permutations = mutableListOf<List<T>>()
  val subList = this.drop(1)
  for (item in this) {
    val remainingItems = subList.filter { it != item }
    val subPermutations = remainingItems.permutations()
    for (subPermutation in subPermutations) {
      permutations.add(listOf(item) + subPermutation)
    }
  }
  return permutations
}

fun Int.pow(m:Int) : Int {
  var result= 1
  for(i in 0..<m) {
    result*=this
  }
  return result
}

fun UInt.pow(m:UInt) : UInt {
  var result= 1U
  for(i in 0..<m.toInt()) {
    result*=this
  }
  return result
}

fun Long.pow(m:Long) :Long {
  var result=1L
  for(i in 0..<m) {
    result*=this
  }
  return result
}

fun <T> List<T>.combinations(size: Int = this.size): List<List<T>> {
  if (size == 0) return listOf(emptyList())
  if (size == 1) return this.map { listOf(it) }

  val combinations = mutableListOf<List<T>>()
  for (element in this) {
    val subCombinations = this.combinations(size - 1)
    for (subCombination in subCombinations) {
      combinations.add(listOf(element) + subCombination)
    }
  }
  return combinations
}

fun <T, R> ((T) -> R).memoize(): (T) -> R {
  val cache = mutableMapOf<T, R>()
  return { arg ->
    if(arg==null) {
      println("cache: ${cache.keys.size}")
    }
    cache.getOrPut(arg) { this(arg) }
  }
}

fun fibonacci(n: Int): Int {
  return if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
}

fun main() {
  listOf('1','2','3').combinations(2).forEach {
    println(it)
  }

  val memFib = ::fibonacci.memoize()
  val normalTime=measureTime {
    memFib(35)
  }
  val fibTime=measureTime {
    memFib(35)
  }

  println("normalTime: $normalTime fibTime $fibTime = ${memFib(35)}")
}