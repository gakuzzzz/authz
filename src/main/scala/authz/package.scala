import scalaz._
import scalaz.Free.FreeC
import scalaz.Id.Id

package object authz {

  type InterpreterF[F[_]] = Command ~> F
  type Interpreter = InterpreterF[Id]

  private[authz] def freeCMonad[F[_]] =
    Free.freeMonad[({type l[a] = Coyoneda[F, a]})#l]

  // https://github.com/scalaz/scalaz/blob/5904d7f6/core/src/main/scala/scalaz/Free.scala#L328
  private[authz] def runFC[S[_], M[_], A](sa: FreeC[S, A])(interp: S ~> M)(implicit M: Monad[M]): M[A] =
    sa.foldMap(new (({type λ[x] = Coyoneda[S, x]})#λ ~> M) {
      def apply[AA](cy: Coyoneda[S, AA]): M[AA] =
        M.map(interp(cy.fi))(cy.k)
      }
    )


  // TODO: make thiese types enable to defined by lib users
  type User          = String // TODO:
  type Identifier    = String // TODO:
  type Authority     = String // TODO:
  type Token         = String // TODO:
  type PlainPassword = String // TODO:


}
