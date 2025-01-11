package org.example.org.vorgi

open class Point(var x: Int, var y: Int) {
  constructor(p: Point) : this(p.x, p.y)

  operator fun minus(point: Point): Point {
    return Point(this.x - point.x, this.y - point.y)
  }

  operator fun plus(point: Point): Point {
    return Point(this.x + point.x, this.y + point.y)
  }

  open operator fun plus(dir: Direction): Point {
    return Point(dir.dx, dir.dy) + this
  }

  fun getDirection(otherPoint: Point): Direction? {
    if (this.x == otherPoint.x && this.y == otherPoint.y + 1) {
      return Direction.Up
    }
    if (this.x == otherPoint.x && this.y == otherPoint.y - 1) {
      return Direction.Down
    }
    if (this.y == otherPoint.y && this.x == otherPoint.x - 1) {
      return Direction.Right
    }
    if (this.y == otherPoint.y && this.x == otherPoint.x + 1) {
      return Direction.Left
    }

    return null
  }


  override fun toString(): String {
    return "$x,$y"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Point

    if (x != other.x) return false
    if (y != other.y) return false

    return true
  }

  override fun hashCode(): Int {
    var result = x
    result = 31 * result + y
    return result
  }
}

open class CharGrid(input: List<String>) {
  val width: Int = input[0].length
  val height: Int = input.size
  private val data: MutableList<MutableList<Char>> = MutableList(height) { y ->
    MutableList(width) { x -> input[y][x] }
  }

  constructor(width: Int, height: Int, fillChar: Char = '.') : this(
    List(height) { "$fillChar".repeat(width) }
  )

  fun copy(): CharGrid {
    val newLines = data.map { line ->
      buildString {
        line.forEach { append(it) }
      }
    }
    return CharGrid(newLines)
  }

  fun isInside(x: Int, y: Int): Boolean {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return false
    }
    return true
  }

  fun isInside(p: Point): Boolean {
    return isInside(p.x, p.y)
  }

  fun getAt(x: Int, y: Int): Char {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return '?'
    }
    return data[y][x]
  }

  fun getAt(point: Point): Char {
    return getAt(point.x, point.y)
  }

  fun setAt(x: Int, y: Int, char: Char) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return
    }
    data[y][x] = char
  }

  fun setAt(p: Point, char: Char) {
    setAt(p.x, p.y, char)
  }

  override fun toString(): String {
    val returnValue = buildString {
      for (line in data) {
        for (char in line) {
          append(char)
        }
        append('\n')
      }
    }
    return returnValue
  }

  fun find(searchFunction: (x: Int, y: Int) -> Boolean): Point? {
    for (y in 0..<height) {
      for (x in 0..<width) {
        if (searchFunction(x, y)) {
          return Point(x, y)
        }
      }
    }
    return null
  }

  fun findAll(searchFunction: (x: Int, y: Int) -> Boolean): List<Point> {
    val result = mutableListOf<Point>()
    for (y in 0..<height) {
      for (x in 0..<width) {
        if (searchFunction(x, y)) {
          result += Point(x, y)
        }
      }
    }
    return result
  }
}

enum class Direction(val dx: Int, val dy: Int) {
  Up(0, -1), Right(1, 0), Down(0, 1), Left(-1, 0), ;

  operator fun plus(point: Point): Point {
    return Point(this.dx, this.dy) + point
  }
}