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
import java.util.function.BooleanSupplier;

/**
 * Represents a supplier of {@code boolean}-valued results. This is the {@code boolean}-producing
 * primitive specialization of {@link ThrowableSupplier}.
 *
 * <p>Permits checked exceptions unlike {@link BooleanSupplier}
 *
 * <p>There is no requirement that a new or distinct result be returned each time the supplier is
 * invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is
 * {@link #getAsBoolean()}.
 *
 * @see ThrowableSupplier
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableBooleanSupplier extends BooleanSupplier {

  /**
   * Gets a result.
   *
   * @return a result
   * @throws Throwable occurred during processing
   */
  boolean getAsBooleanUnsafe() throws Throwable;

  /**
   * Gets a result.
   *
   * @return a result
   */
  @Override
  default boolean getAsBoolean() {
    try {
      return getAsBooleanUnsafe();
    } catch (Throwable throwable) {
      return ExceptionSuppressor.asUnchecked(throwable);
    }
  }
}
