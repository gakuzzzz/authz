package authz

sealed abstract class Command[A]

object Command {

  case class Authentication(identifier: Identifier) extends Command[Option[User]]
  case class Authorization(user: User, authority: Authority) extends Command[Boolean]

  // TODO: OAuth2, OpenId, and so on.
  case class Login(token: Token, password: PlainPassword) extends Command[Option[(User, Identifier)]]
  case class Logout(identifier: Identifier) extends Command[Unit] // Unit?

}
