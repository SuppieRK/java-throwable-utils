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

package io.github.suppierk.java;

import io.github.suppierk.java.util.ExceptionSuppressor;
import io.github.suppierk.java.util.function.ThrowableConsumer;
import io.github.suppierk.java.util.function.ThrowableFunction;
import io.github.suppierk.java.util.function.ThrowablePredicate;
import io.github.suppierk.java.util.function.ThrowableSupplier;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A container object that contains either a value or exception.
 *
 * <p>If a value is present, {@link #isSuccess()} will return {@code true} and {@link #get()} will
 * return the value. If exception occurred, {@link #isFailure()} will return {@code true} and {@link
 * #get()} will return first occurred exception.
 *
 * <p>Additional methods that depend on the presence or absence of a contained value are provided,
 * such as {@link #orElse(java.lang.Object) orElse()} (return a default value if value not present)
 * and {@link #ifSuccess(ThrowableConsumer)} (execute a block of code if the value is present) or
 * {@link #ifFailure(ThrowableConsumer)} (execute a block of code if the exception is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a> class; use of
 * identity-sensitive operations (including reference equality ({@code ==}), identity hash code, or
 * synchronization) on instances of {@code Try} may have unpredictable results and should be
 * avoided.
 *
 * @param <T> the class of the value
 */
@SuppressWarnings("squid:S1181")
public interface Try<T> {
  /**
   * Returns a {@link Try} by invoking specified supplier.
   *
   * @param <T> the class of the value
   * @param supplier the supplier to retrieve the value
   * @return a {@link Try.Success} with the value retrieved successfully or {@link Try.Failure}
   */
  static <T> Try<T> of(ThrowableSupplier<T> supplier) {
    try {
      return success(Objects.requireNonNull(supplier).get());
    } catch (Throwable t) {
      return failure(t);
    }
  }

  /**
   * Returns a {@link Try} created from specified {@link Optional}.
   *
   * @param <T> the class of the value
   * @param optional the {@link Optional} to be present
   * @return a {@link Try.Success} with the value if it was present otherwise a {@link Try.Failure}
   *     with {@link NoSuchElementException}
   * @throws NullPointerException if optional is null
   */
  static <T> Try<T> fromOptional(Optional<T> optional) {
    try {
      if (Objects.requireNonNull(optional).isPresent()) {
        return success(optional.get());
      } else {
        return failure(new NoSuchElementException());
      }
    } catch (Throwable t) {
      return failure(t);
    }
  }

  /**
   * Returns a {@link Try} with the specified value.
   *
   * @param <T> the class of the value
   * @param value the value to be present
   * @return a {@link Try.Success} with the value
   * @throws NullPointerException if value is null
   */
  static <T> Try<T> success(T value) {
    return new Success<>(value);
  }

  /**
   * Returns a {@link Try} with the specified exception.
   *
   * @param <T> the class of the value
   * @param throwable the value to be present, which must be non-null
   * @return a {@link Try.Failure} with the exception
   * @throws NullPointerException if throwable is null
   */
  static <T> Try<T> failure(Throwable throwable) {
    return new Failure<>(throwable);
  }

  /**
   * If this is {@link Try.Success}, returns the value, if this is {@link Try.Failure}, throws the
   * exception stored as unchecked.
   *
   * @return the value held by this {@link Try.Success}
   * @see Try#isSuccess()
   * @see Try#isFailure()
   */
  T get();

  /**
   * @return {@code true} if there is a value present, otherwise {@code false}
   */
  boolean isSuccess();

  /**
   * @return {@code true} if there is an exception present, otherwise {@code false}
   */
  boolean isFailure();

  /**
   * If a value is present, invoke the specified consumer with the value, otherwise do nothing.
   *
   * @param consumer block to be executed if a value is present
   */
  void ifSuccess(ThrowableConsumer<? super T> consumer);

  /**
   * If an exception is present, invoke the specified consumer with the value, otherwise do nothing.
   *
   * @param consumer block to be executed if an exception is present
   */
  void ifFailure(ThrowableConsumer<Throwable> consumer);

  /**
   * If a value is present, invoke the specified consumer with the value, otherwise invoke the
   * specified consumer with the exception.
   *
   * @param valueConsumer block to be executed if a value is present
   * @param throwableConsumer block to be executed if an exception is present
   */
  void ifSuccessOrElse(
      ThrowableConsumer<? super T> valueConsumer, ThrowableConsumer<Throwable> throwableConsumer);

  /**
   * If a value is present, and the value matches the given predicate, return a {@link Try}
   * describing the value, otherwise return a failed {@link Try}.
   *
   * @param predicate a predicate to apply to the value, if present
   * @return a {@link Try.Success} describing the value of this {@link Try} if a value is present
   *     and the value matches the given predicate, otherwise a {@link Try.Failure}
   */
  Try<T> filter(ThrowablePredicate<? super T> predicate);

  /**
   * If a value is present, apply the provided mapping function to it, and if the result is
   * non-null, return a {@link Try} describing the result. Otherwise, return a failed {@link Try}.
   *
   * @param <U> The type of the result of the mapping function
   * @param mapper a mapping function to apply to the value, if present
   * @return a {@link Try.Success} describing the result of applying a mapping function to the value
   *     of this {@link Try}, if a value is present, otherwise a {@link Try.Failure}
   */
  <U> Try<U> map(ThrowableFunction<? super T, ? extends U> mapper);

  /**
   * If a value is present, apply the provided {@link Try}-bearing mapping function to it, return
   * that result, otherwise return a failed {@link Try}. This method is similar to {@link
   * #map(ThrowableFunction)}, but the provided mapper is one whose result is already a {@link Try},
   * and if invoked, {@code flatMap} does not wrap it with an additional {@link Try}.
   *
   * @param <U> The type parameter to the {@link Try} returned by
   * @param mapper a mapping function to apply to the value, if present the mapping function
   * @return the result of applying a {@link Try}-bearing mapping function to the value of this
   *     {@link Try}, if a value is present, otherwise a {@link Try.Failure}
   */
  <U> Try<U> flatMap(ThrowableFunction<? super T, Try<U>> mapper);

  /**
   * Convert this {@link Try} to {@link Optional} without preserving exception information.
   *
   * @return new {@link Optional} containing value or {@link Optional#empty()} if there was an
   *     exception
   */
  Optional<T> toOptional();

  /**
   * Continue {@link Try} composition by providing alternative ways to compute desired value
   *
   * @param supplier to invoke in case of failure.
   * @return new {@link Try} instance
   */
  Try<T> orElseTry(ThrowableSupplier<T> supplier);

  /**
   * Return the value if present, otherwise return {@code other}.
   *
   * @param other the value to be returned if there is a failure, may be null
   * @return the value, if present, otherwise {@code other}
   */
  default T orElse(T other) {
    try {
      return get();
    } catch (Throwable t) {
      return other;
    }
  }

  /**
   * Return the value if present, otherwise invoke {@code other} and return the result of that
   * invocation.
   *
   * @param other a {@link Supplier} whose result is returned if no value is present
   * @return the value if present otherwise the result of {@code other.get()}
   * @throws NullPointerException if value is not present and {@code other} is null
   */
  default T orElse(Supplier<? extends T> other) {
    try {
      return get();
    } catch (Throwable t) {
      return Objects.requireNonNull(other, "Try.orElseGet(Supplier) argument must not be null")
          .get();
    }
  }

  /**
   * A container object which contains value.
   *
   * @param <T> the class of the value
   */
  class Success<T> implements Try<T> {
    /** Non-null value. */
    private final T value;

    /**
     * Constructs an instance with the value present.
     *
     * @param value the value to be present
     */
    protected Success(T value) {
      this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public T get() {
      return value;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSuccess() {
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isFailure() {
      return false;
    }

    /** {@inheritDoc} */
    @Override
    public void ifSuccess(ThrowableConsumer<? super T> consumer) {
      Objects.requireNonNull(consumer).accept(value);
    }

    /** {@inheritDoc} */
    @Override
    public void ifFailure(ThrowableConsumer<Throwable> consumer) {
      // Do nothing
    }

    /** {@inheritDoc} */
    @Override
    public void ifSuccessOrElse(
        ThrowableConsumer<? super T> valueConsumer,
        ThrowableConsumer<Throwable> throwableConsumer) {
      Objects.requireNonNull(valueConsumer).accept(value);
    }

    /** {@inheritDoc} */
    @Override
    public Try<T> filter(ThrowablePredicate<? super T> predicate) {
      try {
        if (Objects.requireNonNull(predicate).test(get())) {
          return this;
        } else {
          return failure(new NoSuchElementException());
        }
      } catch (Throwable t) {
        return failure(t);
      }
    }

    /** {@inheritDoc} */
    @Override
    public <U> Try<U> map(ThrowableFunction<? super T, ? extends U> mapper) {
      return Try.of(() -> Objects.requireNonNull(mapper).apply(get()));
    }

    /** {@inheritDoc} */
    @Override
    public <U> Try<U> flatMap(ThrowableFunction<? super T, Try<U>> mapper) {
      try {
        return Objects.requireNonNull(mapper).apply(get());
      } catch (Throwable t) {
        return failure(t);
      }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<T> toOptional() {
      return Optional.ofNullable(get());
    }

    /** {@inheritDoc} */
    @Override
    public Try<T> orElseTry(ThrowableSupplier<T> supplier) {
      Objects.requireNonNull(supplier, "Try.orElse argument must not be null");
      return this;
    }
  }

  /**
   * A container object which contains exception.
   *
   * @param <T> the class of the value
   */
  class Failure<T> implements Try<T> {
    /** Non-null exception. */
    private final Throwable exception;

    /**
     * Constructs an instance with the exception present.
     *
     * @param exception the non-null exception to be present
     * @throws NullPointerException if exception is null
     */
    protected Failure(Throwable exception) {
      this.exception =
          Objects.requireNonNull(exception, "Try.failure(Throwable) argument must not be null");
    }

    /** {@inheritDoc} */
    @Override
    public T get() {
      return ExceptionSuppressor.asUnchecked(exception);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSuccess() {
      return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isFailure() {
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public void ifSuccess(ThrowableConsumer<? super T> consumer) {
      // Do nothing
    }

    /** {@inheritDoc} */
    @Override
    public void ifFailure(ThrowableConsumer<Throwable> consumer) {
      Objects.requireNonNull(consumer).accept(exception);
    }

    /** {@inheritDoc} */
    @Override
    public void ifSuccessOrElse(
        ThrowableConsumer<? super T> valueConsumer,
        ThrowableConsumer<Throwable> throwableConsumer) {
      Objects.requireNonNull(throwableConsumer).accept(exception);
    }

    /** {@inheritDoc} */
    @Override
    public Try<T> filter(ThrowablePredicate<? super T> predicate) {
      return this;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public <U> Try<U> map(ThrowableFunction<? super T, ? extends U> mapper) {
      return (Try<U>) this;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public <U> Try<U> flatMap(ThrowableFunction<? super T, Try<U>> mapper) {
      return (Try<U>) this;
    }

    /** {@inheritDoc} */
    @Override
    public Optional<T> toOptional() {
      return Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public Try<T> orElseTry(ThrowableSupplier<T> supplier) {
      return Try.of(Objects.requireNonNull(supplier, "Try.orElse argument must not be null"));
    }
  }
}
