package io.github.waleedsamy.examples.cats.day01

import cats.data.Kleisli
import cats.implicits._

object kleisli extends App {

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
  val parseK: Kleisli[Option, String, Int] = Kleisli { i =>
    if (i.matches("-?[0-9]+")) Some(i.toInt) else None
  }
  val reciprocalK: Kleisli[Option, Int, Double] = Kleisli { i =>
    if (i != 0) Some(1.0 / i) else None
  }

  val parseAndReciprocal: Kleisli[Option, String, Double] = parseK andThen reciprocalK

  println(parseAndReciprocal.run("89"))

  // What Kleisli#local allows us to do is essentially “expand” our input type to a more “general” one.
  // final case class Kleisli[F[_], A, B](run: A => F[B]) {
  //  def local[AA](f: AA => A): Kleisli[F, AA, B] = Kleisli(f.andThen(run))
  // }

  // I have Kleisli of String => Option[Int]
  // but I expand it to Unit => Option[Int]
  val c = parseK local [Unit] (_ => "7")
  println(c.run())

  case class DbConfig(url: String, user: String, pass: String)
  trait Db
  object Db {
    val fromDbConfig: Kleisli[Option, DbConfig, Db] = ???
  }

  case class ServiceConfig(addr: String, port: Int)
  trait Service
  object Service {
    val fromServiceConfig: Kleisli[Option, ServiceConfig, Service] = ???
  }

  case class AppConfig(dbConfig: DbConfig, serviceConfig: ServiceConfig)

  class App(db: Db, service: Service)

  def appFromAppConfig: Kleisli[Option, AppConfig, App] =
    for {
      db <- Db.fromDbConfig.local[AppConfig](_.dbConfig)
      sv <- Service.fromServiceConfig.local[AppConfig](_.serviceConfig)
    } yield new App(db, sv)

  // This is the same as the previous for-comprehension
  // return Kleisli that take AppConfig and return DB, Kleisli[Option, AppConfig, Db]
//  val k1 = Db.fromDbConfig.local[AppConfig](_.dbConfig)
//  val k2 = Service.fromServiceConfig.local[AppConfig](_.serviceConfig)
//
//  def appFromAppConfigw: Kleisli[Option, AppConfig, App] = k1.flatMap { db =>
//    k2.map { sv =>
//      new App(db, sv)
//    }
//  }

  val dbConfig = DbConfig("127.0.0.1", "root", "password")
  val serviceConfig = ServiceConfig("example.com", 8080)
  val appConfig = AppConfig(dbConfig, serviceConfig)

  appFromAppConfig.run(appConfig)

}
