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

package com.github.suppie.java.util.function;

import com.github.suppie.java.util.ExceptionSuppressor;

import java.util.function.DoubleConsumer;

/**
 * Represents an operation that accepts a single {@code double}-valued argument and
 * returns no result.  This is the primitive type specialization of
 * {@link ThrowableConsumer} for {@code double}.  Unlike most other functional interfaces,
 * {@code DoubleConsumer} is expected to operate via side-effects.
 * <p>
 * Permits checked exceptions unlike {@link DoubleConsumer}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(double)}.
 *
 * @see ThrowableConsumer
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableDoubleConsumer extends DoubleConsumer {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws Throwable occurred during processing
     */
    void acceptUnsafe(double value) throws Throwable;

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    @Override
    default void accept(double value) {
        try {
            acceptUnsafe(value);
        } catch (Throwable throwable) {
            ExceptionSuppressor.asUnchecked(throwable);
        }
    }
}
