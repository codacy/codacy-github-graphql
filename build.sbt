import sys.process._

val scala212 = "2.12.12"

ThisBuild / scalaVersion := scala212
ThisBuild / crossScalaVersions := Seq(scala212)

name := "codacy-github-graphql"

description := "Generated models and queries for GitHub v4 API in GraphQL"

val apolloVersion = IO
  .readLines(file("gradle.properties"))
  .find(_.startsWith("apolloVersion="))
  .map(_.stripPrefix("apolloVersion="))
  .getOrElse(
    throw new Exception("missing apolloVersion property in gradle.properties")
  )

libraryDependencies ++= Seq(
  "com.apollographql.apollo" % "apollo-runtime" % apolloVersion
)

val runGradleSourceGenerator = Def.task {
  IO.delete(baseDirectory.value / "build")
  Seq("./gradlew", "generateMainGithubApolloSources").!
}

Compile / compile := (Compile / compile)
  .dependsOn(runGradleSourceGenerator)
  .value

Compile / unmanagedSourceDirectories += baseDirectory.value / "build" / "generated" / "source"

homepage := Some(url("https://github.com/codacy/codacy-github-graphql"))

// HACK: This setting is not picked up properly from the plugin
pgpPassphrase := Option(System.getenv("SONATYPE_GPG_PASSPHRASE"))
  .map(_.toCharArray)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/codacy/codacy-github-graphql"),
    "scm:git:git@github.com:codacy/codacy-github-graphql.git"
  )
)
