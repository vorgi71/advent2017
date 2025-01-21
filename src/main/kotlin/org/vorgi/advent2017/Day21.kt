package org.example.org.vorgi.advent2017

import org.example.org.vorgi.*

class Day21 {
  fun start() {
    val input1 = Utils.readInput("day21_1")
    val result1= createPattern(input1, 2)
    println("result1 = ${result1}")

    val input2 = Utils.readInput("day21_2")
    val result2 = createPattern(input2,5)
    println("result2 = ${result2}")
  }

  private fun createPattern(input: List<String>, iterations: Int): Int {
    val rules = parsePattern(input)

    var charGrid = CharGrid(".#.\n..#\n###".lines())

    for (index in 0..<iterations) {
      val newCharGrid = CharGrid(charGrid.width + 1, charGrid.height + 1)
      val splitGrids = if (charGrid.width % 3 == 0) {
        splitGrid(charGrid, 3)
      } else {
        splitGrid(charGrid, 2)
      }

      for (y in splitGrids.indices) {
        for (x in 0..<splitGrids[y].size) {
          val foundGrid =
            rules[splitGrids[y][x]] ?: throw IllegalStateException("no grid found ${splitGrids[x][y]}")
          for (cy in 0..<foundGrid.height) {
            for (cx in 0..<foundGrid.width) {
              newCharGrid.setAt(cx + x * foundGrid.width, cy + y * foundGrid.height, foundGrid.getAt(cx, cy))
            }
          }
        }
      }
      println(newCharGrid)
      charGrid = newCharGrid
    }

    return charGrid.toString().count { c -> c=='#' }
  }


  private fun splitGrid(grid: CharGrid, div: Int): Array<Array<CharGrid>> {
    val divisions = grid.height / div
    val output = Array(divisions) { Array(divisions) { CharGrid(1, 1) } }
    for (y in 0..<divisions) {
      for (x in 0..<divisions) {
        val newGrid = CharGrid(div, div, '.')
        for (cy in 0..<div) {
          for (cx in 0..<div) {
            newGrid.setAt(cx, cy, grid.getAt(cx + x * div, cy + y * div))
          }
        }
        output[y][x] = newGrid
      }
    }
    return output
  }

  fun createVariations(charGrid: CharGrid): Set<CharGrid> {
    val variations = mutableListOf<CharGrid>()

    variations += charGrid
    variations += charGrid.rotate90()
    variations += charGrid.rotate90().rotate90()
    variations += charGrid.rotate90().rotate90().rotate90()
    variations += charGrid.flipHorizontally()
    variations += charGrid.rotate90().flipHorizontally()
    variations += charGrid.rotate90().rotate90().flipHorizontally()
    variations += charGrid.rotate90().rotate90().rotate90().flipHorizontally()

    // I,R,RR,RRR, H,RH,RRH, RRRH

    return variations.toSet()
  }

  private fun parsePattern(input: List<String>): Map<CharGrid, CharGrid> {
    val keyMap = mutableMapOf<CharGrid, CharGrid>()
    for (line in input) {
      val split = line.split(" => ").map { it.replace('/', '\n') }
      val charGridKey = CharGrid(split[0].lines())
      val charGridValue = CharGrid(split[1].lines())
      for (variation in createVariations(charGridKey)) {
        keyMap[variation] = charGridValue
      }
    }
    return keyMap
  }
}

fun main() {
  Day21().start()
}