# Java Throwable Utils

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils?ref=badge_shield)

This dependency-less library serves for one simple purpose: 
reduce boilerplate try-catch statements during work with Java Stream API.

## How to add

- Maven
```xml
<dependency>
    <groupId>io.github.suppierk</groupId>
    <artifactId>java-throwable-utils</artifactId>
    <version>1.0.3</version>
</dependency>
```

- Gradle
```groovy
dependencies {
    implementation 'io.github.suppierk:java-throwable-utils:1.0.3'
}
```

## Examples

If you had to use constructs like:

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
                            // Here we would have to:
                            // a) Return some value to filter out later and log exception
                            // b) Wrap and rethrow an exception to catch it later again
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(s -> {
                        try {
                            throwingMethod(s);
                        } catch (Exception e) {
                            // Here we would have to:
                            // a) Suppress and log exception
                            // b) Wrap and rethrow an exception to catch it later again
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
``` 

with this library, you can simplify this pipeline to:

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

and you can take it further and define functions explicitly, removing the need to specify function types:

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

All exceptions will be propagated using neat trick similar to Apache Commons `ExceptionUtils.rethrow` by leveraging Java type erasure to make checked exceptions unchecked.

## Try

This library has simple implementation of `Try`, 
which benefits greatly from presence of these functions 
and allows us to handle exceptions in functional style much like you deal with nullable values using `Optional`.

```java
import io.github.suppierk.java.util.Try;

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

Same as for `Optional`, `Try` in a case of failure will preserve only first exception happened in a call chain and skip further operations.

## License
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FSuppieRK%2Fjava-throwable-utils?ref=badge_large)