package org.example.org.vorgi.advent2017

import org.example.org.vorgi.Utils
import kotlin.math.abs

data class Point3D(val x: Long, val y: Long, val z: Long)
data class Particle(val position: Point3D, val velocity: Point3D, val acceleration: Point3D)

operator fun Point3D.plus(otherPoint:Point3D) : Point3D {
  return Point3D(this.x+otherPoint.x,this.y+otherPoint.y,this.z+otherPoint.z)
}

fun parseParticle(line: String): Particle {
  val regex =
    """p=<\s*(-?\d+),(-?\d+),(-?\d+)>, v=<\s*(-?\d+),(-?\d+),(-?\d+)>, a=<\s*(-?\d+),(-?\d+),(-?\d+)>""".toRegex()
  val matchResult = regex.matchEntire(line) ?: throw IllegalArgumentException("Invalid input line: $line")
  val (px, py, pz, vx, vy, vz, ax, ay, az) = matchResult.destructured
  return Particle(
    Point3D(px.toLong(), py.toLong(), pz.toLong()),
    Point3D(vx.toLong(), vy.toLong(), vz.toLong()),
    Point3D(ax.toLong(), ay.toLong(), az.toLong())
  )
}

fun position(initial_position: Long, initial_velocity: Long, acceleration: Long, n: Int): Long {
  return (initial_position + initial_velocity * (n + 1) + 0.5 * acceleration * n * (n + 1)).toLong()
}

class Day20 {
  fun start() {
    val input1 = """p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>""".lines()

    val result1 = findNearest(input1)
    println("result1 = ${result1}")

    val input2= Utils.readInput("day20")
    val result2 = findNearest(input2)
    println("result2 = $result2")

    val input3 = """p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>
p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>""".lines()
    val result3 = checkParticles(input3)
    println("result3 = ${result3}")

    val result4 = checkParticles(input2)
    println("result4 = ${result4}")
  }

  private fun checkParticles(input: List<String>): Int {
    var particles = input.map { line -> parseParticle(line) }.toMutableList()

    for(i in 0..<1000) {
      particles=particles.filter { particle: Particle ->
        val collided=particles.count { otherParticle -> particle.position==otherParticle.position }
        collided==1
      }.toMutableList()
      particles=particles.map { particle: Particle ->
        val newVelocity=particle.velocity + particle.acceleration
        val newPosition = particle.position+newVelocity
        Particle(newPosition,newVelocity, particle.acceleration)
      }.toMutableList()
    }
    return particles.size
  }

  private fun findNearest(input: List<String>): Int {
    val particles = input.map { line -> parseParticle(line) }.toMutableList()

    println(particles)
    val pointAt1000 = particles.map { particle: Particle ->
      val x = position(particle.position.x, particle.velocity.x, particle.acceleration.x, 1000)
      val y = position(particle.position.y, particle.velocity.y, particle.acceleration.y, 1000)
      val z = position(particle.position.z, particle.velocity.z, particle.acceleration.z, 1000)
      Point3D(x,y,z)
    }
    val minPoint=pointAt1000.minBy { abs(it.x)+abs(it.y)+abs(it.z) }


    return pointAt1000.indexOf(minPoint)
  }
}

fun main() {
  Day20().start()
}