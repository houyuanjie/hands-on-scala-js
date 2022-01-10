lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name         := "hands-on-scala-js",
    version      := "0.1.0-SNAPSHOT",
    scalaVersion := "2.13.7",
    libraryDependencies ++= Seq(
      "org.scala-js"                  %%% "scalajs-dom" % "2.1.0",
      "com.lihaoyi"                   %%% "scalatags"   % "0.11.0",
      "com.softwaremill.sttp.client3" %%% "core"        % "3.3.18"
    ),
    scalacOptions ++= Seq(
      "-Xsource:3",
      "-Xfatal-warnings",
      "-Ywarn-unused",
      "-deprecation",
      "-unchecked"
    )
  )
