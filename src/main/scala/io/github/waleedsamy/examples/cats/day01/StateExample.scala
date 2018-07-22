package io.github.waleedsamy.examples.cats.day01

import cats.data.State

// State[S, A] is basically a function S => (S, A), where S is the type that represents your state and
// A is the result the function produces. In addition to returning the result of type A,
// the function returns a new S value, which is the updated state.
object StateExample extends App {

  val rng = new scala.util.Random(0L)

  def createRobot1(): Robot = {
    val id = rng.nextLong()
    val sentient = rng.nextBoolean()
    val isCatherine = rng.nextBoolean()
    val name = if (isCatherine) "Catherine" else "Carlos"
    val isReplicant = rng.nextBoolean()
    val model = if (isReplicant) "replicant" else "borg"
    Robot(id, sentient, name, model)
  }

  // why repeat nextBoolean as createRobot1
  // maybe share it in var b (this is wrong)
  def createRobot2(): Robot = {
    val id = rng.nextLong()
    val b = rng.nextBoolean()
    val sentient = b
    val isCatherine = b
    val name = if (isCatherine) "Catherine" else "Carlos"
    val isReplicant = b
    val model = if (isReplicant) "replicant" else "borg"
    Robot(id, sentient, name, model)
  }

  def createRobot3(s0: Seed): Robot = {
    val (s1, id) = nextLong(s0)
    val (s2, sentient) = nextBoolan(s1)
    val (s3, isCatherine) = nextBoolan(s2)
    val name = if (isCatherine) "Catherine" else "Carlos"
    val (s4, isReplicant) = nextBoolan(s3)
    val model = if (isReplicant) "replicant" else "borg"
    Robot(id, sentient, name, model)
  }

  def nextBoolan(seed: Seed): State[Seed, Boolean] = State(

  )


  def nextLong(seed: Seed): (Seed, Long) =
    (seed.next, seed.long)

  println(createRobot1())
  println(createRobot1())
  println(createRobot2())
  println(createRobot2())

  val initialSeed = Seed(13L)

  println(createRobot3(initialSeed))
}

final case class Robot(id: Long, sentient: Boolean, name: String, model: String)

final case class Seed(long: Long) {
  def next = Seed(long * 6364136223846793005L + 1442695040888963407L)
}


