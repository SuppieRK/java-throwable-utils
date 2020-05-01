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

package io.github.suppie.toolset.java.util;

/**
 * Small utility taken from Apache commons-lang ExceptionUtils class
 */
public interface ExceptionSuppressor {
    /**
     * Claim a Throwable is another Exception type using type erasure. This
     * hides a checked exception from the java compiler, allowing a checked
     * exception to be thrown without having the exception in the method's throw
     * clause.
     *
     * @param throwable is exception to rethrow
     * @param <R>       is return type of method caller if needed
     * @param <T>       is exception type to erase
     * @return nothing
     * @throws T is artificial construct to trick Java compiler
     */
    @SuppressWarnings("unchecked")
    static <R, T extends Throwable> R asUnchecked(final Throwable throwable) throws T {
        throw (T) throwable;
    }
}
