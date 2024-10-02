package io.github.suppierk.java;

import static io.github.suppierk.java.UnsafeFunctions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.suppierk.java.util.function.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UnsafeFunctionsTest {
  private static final Exception EXCEPTION = new Exception();

  @ParameterizedTest
  @MethodSource("executables")
  void assertThatFunctionCanBeCalled(Executable expectedToFail) {
    assertThrows(Exception.class, expectedToFail, "Failed to do call returning exception");
  }

  static Arguments arg(Executable executable) {
    return Arguments.of(executable);
  }

  static Stream<Arguments> executables() {
    return Stream.of(
        arg(
            () ->
                unsafeRunnable(
                        () -> {
                          throw EXCEPTION;
                        })
                    .run()),
        arg(
            () ->
                unsafeBiConsumer(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .accept(null, null)),
        arg(
            () ->
                unsafeBiFunction(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .apply(null, null)),
        arg(
            () ->
                unsafeBinaryOperator(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .apply(null, null)),
        arg(
            () ->
                ThrowableBinaryOperator.minBy(
                        unsafeComparator(
                            (o1, o2) -> {
                              throw EXCEPTION;
                            }))
                    .apply(null, null)),
        arg(
            () ->
                ThrowableBinaryOperator.maxBy(
                        unsafeComparator(
                            (o1, o2) -> {
                              throw EXCEPTION;
                            }))
                    .apply(null, null)),
        arg(
            () ->
                unsafeBiPredicate(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .test(null, null)),
        arg(
            unsafeBooleanSupplier(
                    () -> {
                      throw EXCEPTION;
                    })
                ::getAsBoolean),
        arg(
            () ->
                unsafeConsumer(
                        s -> {
                          throw EXCEPTION;
                        })
                    .accept(null)),
        arg(
            () ->
                unsafeDoubleBinaryOperator(
                        (left, right) -> {
                          throw EXCEPTION;
                        })
                    .applyAsDouble(.0, .0)),
        arg(
            () ->
                unsafeDoubleConsumer(
                        value -> {
                          throw EXCEPTION;
                        })
                    .accept(.0)),
        arg(
            () ->
                unsafeDoubleFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .apply(.0)),
        arg(
            () ->
                unsafeDoublePredicate(
                        value -> {
                          throw EXCEPTION;
                        })
                    .test(.0)),
        arg(
            unsafeDoubleSupplier(
                    () -> {
                      throw EXCEPTION;
                    })
                ::getAsDouble),
        arg(
            () ->
                unsafeDoubleToIntFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsInt(.0)),
        arg(
            () ->
                unsafeDoubleToLongFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsLong(.0)),
        arg(
            () ->
                unsafeDoubleUnaryOperator(
                        operand -> {
                          throw EXCEPTION;
                        })
                    .applyAsDouble(.0)),
        arg(
            () ->
                unsafeFunction(
                        s -> {
                          throw EXCEPTION;
                        })
                    .apply(null)),
        arg(
            () ->
                unsafeIntBinaryOperator(
                        (left, right) -> {
                          throw EXCEPTION;
                        })
                    .applyAsInt(0, 0)),
        arg(
            () ->
                unsafeIntConsumer(
                        value -> {
                          throw EXCEPTION;
                        })
                    .accept(0)),
        arg(
            () ->
                unsafeIntFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .apply(0)),
        arg(
            () ->
                unsafeIntPredicate(
                        value -> {
                          throw EXCEPTION;
                        })
                    .test(0)),
        arg(
            unsafeIntSupplier(
                    () -> {
                      throw EXCEPTION;
                    })
                ::getAsInt),
        arg(
            () ->
                unsafeIntToDoubleFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsDouble(0)),
        arg(
            () ->
                unsafeIntToLongFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsLong(0)),
        arg(
            () ->
                unsafeIntUnaryOperator(
                        operand -> {
                          throw EXCEPTION;
                        })
                    .applyAsInt(0)),
        arg(
            () ->
                unsafeLongBinaryOperator(
                        (left, right) -> {
                          throw EXCEPTION;
                        })
                    .applyAsLong(0L, 0L)),
        arg(
            () ->
                unsafeLongConsumer(
                        value -> {
                          throw EXCEPTION;
                        })
                    .accept(0L)),
        arg(
            () ->
                unsafeLongFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .apply(0L)),
        arg(
            () ->
                unsafeLongPredicate(
                        value -> {
                          throw EXCEPTION;
                        })
                    .test(0L)),
        arg(
            unsafeLongSupplier(
                    () -> {
                      throw EXCEPTION;
                    })
                ::getAsLong),
        arg(
            () ->
                unsafeLongToDoubleFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsDouble(0L)),
        arg(
            () ->
                unsafeLongToIntFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsInt(0L)),
        arg(
            () ->
                unsafeLongUnaryOperator(
                        operand -> {
                          throw EXCEPTION;
                        })
                    .applyAsLong(0L)),
        arg(
            () ->
                unsafeObjDoubleConsumer(
                        (s, value) -> {
                          throw EXCEPTION;
                        })
                    .accept(null, .0)),
        arg(
            () ->
                unsafeObjIntConsumer(
                        (s, value) -> {
                          throw EXCEPTION;
                        })
                    .accept(null, 0)),
        arg(
            () ->
                unsafeObjLongConsumer(
                        (s, value) -> {
                          throw EXCEPTION;
                        })
                    .accept(null, 0L)),
        arg(
            () ->
                unsafePredicate(
                        s -> {
                          throw EXCEPTION;
                        })
                    .test(null)),
        arg(
            unsafeSupplier(
                    () -> {
                      throw EXCEPTION;
                    })
                ::get),
        arg(
            () ->
                unsafeToDoubleBiFunction(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .applyAsDouble(null, null)),
        arg(
            () ->
                unsafeToDoubleFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsDouble(null)),
        arg(
            () ->
                unsafeToIntBiFunction(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .applyAsInt(null, null)),
        arg(
            () ->
                unsafeToIntFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsInt(null)),
        arg(
            () ->
                unsafeToLongBiFunction(
                        (s, s2) -> {
                          throw EXCEPTION;
                        })
                    .applyAsLong(null, null)),
        arg(
            () ->
                unsafeToLongFunction(
                        value -> {
                          throw EXCEPTION;
                        })
                    .applyAsLong(null)),
        arg(
            () ->
                unsafeComparator(
                        (o1, o2) -> {
                          throw EXCEPTION;
                        })
                    .compare(null, null)),
        arg(
            () ->
                unsafeUnaryOperator(
                        s -> {
                          throw EXCEPTION;
                        })
                    .apply(null)));
  }
}
