# codacy-github-graphql

Library with Apollo compiled queries and models for GitHub API V4

## Build

```sh
./gradlew build
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
