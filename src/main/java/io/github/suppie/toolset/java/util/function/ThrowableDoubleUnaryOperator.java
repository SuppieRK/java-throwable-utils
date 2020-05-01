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

import java.util.function.DoubleUnaryOperator;

/**
 * Represents an operation on a single {@code double}-valued operand that produces
 * a {@code double}-valued result.  This is the primitive type specialization of
 * {@link ThrowableUnaryOperator} for {@code double}.
 * <p>
 * Permits checked exceptions unlike {@link DoubleUnaryOperator}
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(double)}.
 *
 * @see ThrowableUnaryOperator
 */
@FunctionalInterface
@SuppressWarnings("squid:S112")
public interface ThrowableDoubleUnaryOperator extends DoubleUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws Throwable occurred during processing
     */
    double applyAsDoubleUnsafe(double operand) throws Throwable;

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    @Override
    default double applyAsDouble(double operand) {
        try {
            return applyAsDoubleUnsafe(operand);
        } catch (Throwable throwable) {
            return ExceptionSuppressor.asUnchecked(throwable);
        }
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static ThrowableDoubleUnaryOperator identity() {
        return t -> t;
    }
}
