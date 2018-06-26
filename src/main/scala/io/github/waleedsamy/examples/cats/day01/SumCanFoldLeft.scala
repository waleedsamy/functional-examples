package io.github.waleedsamy.examples.cats.day01

// In SumList we sum all data in a List, here we will generalize the sum function to sum any data type that support foldLeft
object SumCanFoldLeft extends App {

  trait CanFoldLeft[M[_]] {
    def foldLeft[A, B](xs: M[A], b: B, f: (B, A) => B): B
  }

  object CanFoldLeft {

    implicit val ListFoldLift = new CanFoldLeft[List] {
      override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B =
        xs.foldLeft(b)(f)
    }

    implicit val SetFoldLift = new CanFoldLeft[Set] {
      override def foldLeft[A, B](xs: Set[A], b: B, f: (B, A) => B): B =
        xs.foldLeft(b)(f)
    }
  }

  def sum[M[_]: CanFoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[CanFoldLeft[M]]
    fl.foldLeft(xs, m.identity, m.combine)
  }

  println(sum(List(1, 2, 3, 4)))

  println(sum(Set("a", "b", "c", "d")))
}
