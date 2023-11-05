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
import java.util.function.ToIntFunction;

/**
 * Represents a function that produces an int-valued result. This is the {@code int}-producing
 * primitive specialization for {@link Function}.
 *
 * <p>Permits checked exceptions unlike {@link ToIntFunction}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is
 * {@link #applyAsInt(Object)}.
 *
 * @param <T> the type of the input to the function
 * @see Function
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableToIntFunction<T> extends ToIntFunction<T> {

  /**
   * Applies this function to the given argument.
   *
   * @param value the function argument
   * @return the function result
   * @throws Throwable occurred during processing
   */
  int applyAsIntUnsafe(T value) throws Throwable;

  /**
   * Applies this function to the given argument.
   *
   * @param value the function argument
   * @return the function result
   */
  @Override
  default int applyAsInt(T value) {
    try {
      return applyAsIntUnsafe(value);
    } catch (Throwable throwable) {
      return ExceptionSuppressor.asUnchecked(throwable);
    }
  }
}
