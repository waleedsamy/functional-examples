package io.github.waleedsamy.examples.cats.types

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._
import zio.ZIO

class ZIOExampleSpec extends AnyFlatSpec with should.Matchers with OptionValues with StrictLogging {

  "ZIOExampleSpec" should "producer consumer" in {
    val zop = ZIO.fromOption(Some(3))
    val x = zop.mapError(_ => "non")
    2 should be(2)
  }
}
