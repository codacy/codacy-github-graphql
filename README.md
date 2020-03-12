# codacy-github-graphql

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/75259e9537da4aa48e6744dff277f0e3)](https://www.codacy.com/gh/codacy/codacy-github-graphql?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=codacy/codacy-github-graphql&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/codacy/codacy-github-graphql.svg?style=svg)](https://circleci.com/gh/codacy/codacy-github-graphql)
[![Download](https://api.bintray.com/packages/codacy/maven/codacy-github-graphql/images/download.svg)](https://bintray.com/codacy/maven/codacy-github-graphql/_latestVersion)

Library with Apollo compiled queries and models for GitHub API V4

## Use

This package is published under bintray, you can check it out in https://bintray.com/codacy/maven/codacy-github-graphql.

You need to add the resolver `https://dl.bintray.com/codacy/maven` to your project to use this library.

## Build

```sh
./gradlew build
```

## Usage

Look for queries under package `com.github.api.v4._`
and use `com.codacy.graphql.apollo.client.ScalaApolloClient` to get futures instead of callbacks from the ApolloClient.

**Example**

```scala
import java.net.URL

import com.github.api.v4.ListProjectMembershipsQuery
import com.codacy.graphql.apollo.client.ScalaApolloClient

import scala.collection.JavaConverters._
import scala.compat.java8.OptionConverters._
import scala.concurrent.duration._

val client = ScalaApolloClient(new URL("https://api.example.com/graphql"))
// val client = ScalaApolloClient(new URL("https://api.example.com/graphql"), okHttpClient)
// val client = ScalaApolloClient(apolloClient)

val queryMemberships = ListProjectMembershipsQuery
  .builder()
  .ownerName("codacy")
  .repositoryName("codacy-eslint")
  .build()
val result = client.execute(queryMemberships)

val data = result.map { res =>
    for {
      data <- res.data().asScala
      repositoryEntries <- data.repositoryEntries().asScala
      collaborators <- repositoryEntries.collaborators().asScala
      nodes <- collaborators.nodes().asScala.map(_.asScala)
    } yield nodes.map(c => (c.databaseId().asScala, c.login(), Option(c.email()).filter(_.nonEmpty)))
}
```

## FAQ

1. How to add a new query?
    * Create file in `src/main/graphql/com/github/api/v4` with the query
    * Check if any new scalar is needed.
    Look in `com.github.api.v4.type.CustomType` if all the types there already have CustomTypeAdapter
    that is linked in `com.codacy.graphql.apollo.client.ApolloClient` (look for a `addCustomTypeAdapter`)

2. How to add a CustomTypeAdapter?
    * Check what is the original type of the scalar in the `src/main/graphql/com/github/api/v4/schema.graphql` (e.g.: Date in a string format, long, ...)
    * Check package `com.codacy.graphql.adapter` for examples and possible implementations for existing API types
    * Add invocation of `addCustomTypeAdapter` when creating the `com.codacy.graphql.apollo.client.ApolloClient`

3. How to handle `Inline Fragments`?
    * The visitor provided by the generator should help but for some reason it is not being generated. 
    For now, since they can be of multiple types you need to check the instance type and to cast :(

## What is Codacy

[Codacy](https://www.codacy.com/) is an Automated Code Review Tool that monitors your technical debt, helps you improve your code quality, teaches best practices to your developers, and helps you save time in Code Reviews.

### Among Codacy’s features

- Identify new Static Analysis issues
- Commit and Pull Request Analysis with GitHub, BitBucket/Stash, GitLab (and also direct git repositories)
- Auto-comments on Commits and Pull Requests
- Integrations with Slack, HipChat, Jira, YouTrack
- Track issues Code Style, Security, Error Proneness, Performance, Unused Code and other categories

Codacy also helps keep track of Code Coverage, Code Duplication, and Code Complexity.

Codacy supports PHP, Python, Ruby, Java, JavaScript, and Scala, among others.

### Free for Open Source

Codacy is free for Open Source projects.
