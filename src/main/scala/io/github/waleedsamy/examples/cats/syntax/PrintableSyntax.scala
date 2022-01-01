package io.github.waleedsamy.examples.cats.syntax

import io.github.waleedsamy.examples.cats.types.Printable

object PrintableSyntax {

  implicit class PrintableOps[A](a: A) {
    def format()(implicit p: Printable[A]) = p.format(a)

    def print()(implicit p: Printable[A]) = println(format)
  }
}
