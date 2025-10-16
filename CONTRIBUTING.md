# Contributing

Thank you for improving `java-throwable-utils`! This guide outlines the bits of project hygiene that help us ship
tiny-but-reliable releases.

## Prerequisites

- JDK 17 is used for primary development and CI. The published artifacts remain Java 8 compatible, so feel free to
  validate on older JDKs if your change touches bytecode-sensitive areas.
- The Gradle wrapper (`./gradlew`) drives all builds; no additional plugins or package managers are required.

## Local build & test workflow

1. Bootstrap and compile everything:
   ```bash
   ./gradlew build
   ```
   This runs Spotless checks, compiles sources, executes tests, and produces coverage.
2. Run just the unit tests when iterating quickly:
   ```bash
   ./gradlew test
   ```
3. Generate local reports if you need them:
   ```bash
   ./gradlew jacocoTestReport
   ```

## Code style

- Spotless with Google Java Format guards the Java sources. Let the pre-configured Gradle task fix formatting
  automatically:
  ```bash
  ./gradlew spotlessApply
  ```
- Source and target compatibility are locked to Java 8 (`1.8`). Avoid introducing APIs that require newer language
  features unless accompanied by a deliberate major-version plan.

## Releasing

- Creating a Git tag triggers the [`publish.yml`](.github/workflows/publish.yml) workflow, which builds the artifacts
  and deploys them to Maven Central.
- Ensure `./gradlew build` is green on the default branch before tagging.
- Update `CHANGELOG.md` and bump the version in `gradle.properties` as part of your release PR.

## Opening issues & pull requests

- Include the Java version(s) you used along with a minimal reproduction when reporting bugs.
- For feature requests, describe the motivating use case and expected ergonomics; small reproducer snippets are ideal.
- Keep PRs focused—tooling updates, behavioural changes, and documentation refreshes are easier to review separately.
- All contributions require at least one passing GitHub Actions run; re-run the build workflow if you amend commits.

We appreciate every improvement, from typo fixes to new APIs. Thanks for helping keep exception handling ergonomic!
