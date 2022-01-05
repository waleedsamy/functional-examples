package io.github.waleedsamy.examples.cats.types
import cats.data.OptionT
import cats.implicits._
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class MonadTransformersSpec extends AnyFlatSpec with should.Matchers with OptionValues {

  "MonadTransformers" should "compose Monads easily" in {
    val x = for {
      a <- 1.pure[OptionT[Future, *]]
      b <- 1.pure[OptionT[Future, *]]
    } yield a + b

    Await.result(x.value, 1.second).value should be(2)
  }
}
