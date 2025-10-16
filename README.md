# Java Throwable Utils

[![Build status](https://github.com/SuppieRK/java-throwable-utils/actions/workflows/build.yml/badge.svg)](https://github.com/SuppieRK/java-throwable-utils/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.suppierk/java-throwable-utils.svg)](https://search.maven.org/artifact/io.github.suppierk/java-throwable-utils)
[![Javadoc](https://javadoc.io/badge2/io.github.suppierk/java-throwable-utils/javadoc.svg)](https://javadoc.io/doc/io.github.suppierk/java-throwable-utils)
[![SonarCloud Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=SuppieRK_java-throwable-utils&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=SuppieRK_java-throwable-utils)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=SuppieRK_java-throwable-utils&metric=coverage)](https://sonarcloud.io/summary/new_code?id=SuppieRK_java-throwable-utils)
[![SonarCloud Maintainability](https://sonarcloud.io/api/project_badges/measure?project=SuppieRK_java-throwable-utils&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=SuppieRK_java-throwable-utils)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils?ref=badge_shield)

This dependency-less library serves for one simple purpose: reduce boilerplate try-catch statements when using the Java
Stream API and functional interfaces.

## How to add

- Maven

```xml
<dependency>
    <groupId>io.github.suppierk</groupId>
    <artifactId>java-throwable-utils</artifactId>
    <version>2.0.2</version>
</dependency>
```

- Gradle

```groovy
dependencies {
    implementation("io.github.suppierk:java-throwable-utils:2.0.2")
}
```

## Compatibility

| Topic                    | Details                                                                                                                                             |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| Source and target levels | Compiled with `sourceCompatibility = 1.8` / `targetCompatibility = 1.8`. Binaries remain compatible with Java 8, 11, 17, and newer runtimes.        |
| CI coverage              | GitHub Actions (`build.yml`) runs `./gradlew build sonar` on Temurin 17. Local spot-checks are encouraged on Java 8 or 11 when developing new APIs. |
| Runtime requirements     | No third-party dependencies; artifacts are verified to run on Java 8+.                                                                              |

## Examples

If you previously had to write boilerplate like this:

```java
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static String throwingMethod(String source) throws Exception {
        throw new Exception(source);
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("sample");

        try {
            test.stream()
                    .map(s -> {
                        try {
                            return throwingMethod(s);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(s -> {
                        try {
                            throwingMethod(s);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

with this library, you can simplify the pipeline to:

```java
import io.github.suppierk.java.util.function.*;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static String throwingMethod(String source) throws Exception {
        throw new Exception(source);
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("sample");

        try {
            test.stream()
                    .map((ThrowableFunction<String, String>) Demo::throwingMethod)
                    .forEach((ThrowableConsumer<String>) Demo::throwingMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

You can take it further and define functions explicitly, removing the need to specify function types at the call site:

```java
import io.github.suppierk.java.util.function.*;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static ThrowableFunction<String, String> throwingMap() {
        return source -> {
            throw new Exception(source);
        };
    }

    public static ThrowableConsumer<String> throwingConsumer() {
        return source -> {
            throw new Exception(source);
        };
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("sample");

        try {
            test.stream().map(Demo.throwingMap()).forEach(Demo.throwingConsumer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Or, with the help of `UnsafeFunctions`, shorten the code without changing your logic too much. This is the recommended,
least intrusive, and least verbose way:

```java
import static io.github.suppierk.java.UnsafeFunctions.*;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static String throwingMethod(String source) throws Exception {
        throw new Exception(source);
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("sample");

        try {
            test.stream()
                    .map(unsafeFunction(Demo::throwingMethod))
                    .forEach(unsafeConsumer(Demo::throwingMethod));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Try

This library ships a simple implementation of `Try`, which benefits greatly from the throwable functional interfaces and
enables functional-style exception handling similar to how `Optional` deals with nullable values.

```java
import io.github.suppierk.java.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public String throwingMethod(String source) throws Exception {
        throw new Exception(source);
    }

    public void processingMethod() {
        List<String> test = new ArrayList<>();

        test.stream()
                .map(s -> Try.of(() -> throwingMethod(s)))
                .filter(Try::isSuccess)
                .collect(Collectors.toList());
    }
}
```

Like `Optional`, `Try` in a case of failure preserves only the first exception in a call chain and skips further
operations.

### Composing `Try` with recovery

```java
import io.github.suppierk.java.Try;
import io.github.suppierk.java.util.function.ThrowableFunction;

class TryDemo {
    private static String toUpper(String value) throws Exception {
        if (value == null) {
            throw new Exception("missing value");
        }
        return value.toUpperCase();
    }

    static String recoverablePipeline(String input) {
        return Try.of(() -> input)
                .filter(s -> !s.isEmpty())
                .map((ThrowableFunction<String, String>) TryDemo::toUpper)
                .orElseTry(() -> "fallback")
                .orElse("UNKNOWN");
    }
}
```

The first failing stage short-circuits the rest of the pipeline; `orElseTry` lets you provide an alternate computation
and `orElse` finally retrieves the value with a default.

### How exceptions are propagated

Every throwable functional interface delegates to `ExceptionSuppressor.asUnchecked(Throwable)`, which relies on Java's
type erasure trick to rethrow checked exceptions without wrapping them. This keeps the original exception type and stack
trace intact.

`Try` captures only the first failure in a chain. Once a `Try` instance enters the failed state:

- Subsequent `map`, `flatMap`, or `filter` calls are skipped.
- Additional exceptions are **not** attached as suppressed exceptions.
- `Try.get()` rethrows the stored exception as unchecked, preserving the original cause for diagnostics.

## Changelog

Changes are tracked in [CHANGELOG.md](CHANGELOG.md).

## License

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils?ref=badge_large)
