lazy val commonSettings = Seq(
    organization := "com.myproject"
    version := "1.1.0"
    scalaVersion := "2.12.1"
)

lazy val root = (project in file(".")).
    settings(commonSettings: _*).
    settings(
        name := "progress",
        scalacOptions += "-deprecation",
        libraryDependencies ++= Seq(
            "org.postgresql" % "postgresql" % "42.0.0",
            "joda-time" % "joda-time" % "2.9.9"
        )
    )
