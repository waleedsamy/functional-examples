package io.github.waleedsamy.examples.cats

package object day01 {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def identity: A
  }

  object Monoid {

    implicit val intMonoid = new Monoid[Int] {
      def combine(a: Int, b: Int): Int = a + b

      def identity: Int = 0
    }

    implicit val stringMonoid = new Monoid[String] {
      def combine(a: String, b: String): String = a + b

      def identity: String = ""
    }
  }

}