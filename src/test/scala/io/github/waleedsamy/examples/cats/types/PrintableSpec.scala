import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import io.github.waleedsamy.examples.cats.Cat
import io.github.waleedsamy.examples.cats.types.Printable
import io.github.waleedsamy.examples.cats.instances.PrintableInstances._
import io.github.waleedsamy.examples.cats.syntax.PrintableSyntax._

object PrintableSpec extends Properties("Printable") {

  property("format with Interface Objects") = forAll { (name: String, age: Int, color: String) =>
    implicitly[Printable[Cat]].format(Cat(name, age, color)) == f"$name is $age years old $color cat"
    Printable.format(Cat(name, age, color)) == f"$name is $age years old $color cat"
  }

  property("format with Interface Syntax") = forAll { (name: String, age: Int, color: String) =>
    Cat(name, age, color).format == f"$name is $age years old $color cat"
  }
}
