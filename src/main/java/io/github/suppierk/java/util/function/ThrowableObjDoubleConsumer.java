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

package io.github.suppierk.java.util.function;

import io.github.suppierk.java.util.ExceptionSuppressor;
import java.util.function.ObjDoubleConsumer;

/**
 * Represents an operation that accepts an object-valued and a {@code double}-valued argument, and
 * returns no result. This is the {@code (reference, double)} specialization of {@link
 * ThrowableBiConsumer}. Unlike most other functional interfaces, {@code ObjDoubleConsumer} is
 * expected to operate via side-effects.
 *
 * <p>Permits checked exceptions unlike {@link ObjDoubleConsumer}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is
 * {@link #accept(Object, double)}.
 *
 * @param <T> the type of the object argument to the operation
 * @see ThrowableBiConsumer
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableObjDoubleConsumer<T> extends ObjDoubleConsumer<T> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param value the second input argument
   * @throws Throwable occurred during processing
   */
  void acceptUnsafe(T t, double value) throws Throwable;

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param value the second input argument
   */
  @Override
  default void accept(T t, double value) {
    try {
      acceptUnsafe(t, value);
    } catch (Throwable throwable) {
      ExceptionSuppressor.asUnchecked(throwable);
    }
  }
}
