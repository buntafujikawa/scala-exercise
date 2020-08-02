name := "scala-exercise"

version := "1.0"

scalaVersion := "2.13.3"

//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
//libraryDependencies += "org.mockito" % "mockito-core" % "3.4.3" % "test"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0",
  "org.mockito" % "mockito-core" % "3.4.3",
  "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
  "org.mindrot"     %  "jbcrypt"     % "0.4"
)
