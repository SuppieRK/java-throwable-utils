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
import java.util.function.ToLongBiFunction;

/**
 * Represents a function that accepts two arguments and produces a long-valued result. This is the
 * {@code long}-producing primitive specialization for {@link ThrowableBiFunction}.
 *
 * <p>Permits checked exceptions unlike {@link ToLongBiFunction}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is
 * {@link #applyAsLong(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @see ThrowableBiFunction
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableToLongBiFunction<T, U> extends ToLongBiFunction<T, U> {

  /**
   * Applies this function to the given arguments.
   *
   * @param t the first function argument
   * @param u the second function argument
   * @return the function result
   * @throws Throwable occurred during processing
   */
  long applyAsLongUnsafe(T t, U u) throws Throwable;

  /**
   * Applies this function to the given arguments.
   *
   * @param t the first function argument
   * @param u the second function argument
   * @return the function result
   */
  @Override
  default long applyAsLong(T t, U u) {
    try {
      return applyAsLongUnsafe(t, u);
    } catch (Throwable throwable) {
      return ExceptionSuppressor.asUnchecked(throwable);
    }
  }
}
