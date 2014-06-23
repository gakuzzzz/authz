package authz

import scalaz.Free.FreeC
import scalaz.{\/, Monad, Free, Inject}

class Authz[F[_]](implicit I: Inject[Command, F]) {

  private[this] type FreeCF[A] = FreeC[F, A]

  private[this] def lift[A](f: Command[A]): FreeCF[A] =
    Free.liftFC(I.inj(f))

  import Command._

  def authenticate(identifier: Identifier): FreeCF[Option[User]] =
    lift(Authentication(identifier))
  def authorize(user: User, authority: Authority): FreeCF[Boolean] =
    lift(Authorization(user, authority))

  def login(token: Token, password: PlainPassword): FreeCF[Option[(User, Identifier)]] =
    lift(Login(token, password))
  def logout(identifier: Identifier): FreeCF[Unit] =
    lift(Logout(identifier))

}
