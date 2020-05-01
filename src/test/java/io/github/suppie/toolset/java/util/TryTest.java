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

import io.github.suppie.toolset.java.util.function.ThrowableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TryTest {
    private static final Try<String> SUCCESS_SAMPLE = Try.success("");
    private static final Try<String> FAILURE_SAMPLE = Try.failure(new IllegalStateException());

    @Test
    void assertCreationUsingSupplier() {
        Try<String> nullSupplier = Try.of(null);
        assertFalse(nullSupplier.isSuccess(), "Try for null supplier must return 'false' for #isSuccess()");
        assertTrue(nullSupplier.isFailure(), "Try for null supplier must return 'true' for #isFailure()");
        assertThrows(NullPointerException.class, nullSupplier::get, "Exception must be of type NullPointerException");

        Try<String> nullValueSupplier = Try.of(() -> null);
        assertFalse(nullValueSupplier.isSuccess(), "Try for supplied null value must return 'false' for #isSuccess()");
        assertTrue(nullValueSupplier.isFailure(), "Try for supplied null value must return 'true' for #isFailure()");
        assertThrows(NullPointerException.class, nullValueSupplier::get, "Exception must be of type NullPointerException");

        Try<String> throwableSupplier = Try.of(() -> {
            throw new IllegalStateException();
        });
        assertFalse(throwableSupplier.isSuccess());
        assertTrue(throwableSupplier.isFailure());
        assertThrows(IllegalStateException.class, throwableSupplier::get);

        Try<String> success = Try.of(() -> "");
        assertTrue(success.isSuccess());
        assertFalse(success.isFailure());
        assertEquals("", success.get());
    }

    @Test
    void assertCreationUsingOptional() {
        Try<String> nullOptional = Try.fromOptional(null);
        assertFalse(nullOptional.isSuccess());
        assertTrue(nullOptional.isFailure());
        assertThrows(NullPointerException.class, nullOptional::get);

        Try<String> nullValueSupplier = Try.fromOptional(Optional.empty());
        assertFalse(nullValueSupplier.isSuccess());
        assertTrue(nullValueSupplier.isFailure());
        assertThrows(NoSuchElementException.class, nullValueSupplier::get);

        Try<String> success = Try.fromOptional(Optional.of(""));
        assertTrue(success.isSuccess());
        assertFalse(success.isFailure());
        assertEquals("", success.get());
    }

    @Test
    void assertCreationUsingDirectApproach() {
        assertThrows(NullPointerException.class, () -> Try.success(null));

        Try<String> correctSuccessValue = Try.success("");
        assertTrue(correctSuccessValue.isSuccess());
        assertFalse(correctSuccessValue.isFailure());
        assertEquals("", correctSuccessValue.get());

        assertThrows(NullPointerException.class, () -> Try.failure(null));

        Try<String> correctFailureValue = Try.failure(new IllegalStateException());
        assertFalse(correctFailureValue.isSuccess());
        assertTrue(correctFailureValue.isFailure());
        assertThrows(IllegalStateException.class, correctFailureValue::get);
    }

    @Test
    void assertConditionalExecutionDependingOnValuePresence() {
        Try<String> success = Try.success("");
        success.ifSuccess(s -> assertEquals("", s));
        success.ifFailure(throwable -> fail());

        Try<String> failure = Try.failure(new IllegalStateException());
        failure.ifSuccess(s -> fail());
        failure.ifFailure(throwable -> assertEquals(IllegalStateException.class, throwable.getClass()));
    }

    @Test
    void assertFiltering() {
        String result = Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.filter(s -> s.equals("")).get());
        assertEquals("", result);

        assertThrows(Exception.class, () -> SUCCESS_SAMPLE.filter(s -> {
            throw new Exception();
        }).get());

        assertThrows(IllegalStateException.class, () -> FAILURE_SAMPLE.filter(s -> s.equals("")).get());

        assertThrows(IllegalStateException.class, () -> FAILURE_SAMPLE.filter(s -> {
            throw new IllegalArgumentException();
        }).get());
    }

    @Test
    void assertMapping() {
        String result = Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.map(ThrowableFunction.identity()).get());
        assertEquals("", result);

        assertThrows(Exception.class, () -> SUCCESS_SAMPLE.map(s -> {
            throw new Exception();
        }).get());

        assertThrows(IllegalStateException.class, () -> FAILURE_SAMPLE.map(ThrowableFunction.identity()).get());

        assertThrows(IllegalStateException.class, () -> FAILURE_SAMPLE.map(s -> {
            throw new IllegalArgumentException();
        }).get());
    }

    @Test
    void assertFlatMapping() {
        String result = Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.flatMap(Try::success).get());
        assertEquals("", result);

        assertThrows(Exception.class, () -> SUCCESS_SAMPLE.flatMap(s -> {
            throw new Exception();
        }).get());

        assertThrows(IllegalStateException.class, () -> FAILURE_SAMPLE.flatMap(Try::success).get());

        assertThrows(IllegalStateException.class, () -> FAILURE_SAMPLE.flatMap(s -> {
            throw new IllegalArgumentException();
        }).get());
    }

    @Test
    void assertFlatMappingToOppositeTryType() {
        assertFalse(SUCCESS_SAMPLE.flatMap(s -> FAILURE_SAMPLE).isSuccess());
        assertTrue(SUCCESS_SAMPLE.flatMap(s -> FAILURE_SAMPLE).isFailure());

        assertFalse(FAILURE_SAMPLE.flatMap(s -> SUCCESS_SAMPLE).isSuccess());
        assertTrue(FAILURE_SAMPLE.flatMap(s -> SUCCESS_SAMPLE).isFailure());
    }

    @Test
    void assertConversionToOptional() {
        assertTrue(SUCCESS_SAMPLE.toOptional().isPresent());
        assertFalse(FAILURE_SAMPLE.toOptional().isPresent());
    }

    @Test
    void assertRecoveryUsingValue() {
        final String substitute = "substitute";

        assertNotEquals(substitute, SUCCESS_SAMPLE.orElse(substitute));
        assertEquals(substitute, FAILURE_SAMPLE.orElse(substitute));
    }

    @Test
    void assertRecoveryUsingSupplier() {
        final String substitute = "substitute";

        assertNotEquals(substitute, SUCCESS_SAMPLE.orElseGet(() -> substitute));
        assertEquals(substitute, FAILURE_SAMPLE.orElseGet(() -> substitute));
        assertThrows(NullPointerException.class, () -> FAILURE_SAMPLE.orElseGet(null));
    }
}
