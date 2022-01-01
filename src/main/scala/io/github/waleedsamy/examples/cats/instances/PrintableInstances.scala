package io.github.waleedsamy.examples.cats.instances

import io.github.waleedsamy.examples.cats.Cat
import io.github.waleedsamy.examples.cats.types.Printable

object PrintableInstances {

  implicit val PrintableInt = new Printable[Int] {
    override implicit def format(a: Int): String = a.toString()
  }

  implicit val PrintableString = new Printable[String] {
    override implicit def format(a: String): String = a
  }

  implicit val PrintableCat = new Printable[Cat] {
    override implicit def format(a: Cat): String = f"${a.name} is ${a.age} years old ${a.color} cat"
  }
}
