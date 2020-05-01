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

package io.github.suppie.toolset.java.util.function;

import io.github.suppie.toolset.java.util.ExceptionSuppressor;

import java.util.function.LongPredicate;

/**
 * Represents a predicate (boolean-valued function) of one {@code long}-valued
 * argument. This is the {@code long}-consuming primitive type specialization of
 * {@link ThrowablePredicate}.
 * <p>
 * Permits checked exceptions unlike {@link LongPredicate}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(long)}.
 *
 * @see ThrowablePredicate
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableLongPredicate extends LongPredicate {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     * @throws Throwable occurred during processing
     */
    boolean testUnsafe(long value) throws Throwable;

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    default boolean test(long value) {
        try {
            return testUnsafe(value);
        } catch (Throwable throwable) {
            return ExceptionSuppressor.asUnchecked(throwable);
        }
    }
}
