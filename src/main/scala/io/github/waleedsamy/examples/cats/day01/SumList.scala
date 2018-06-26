package io.github.waleedsamy.examples.cats.day01

object SumList extends App {

  def sumInt1(xs: List[Int], m: Monoid[Int]): Int =
    xs.foldLeft(m.identity)(m.combine)
  def sumInt2(xs: List[Int])(implicit m: Monoid[Int]): Int =
    xs.foldLeft(m.identity)(m.combine)

  def sum[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    xs.foldLeft(m.identity)(m.combine)
  }

  println(sumInt1(List(1, 2, 3, 4), Monoid.intMonoid))
  println(sumInt2(List(1, 2, 3, 4)))
  println(sum(List(1, 2, 3, 4)))

  val multiMonoid: Monoid[Int] = new Monoid[Int] {
    override def combine(a: Int, b: Int): Int = a * b

    override def identity: Int = 1
  }
  println(sum(List(1, 2, 3, 4))(multiMonoid))

  def sumString1(xs: List[String], m: Monoid[String]): String =
    xs.foldLeft(m.identity)(m.combine)
  def sumString2(xs: List[String])(implicit m: Monoid[String]): String =
    xs.foldLeft(m.identity)(m.combine)

  println(sumString1(List("a", "b"), Monoid.stringMonoid))
  println(sumString2(List("a", "b")))
  println(sum(List("a", "b")))
}
