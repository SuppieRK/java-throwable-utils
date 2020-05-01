/*
 * MIT License
 *
 * Copyright (c) 2020 Roman Khlebnov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.suppie.toolset.java.util;

import io.github.suppie.toolset.java.util.function.ThrowableBiConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableBiFunction;
import io.github.suppie.toolset.java.util.function.ThrowableBiPredicate;
import io.github.suppie.toolset.java.util.function.ThrowableBinaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableBooleanSupplier;
import io.github.suppie.toolset.java.util.function.ThrowableConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleBinaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleFunction;
import io.github.suppie.toolset.java.util.function.ThrowableDoublePredicate;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleSupplier;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleToIntFunction;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleToLongFunction;
import io.github.suppie.toolset.java.util.function.ThrowableDoubleUnaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableFunction;
import io.github.suppie.toolset.java.util.function.ThrowableIntBinaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableIntConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableIntFunction;
import io.github.suppie.toolset.java.util.function.ThrowableIntPredicate;
import io.github.suppie.toolset.java.util.function.ThrowableIntSupplier;
import io.github.suppie.toolset.java.util.function.ThrowableIntToDoubleFunction;
import io.github.suppie.toolset.java.util.function.ThrowableIntToLongFunction;
import io.github.suppie.toolset.java.util.function.ThrowableIntUnaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableLongBinaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableLongConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableLongFunction;
import io.github.suppie.toolset.java.util.function.ThrowableLongPredicate;
import io.github.suppie.toolset.java.util.function.ThrowableLongSupplier;
import io.github.suppie.toolset.java.util.function.ThrowableLongToDoubleFunction;
import io.github.suppie.toolset.java.util.function.ThrowableLongToIntFunction;
import io.github.suppie.toolset.java.util.function.ThrowableLongUnaryOperator;
import io.github.suppie.toolset.java.util.function.ThrowableObjDoubleConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableObjIntConsumer;
import io.github.suppie.toolset.java.util.function.ThrowableObjLongConsumer;
import io.github.suppie.toolset.java.util.function.ThrowablePredicate;
import io.github.suppie.toolset.java.util.function.ThrowableSupplier;
import io.github.suppie.toolset.java.util.function.ThrowableToDoubleBiFunction;
import io.github.suppie.toolset.java.util.function.ThrowableToDoubleFunction;
import io.github.suppie.toolset.java.util.function.ThrowableToIntBiFunction;
import io.github.suppie.toolset.java.util.function.ThrowableToIntFunction;
import io.github.suppie.toolset.java.util.function.ThrowableToLongBiFunction;
import io.github.suppie.toolset.java.util.function.ThrowableToLongFunction;
import io.github.suppie.toolset.java.util.function.ThrowableUnaryOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThrowableFunctionsCallTest {
    private static final Exception EXCEPTION = new Exception();

    @ParameterizedTest
    @MethodSource("executables")
    void assertThatFunctionCanBeCalled(Executable expectedToFail) {
        assertThrows(Exception.class, expectedToFail, "Failed to do call returning exception");
    }

    @Test
    void assertThatThrowableIdentityWorksAsExpected() {
        String test = "test";
        Assertions.assertEquals(.0, ThrowableDoubleUnaryOperator.identity().applyAsDouble(.0), "Unable to use identity");
        Assertions.assertEquals(0, ThrowableIntUnaryOperator.identity().applyAsInt(0), "Unable to use identity");
        Assertions.assertEquals(0L, ThrowableLongUnaryOperator.identity().applyAsLong(0L), "Unable to use identity");
        Assertions.assertEquals(test, ThrowableUnaryOperator.identity().apply(test), "Unable to use identity");
    }

    @Test
    void assertThatThrowablePredicateIsEqualWorksAsExpected() {
        String test = "test";
        Assertions.assertTrue(ThrowablePredicate.isEqual(test).test(test));
        assertTrue(ThrowablePredicate.isEqual(null).test(null));
        assertFalse(ThrowablePredicate.isEqual(test).test(test + test));
        assertFalse(ThrowablePredicate.isEqual(null).test(test));
        assertFalse(ThrowablePredicate.isEqual(test).test(null));
    }

    static Arguments arg(Executable executable) {
        return Arguments.of(executable);
    }

    static Stream<Arguments> executables() {
        return Stream.of(
                arg(() -> ((ThrowableBiConsumer<String, String>) (s, s2) -> {
                    throw EXCEPTION;
                }).accept(null, null)),
                arg(() -> ((ThrowableBiFunction<String, String, String>) (s, s2) -> {
                    throw EXCEPTION;
                }).apply(null, null)),
                arg(() -> ThrowableBinaryOperator.minBy((ThrowableComparator<String>) (o1, o2) -> {
                    throw EXCEPTION;
                }).apply(null, null)),
                arg(() -> ThrowableBinaryOperator.maxBy((ThrowableComparator<String>) (o1, o2) -> {
                    throw EXCEPTION;
                }).apply(null, null)),
                arg(() -> ((ThrowableBiPredicate<String, String>) (s, s2) -> {
                    throw EXCEPTION;
                }).test(null, null)),
                arg(((ThrowableBooleanSupplier) () -> {
                    throw EXCEPTION;
                })::getAsBoolean),
                arg(() -> ((ThrowableConsumer<String>) s -> {
                    throw EXCEPTION;
                }).accept(null)),
                arg(() -> ((ThrowableDoubleBinaryOperator) (left, right) -> {
                    throw EXCEPTION;
                }).applyAsDouble(.0, .0)),
                arg(() -> ((ThrowableDoubleConsumer) value -> {
                    throw EXCEPTION;
                }).accept(.0)),
                arg(() -> ((ThrowableDoubleFunction<String>) value -> {
                    throw EXCEPTION;
                }).apply(.0)),
                arg(() -> ((ThrowableDoublePredicate) value -> {
                    throw EXCEPTION;
                }).test(.0)),
                arg(((ThrowableDoubleSupplier) () -> {
                    throw EXCEPTION;
                })::getAsDouble),
                arg(() -> ((ThrowableDoubleToIntFunction) value -> {
                    throw EXCEPTION;
                }).applyAsInt(.0)),
                arg(() -> ((ThrowableDoubleToLongFunction) value -> {
                    throw EXCEPTION;
                }).applyAsLong(.0)),
                arg(() -> ((ThrowableDoubleUnaryOperator) operand -> {
                    throw EXCEPTION;
                }).applyAsDouble(.0)),
                arg(() -> ((ThrowableFunction<String, String>) s -> {
                    throw EXCEPTION;
                }).apply(null)),
                arg(() -> ((ThrowableIntBinaryOperator) (left, right) -> {
                    throw EXCEPTION;
                }).applyAsInt(0, 0)),
                arg(() -> ((ThrowableIntConsumer) value -> {
                    throw EXCEPTION;
                }).accept(0)),
                arg(() -> ((ThrowableIntFunction<String>) value -> {
                    throw EXCEPTION;
                }).apply(0)),
                arg(() -> ((ThrowableIntPredicate) value -> {
                    throw EXCEPTION;
                }).test(0)),
                arg(((ThrowableIntSupplier) () -> {
                    throw EXCEPTION;
                })::getAsInt),
                arg(() -> ((ThrowableIntToDoubleFunction) value -> {
                    throw EXCEPTION;
                }).applyAsDouble(0)),
                arg(() -> ((ThrowableIntToLongFunction) value -> {
                    throw EXCEPTION;
                }).applyAsLong(0)),
                arg(() -> ((ThrowableIntUnaryOperator) operand -> {
                    throw EXCEPTION;
                }).applyAsInt(0)),
                arg(() -> ((ThrowableLongBinaryOperator) (left, right) -> {
                    throw EXCEPTION;
                }).applyAsLong(0L, 0L)),
                arg(() -> ((ThrowableLongConsumer) value -> {
                    throw EXCEPTION;
                }).accept(0L)),
                arg(() -> ((ThrowableLongFunction<String>) value -> {
                    throw EXCEPTION;
                }).apply(0L)),
                arg(() -> ((ThrowableLongPredicate) value -> {
                    throw EXCEPTION;
                }).test(0L)),
                arg(((ThrowableLongSupplier) () -> {
                    throw EXCEPTION;
                })::getAsLong),
                arg(() -> ((ThrowableLongToDoubleFunction) value -> {
                    throw EXCEPTION;
                }).applyAsDouble(0L)),
                arg(() -> ((ThrowableLongToIntFunction) value -> {
                    throw EXCEPTION;
                }).applyAsInt(0L)),
                arg(() -> ((ThrowableLongUnaryOperator) operand -> {
                    throw EXCEPTION;
                }).applyAsLong(0L)),
                arg(() -> ((ThrowableObjDoubleConsumer<String>) (s, value) -> {
                    throw EXCEPTION;
                }).accept(null, .0)),
                arg(() -> ((ThrowableObjIntConsumer<String>) (s, value) -> {
                    throw EXCEPTION;
                }).accept(null, 0)),
                arg(() -> ((ThrowableObjLongConsumer<String>) (s, value) -> {
                    throw EXCEPTION;
                }).accept(null, 0L)),
                arg(() -> ((ThrowablePredicate<String>) s -> {
                    throw EXCEPTION;
                }).test(null)),
                arg(((ThrowableSupplier<String>) () -> {
                    throw EXCEPTION;
                })::get),
                arg(() -> ((ThrowableToDoubleBiFunction<String, String>) (s, s2) -> {
                    throw EXCEPTION;
                }).applyAsDouble(null, null)),
                arg(() -> ((ThrowableToDoubleFunction<String>) value -> {
                    throw EXCEPTION;
                }).applyAsDouble(null)),
                arg(() -> ((ThrowableToIntBiFunction<String, String>) (s, s2) -> {
                    throw EXCEPTION;
                }).applyAsInt(null, null)),
                arg(() -> ((ThrowableToIntFunction<String>) value -> {
                    throw EXCEPTION;
                }).applyAsInt(null)),
                arg(() -> ((ThrowableToLongBiFunction<String, String>) (s, s2) -> {
                    throw EXCEPTION;
                }).applyAsLong(null, null)),
                arg(() -> ((ThrowableToLongFunction<String>) value -> {
                    throw EXCEPTION;
                }).applyAsLong(null)),
                arg(() -> ((ThrowableComparator<String>) (o1, o2) -> {
                    throw EXCEPTION;
                }).compare(null, null))
        );
    }
}
