import sbt._

object Common {
  import Keys._

  val servletApiDep = "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
  val jettyVersion = "9.2.5.v20141112"

  def specs2Dep(sv: String) =
    sv.split("[.-]").toList match {
      case "2" :: "9" :: _ => "org.specs2" %% "specs2" % "1.12.4.1"
      case _ => "org.specs2" %% "specs2-core" % "3.8.6"
    }


  val dispatchVersion = "0.8.11"
  def dispatchDeps =
    "net.databinder" %% "dispatch-mime" % dispatchVersion ::
    "net.databinder" %% "dispatch-http" % dispatchVersion :: Nil

  def dispatchOAuthDep =
    "net.databinder" %% "dispatch-oauth" % dispatchVersion

  def integrationTestDeps(sv: String) = (specs2Dep(sv) :: dispatchDeps) map { _ % "test" }

  val settings: Seq[Setting[_]] = Defaults.coreDefaultSettings ++ Seq(
    organization := "net.databinder",

    version := "2.0.0-SNAPSHOT",

    crossScalaVersions := Seq("2.12.0"),

    scalaVersion := crossScalaVersions.value.head,

    scalacOptions ++=
      Seq("-Xcheckinit", "-encoding", "utf8", "-deprecation", "-unchecked", "-feature"),

    javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8"),

    incOptions := incOptions.value.withNameHashing(true),

    parallelExecution in Test := false, // :( test servers collide on same port

    homepage := Some(new java.net.URL("http://unfiltered.databinder.net/")),

    publishMavenStyle := true,

    publishTo := Some("releases" at
              "https://oss.sonatype.org/service/local/staging/deploy/maven2"),

    publishArtifact in Test := false,

    licenses := Seq("MIT" -> url("http://www.opensource.org/licenses/MIT")),

    pomExtra := (
      <scm>
        <url>git@github.com:unfiltered/unfiltered.git</url>
        <connection>scm:git:git@github.com:unfiltered/unfiltered.git</connection>
      </scm>
      <developers>
        <developer>
          <id>n8han</id>
          <name>Nathan Hamblen</name>
          <url>http://twitter.com/n8han</url>
        </developer>
        <developer>
          <id>softprops</id>
          <name>Doug Tangren</name>
          <url>http://twitter.com/softprops</url>
        </developer>
      </developers>
    ),

    // this should resolve artifacts recently published to sonatype oss not yet mirrored to maven central
    resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"
  )
}
