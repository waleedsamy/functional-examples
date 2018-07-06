package io.github.waleedsamy.examples.cats.day01

import cats.data._
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


object Transformer extends App {

  val f1 = db.findAddressByUserId(1)

  f1 map { a => println(s"findAddressByUserId $a") }

  Await.ready(f1, Duration.Inf)
}


object db {
  case class User(id: Long, name: String)
  case class Address(id: Long, userId: Long)


  def findUserById(id: Long): OptionT[Future, User] = OptionT {
    Future(Option(User(1, "A")))
  }

  def findAddressByUser(user: User):  OptionT[Future, Address] = OptionT {
    Future(Option(Address(1, 1)))
  }

  def findAddressByUserId(id: Long): Future[Option[Address]] = (for {
    user <- findUserById(id)
    address <- findAddressByUser(user)
  } yield address).value

}


// if you look closely, you’ll realize we don’t need to know anything specific about the “outer” Monad (Future and List from the previous examples).
// As long as we can map and flatMap over it, we’re fine. On the other hand, see how we destructured the Option?
// That’s some specific knowledge about the “inner” Monad (Option in this case) that we need to have.
// With this piece of information in our pocket, we can write a generic data structure that “wraps” any Monad M around an Option.
// Here’s the shocking news: we’ve just accidentally invented a Monad Transformer, usually named OptionT! 😱

// OptionT[F, A] is a flat version of F[Option[A]] which is a Monad itself