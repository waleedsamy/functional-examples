package io.github.waleedsamy.examples.cats.day01

import cats.data.Kleisli
import cats.implicits._

object kleisli extends  App {

  val twice: Int => Int = i => i * 2
  val countCats: Int => String = x => if (x == 1) "$x cat" else s"$x cats"

  // function can compose
  val twiceAsManyCats: Int => String = twice andThen countCats

  println(twice(3))
  println(countCats(4))
  println(twiceAsManyCats(1))

  // can't compose function with wrong types, in this case reciprocal accept Int to Option[Int]
  // parse is String => Option[Int]
  // reciprocal is Int => Option[Double]
  val parse: String => Option[Int] = i => if (i.matches("-?[0-9]+")) Some(i.toInt) else None
  val reciprocal: Int => Option[Double] = i => if (i != 0) Some(1.0 / i) else None

  // won't compile
  // val parseAndReciprocal: String => Option[Double] = parse andThen reciprocal


  // Kleisli is Kleisli[F[_], A, B], in different words A => F[B]
  // Kleisli[Option, String, Int] is String => Option[Int]
  // and we could compose Kleislis
  val parseK: Kleisli[Option, String, Int] = Kleisli { i => if (i.matches("-?[0-9]+")) Some(i.toInt) else None }
  val reciprocalK: Kleisli[Option, Int, Double] = Kleisli { i => if (i != 0) Some(1.0 / i) else None }

  val parseAndReciprocal: Kleisli[Option, String, Double] = parseK andThen reciprocalK

  println(parseAndReciprocal.run("89"))
}
