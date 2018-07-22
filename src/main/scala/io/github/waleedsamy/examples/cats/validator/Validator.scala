package io.github.waleedsamy.examples.cats.validator

import cats.data.ValidatedNel
import cats.syntax.ApplicativeOps
import cats.implicits._
import io.github.waleedsamy.examples.cats.validator.FormValidator._

object Validator extends App {

  type ValidationResult[A] = ValidatedNel[DomainValidation, A]

  def validateUserName(userName: String): ValidationResult[String] =
    if (userName.matches("^[a-zA-Z0-9]+$")) userName.validNel
    else UsernameHasSpecialCharacters.invalidNel

  def validatePassword(password: String) =
    if (password.matches(
          "(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"))
      password.validNel
    else PasswordDoesNotMeetCriteria.invalidNel

  def validateFirstName(firstName: String) =
    if (firstName.matches("^[a-zA-Z]+$")) firstName.validNel
    else FirstNameHasSpecialCharacters.invalidNel

  def validateLastName(lastName: String) =
    if (lastName.matches("^[a-zA-Z]+$")) lastName.validNel
    else LastNameHasSpecialCharacters.invalidNel

  def validateAge(age: Int) =
    if (age >= 18 && age <= 75) age.validNel else AgeIsInvalid.invalidNel

  def validateForm(username: String,
                   password: String,
                   firstName: String,
                   lastName: String,
                   age: Int) = {
    val l = (validateUserName(username),
             validatePassword(password),
             validateFirstName(firstName),
             validateLastName(lastName),
             validateAge(age))

    l.mapN(RegistrationData)

  }

  println(
    validateForm(
      username = "fakeUs3rname",
      password = "password",
      firstName = "John",
      lastName = "Doe",
      age = 15
    ),
    validateForm(
      username = "Joe",
      password = "Passw0r$1234",
      firstName = "John",
      lastName = "Doe",
      age = 21
    )
  )
}

sealed trait FormValidator {

  final case class RegistrationData(username: String,
                                    password: String,
                                    firstName: String,
                                    lastName: String,
                                    age: Int)

  sealed trait DomainValidation {
    def errorMessage: String
  }

  case object UsernameHasSpecialCharacters extends DomainValidation {
    def errorMessage: String = "Username cannot contain special characters."
  }

  case object PasswordDoesNotMeetCriteria extends DomainValidation {
    def errorMessage: String =
      "Password must be at least 10 characters long, including an uppercase and a lowercase letter, one number and one special character."
  }

  case object FirstNameHasSpecialCharacters extends DomainValidation {
    def errorMessage: String =
      "First name cannot contain spaces, numbers or special characters."
  }

  case object LastNameHasSpecialCharacters extends DomainValidation {
    def errorMessage: String =
      "Last name cannot contain spaces, numbers or special characters."
  }

  case object AgeIsInvalid extends DomainValidation {
    def errorMessage: String =
      "You must be aged 18 and not older than 75 to use our services."
  }
}

object FormValidator extends FormValidator
