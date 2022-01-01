package io.github.waleedsamy.examples.cats.types

trait Printable[A] {
  implicit def format(a: A): String
}

object Printable {
  def format[A](a: A)(implicit p: Printable[A]) = p.format(a)

  def print[A](a: A)(implicit p: Printable[A]) = println(format(a))
}
