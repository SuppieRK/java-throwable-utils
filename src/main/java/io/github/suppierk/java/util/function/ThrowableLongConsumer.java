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
import java.util.function.LongConsumer;

/**
 * Represents an operation that accepts a single {@code long}-valued argument and returns no result.
 * This is the primitive type specialization of {@link ThrowableConsumer} for {@code long}. Unlike
 * most other functional interfaces, {@code LongConsumer} is expected to operate via side-effects.
 *
 * <p>Permits checked exceptions unlike {@link LongConsumer}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is
 * {@link #accept(long)}.
 *
 * @see ThrowableConsumer
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableLongConsumer extends LongConsumer {

  /**
   * Performs this operation on the given argument.
   *
   * @param value the input argument
   * @throws Throwable occurred during processing
   */
  void acceptUnsafe(long value) throws Throwable;

  /**
   * Performs this operation on the given argument.
   *
   * @param value the input argument
   */
  @Override
  default void accept(long value) {
    try {
      acceptUnsafe(value);
    } catch (Throwable throwable) {
      ExceptionSuppressor.asUnchecked(throwable);
    }
  }
}
