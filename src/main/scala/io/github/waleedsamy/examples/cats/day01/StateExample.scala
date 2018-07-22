package io.github.waleedsamy.examples.cats.day01

import cats.data.StateT
import cats.instances.future._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

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

  def createRobot3: StateT[Future, AsyncSeed, Robot] = for {
      id <- nextLong
      sentient <- nextBoolean
      isCatherine <- nextBoolean
      name = if (isCatherine) "Catherine" else "Carlos"
      isReplicant <- nextBoolean
      model = if (isReplicant) "replicant" else "borg"
    } yield Robot(id, sentient, name, model)


  // StateT[F[_], S, A] This data type represents computations of the form S => F[(S, A)].
  def nextBoolean: StateT[Future, AsyncSeed, Boolean] = nextLong.map(_ >= 0)

  def nextLong: StateT[Future, AsyncSeed, Long] = StateT(seed =>
    seed.next zip Future.successful(seed.long)
  )


  println(createRobot1())
  println(createRobot1())
  println(createRobot2())
  println(createRobot2())

  val initialSeed = Seed(13L)
  val initialAsyncSeed = AsyncSeed(0)

  val f1 = createRobot3.runA(initialAsyncSeed)
  f1 onComplete println
  Await.ready(f1, Duration.Inf)
}

final case class Robot(id: Long, sentient: Boolean, name: String, model: String)

final case class Seed(long: Long) {
  def next = Seed(long * 6364136223846793005L + 1442695040888963407L)
}

final case class AsyncSeed(long: Long) {
  def next = Future(AsyncSeed(long * 6364136223846793005L + 1442695040888963407L))
}


