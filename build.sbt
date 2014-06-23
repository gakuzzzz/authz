name := "authz"

scalaVersion := "2.11.1"

crossScalaVersions := scalaVersion.value :: "2.10.4" :: Nil

organization := "jp.t2v"

startYear := Some(2014)

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  Nil
)

scalacOptions ++= (
  if(scalaVersion.value.startsWith("2.10"))
    Nil
  else
    Seq("-Ywarn-unused", "-Ywarn-unused-import")
)

libraryDependencies ++= (
  ("org.scalaz" %% "scalaz-core" % "7.1.0-M7") ::
  Nil
)

def gitHash: String = scala.util.Try(
  sys.process.Process("git rev-parse HEAD").lines_!.head
).getOrElse("master")

scalacOptions in (Compile, doc) ++= {
  val tag = if(isSnapshot.value) gitHash else { "v" + version.value }
  Seq(
    "-sourcepath", baseDirectory.value.getAbsolutePath,
    "-doc-source-url", s"https://github.com/gakuzzzz/authz/tree/${tag}€{FILE_PATH}.scala"
  )
}
