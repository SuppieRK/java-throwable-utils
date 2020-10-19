# Java Throwable Utils

[ ![Download](https://api.bintray.com/packages/suppierk/io.github.suppie.toolset/java-throwable-utils/images/download.svg) ](https://bintray.com/suppierk/io.github.suppie.toolset/java-throwable-utils/_latestVersion)

This dependency-less library serves for one simple purpose - allow developer to invoke methods throwing exceptions in Java 8 functional expressions / Stream API

## How to add

- Maven
```xml
<dependency>
    <groupId>com.github.suppie</groupId>
    <artifactId>java-throwable-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

- Gradle
```groovy
implementation 'com.github.suppie:java-throwable-utils:1.0.0'
```

## Examples

If previously you had to use constructs like:

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public void throwingMethod(String source) throws Exception {
        throw new Exception(source);
    }

    public void processingMethod() {
        List<String> test = new ArrayList<>();

        test.forEach(s -> {
            try {
                throwingMethod(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
``` 

with this library you can simplify this pipeline to:

```java
import com.github.suppie.java.util.function.ThrowableConsumer;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public void throwingMethod(String source) throws Exception {
        throw new Exception(source);
    }

    public void processingMethod() {
        List<String> test = new ArrayList<>();

        test.forEach((ThrowableConsumer<String>) this::throwingMethod);
    }
}
```

All exceptions will be propagated as is using neat trick similar to Apache Commons `ExceptionUtils.rethrow` by leveraging Java' type erasure to make checked exceptions unchecked.

## Try

This library has simple implementation of `Try` monad, which benefits greatly from presence of these functions and allows us to handle exceptions in functional style much like you deal with nullable values using `Optional`

```java
import com.github.suppie.java.util.Try;

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

Same as for `Optional`, `Try` in case of failure will preserve only first exception happened in call chain and skip further operations.