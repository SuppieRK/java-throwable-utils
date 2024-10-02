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

package io.github.suppierk.java.util.function;

import io.github.suppierk.java.util.ExceptionSuppressor;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Represents an operation on a single operand that produces a result of the same type as its
 * operand. This is a specialization of {@code Function} for the case where the operand and result
 * are of the same type.
 *
 * <p>Permits checked exceptions unlike {@link UnaryOperator}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is
 * {@link #apply(Object)}.
 *
 * @param <T> the type of the operand and result of the operator
 * @see ThrowableFunction
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableUnaryOperator<T> extends UnaryOperator<T> {
  /**
   * Applies this function to the given argument.
   *
   * @param t the function argument
   * @return the function result
   * @throws Throwable occurred during processing
   */
  T applyUnsafe(T t) throws Throwable;

  /**
   * Applies this function to the given argument.
   *
   * <p>Has default implementation in order to make {@link #compose(Function)} and {@link
   * #andThen(Function)} work.
   *
   * @param t the function argument
   * @return the function result
   */
  @Override
  default T apply(T t) {
    try {
      return applyUnsafe(t);
    } catch (Throwable throwable) {
      return ExceptionSuppressor.asUnchecked(throwable);
    }
  }

  /**
   * Returns a unary operator that always returns its input argument.
   *
   * @param <T> the type of the input and output of the operator
   * @return a unary operator that always returns its input argument
   */
  static <T> ThrowableUnaryOperator<T> identity() {
    return t -> t;
  }
}
