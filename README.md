# Functional Programming Tutorial

* A group is a set, G, together with an operation and must satisfy

  * Closure
    * For all a, b in G, then the product a • b is also in G.
  * Associativity
    * For all a, b and c in G, (a • b) • c = a • (b • c).
  * Identity element
    * There exists an element e in G such that, for every element a in G, the equation e • a = a • e = a holds
  * Inverse element
    * For each a in G, there exists an element b in G, commonly denoted a^−1 (or −a, if the operation is denoted "+"), such that a • b = b • a = e, where e is the identity element.

* A semigroup is a set that is closed under an associative binary operation and has **NO** identity element

* A monoid is a set that is closed under an associative binary operation and has an identity element, and does not need to have inverses (semigroup with Identity)
    * A binary operation f(x,y) is an operation that applies to two quantities or expressions x and y.

* Parametric polymorphism refers to when the type of a value contains one or more (unconstrained) type variables, 
  so that the value may adopt any type that results from substituting those variables with concrete types.

    ```scala
      def head[A](xs: List[A]): A = xs(0) // xs is Parametric polymorphism type
    ```

* Subtype polymorphism when trait needs to be mixed in at the time of defining the datatype. So it can’t work if you don't have access to the source code of your data types 

* Ad-hoc polymorphism is to provide an implicit conversion or implicit parameters for the trait. so you can provide your trait F1 and define an implicit converter of type X (i.e Int) 
  to the F1 type.
    ```scala
      // To allow a type to be added
      trait CanPlus[T]{
        def plus(a: T, b: T) : T
      }

      def plus[A: CanPlus](a1: Int, a2: Int): Int = {
        val m = implicitly[CanPlus[Int]] // you ask to be provided with an implementation of CanPlus of Int
        m.plus(a1, a2)
      }

      // you can add strings once your provides CanPlus[String] and make it available in the execution scope (maybe by passing it as a parameter or implicit)
      // we can provide function definitions to types (like String) without access to its source code
      // the function definitions can be enabled or disabled in different scopes
    ```


#### Resources:
  * https://en.wikipedia.org/wiki/Group_(mathematics)
  * http://mathworld.wolfram.com/Semigroup.html
  * http://mathworld.wolfram.com/Group.html
  * http://mathworld.wolfram.com/Monoid.html
  * http://eed3si9n.com/herding-cats