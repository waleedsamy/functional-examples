package io.github.waleedsamy.examples.cats.types

import cats.effect._
import cats.effect.std.{Console, Queue}
import cats.implicits._
import com.typesafe.scalalogging.StrictLogging
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._

import scala.concurrent.duration._
import scala.collection.immutable.{Queue => ScalaQueue}

class QueueSpec extends AnyFlatSpec with should.Matchers with OptionValues with StrictLogging {

  "QueueSpec" should "producer consumer" in {
    import cats.effect.unsafe.implicits.global
    main.unsafeRunSync()
    2 should be(2)
  }

  val count = 1000

  def producer(q: Queue[IO, Int], n: Int): IO[Unit] =
    if (n > 0)
      q.offer(count - n)
        .flatMap(_ => {
          IO { logger.info("Offered") } *> producer(q, n - 1)
        })
    else IO.unit

  def consumer(
    q: Queue[IO, Int],
    n: Int,
    acc: ScalaQueue[Int] = ScalaQueue.empty
  ): IO[Long] =
    if (n > 0)
      q.take.flatMap { a =>
        IO { logger.info(s"Consumed ${a}") } *> consumer(q, n - 1, acc.enqueue(a))
      }
    else
      IO.pure(acc.foldLeft(0L)(_ + _))

  def main: IO[ExitCode] = {
    for {
      queueR <- Queue.bounded[IO, Int](10)
      res <- (consumer(queueR, count), producer(queueR, count))
        .parMapN((_, _) => ExitCode.Success)
        .handleErrorWith { t =>
          Console[IO].errorln(s"Error caught: ${t.getMessage}").as(ExitCode.Error)
        }
    } yield res
  }

}
