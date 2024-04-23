/*
 * MIT License
 *
 * Copyright (c) 2023 Roman Khlebnov
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

package io.github.suppierk.java.util;

import static org.junit.jupiter.api.Assertions.*;

import io.github.suppierk.java.util.function.ThrowableFunction;
import io.github.suppierk.java.util.function.ThrowableSupplier;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TryTest {
  private static final Try<String> SUCCESS_SAMPLE = Try.success("");
  private static final Try<String> FAILURE_SAMPLE = Try.failure(new IllegalStateException());

  @Test
  void assertGet() {
    assertDoesNotThrow(SUCCESS_SAMPLE::get);
    assertThrows(IllegalStateException.class, FAILURE_SAMPLE::get);
  }

  @Test
  void assertCreationUsingSupplier() {
    Try<String> nullSupplier = Try.of(null);
    assertFalse(
        nullSupplier.isSuccess(), "Try for null supplier must return 'false' for #isSuccess()");
    assertTrue(
        nullSupplier.isFailure(), "Try for null supplier must return 'true' for #isFailure()");
    assertThrows(
        NullPointerException.class,
        nullSupplier::get,
        "Exception must be of type NullPointerException");

    Try<String> nullValueSupplier = Try.of(() -> null);
    assertTrue(
        nullValueSupplier.isSuccess(),
        "Try for supplied null value must return 'false' for #isSuccess()");
    assertFalse(
        nullValueSupplier.isFailure(),
        "Try for supplied null value must return 'true' for #isFailure()");
    assertNull(nullValueSupplier.get(), "Successful value should be null");

    Try<String> throwableSupplier =
        Try.of(
            () -> {
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
    assertDoesNotThrow(() -> Try.success(null));

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
    Try<String> successSuccessFilter =
        Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.filter(s -> s.equals("")));
    assertEquals("", successSuccessFilter.get());

    Try<String> successNegativeFilter =
        Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.filter(s -> !s.equals("")));
    assertThrows(NoSuchElementException.class, successNegativeFilter::get);

    Try<String> successFailedFilter =
        Assertions.assertDoesNotThrow(
            () ->
                SUCCESS_SAMPLE.filter(
                    s -> {
                      throw new Exception();
                    }));
    assertThrows(Exception.class, successFailedFilter::get);

    Try<String> failureSuccessFilter =
        Assertions.assertDoesNotThrow(() -> FAILURE_SAMPLE.filter(s -> s.equals("")));
    assertThrows(IllegalStateException.class, failureSuccessFilter::get);

    Try<String> failureFailedFilter =
        Assertions.assertDoesNotThrow(
            () ->
                FAILURE_SAMPLE.filter(
                    s -> {
                      throw new IllegalArgumentException();
                    }));
    assertThrows(IllegalStateException.class, failureFailedFilter::get);
  }

  @Test
  @SuppressWarnings("squid:S5578")
  void assertMapping() {
    String result =
        Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.map(ThrowableFunction.identity()).get());
    assertEquals("", result);

    assertThrows(
        Exception.class,
        () ->
            SUCCESS_SAMPLE
                .map(
                    s -> {
                      throw new Exception();
                    })
                .get());

    Try<String> mappedToItself = FAILURE_SAMPLE.map(ThrowableFunction.identity());
    assertThrows(IllegalStateException.class, mappedToItself::get);

    Try<Object> mappedToException =
        FAILURE_SAMPLE.map(
            s -> {
              throw new IllegalArgumentException();
            });
    assertThrows(IllegalStateException.class, mappedToException::get);
  }

  @Test
  @SuppressWarnings("squid:S5578")
  void assertFlatMapping() {
    String result = Assertions.assertDoesNotThrow(() -> SUCCESS_SAMPLE.flatMap(Try::success).get());
    assertEquals("", result);

    assertThrows(
        Exception.class,
        () ->
            SUCCESS_SAMPLE
                .flatMap(
                    s -> {
                      throw new Exception();
                    })
                .get());

    Try<String> mappedToSuccess = FAILURE_SAMPLE.flatMap(Try::success);
    assertThrows(IllegalStateException.class, mappedToSuccess::get);

    Try<Object> mappedToException =
        FAILURE_SAMPLE.flatMap(
            s -> {
              throw new IllegalArgumentException();
            });
    assertThrows(IllegalStateException.class, mappedToException::get);
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

    assertNotEquals(substitute, SUCCESS_SAMPLE.orElse(() -> substitute));
    assertEquals(substitute, FAILURE_SAMPLE.orElse(() -> substitute));
    assertThrows(NullPointerException.class, () -> FAILURE_SAMPLE.orElse((Supplier<String>) null));
  }

  @Test
  void assertTryComposition() {
    final String first = "first";
    final String second = "second";

    ThrowableSupplier<String> successOne = () -> first;
    ThrowableSupplier<String> successTwo = () -> second;
    ThrowableSupplier<String> failure =
        () -> {
          throw new IllegalStateException();
        };

    Try<String> otf = Try.of(successOne).orElseTry(successTwo).orElseTry(failure);
    Try<String> oft = Try.of(successOne).orElseTry(failure).orElseTry(successTwo);
    Try<String> tof = Try.of(successTwo).orElseTry(successOne).orElseTry(failure);
    Try<String> tfo = Try.of(successTwo).orElseTry(failure).orElseTry(successOne);
    Try<String> fot = Try.of(failure).orElseTry(successOne).orElseTry(successTwo);
    Try<String> fto = Try.of(failure).orElseTry(successTwo).orElseTry(successOne);

    assertEquals(first, assertDoesNotThrow(otf::get));
    assertEquals(first, assertDoesNotThrow(oft::get));
    assertEquals(second, assertDoesNotThrow(tof::get));
    assertEquals(second, assertDoesNotThrow(tfo::get));
    assertEquals(first, assertDoesNotThrow(fot::get));
    assertEquals(second, assertDoesNotThrow(fto::get));

    assertThrows(NullPointerException.class, () -> SUCCESS_SAMPLE.orElseTry(null));
    assertThrows(NullPointerException.class, () -> FAILURE_SAMPLE.orElseTry(null));
  }
}
