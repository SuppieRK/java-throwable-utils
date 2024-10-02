package io.github.suppierk.java;

import io.github.suppierk.java.lang.ThrowableRunnable;
import io.github.suppierk.java.util.ThrowableComparator;
import io.github.suppierk.java.util.function.*;
import java.util.Comparator;
import java.util.function.*;

/** A utility for nicer wrapping of the unsafe function calls. */
public final class UnsafeFunctions {
  private UnsafeFunctions() {
    // Utility class
  }

  public static Runnable unsafeRunnable(ThrowableRunnable runnable) {
    return runnable;
  }

  public static <T, U> BiConsumer<T, U> unsafeBiConsumer(ThrowableBiConsumer<T, U> biConsumer) {
    return biConsumer;
  }

  public static <T, U, R> BiFunction<T, U, R> unsafeBiFunction(
      ThrowableBiFunction<T, U, R> biFunction) {
    return biFunction;
  }

  public static <T> BinaryOperator<T> unsafeBinaryOperator(
      ThrowableBinaryOperator<T> binaryOperator) {
    return binaryOperator;
  }

  public static <T, U> BiPredicate<T, U> unsafeBiPredicate(ThrowableBiPredicate<T, U> biPredicate) {
    return biPredicate;
  }

  public static BooleanSupplier unsafeBooleanSupplier(ThrowableBooleanSupplier booleanSupplier) {
    return booleanSupplier;
  }

  public static <T> Consumer<T> unsafeConsumer(ThrowableConsumer<T> consumer) {
    return consumer;
  }

  public static <T> Comparator<T> unsafeComparator(ThrowableComparator<T> comparator) {
    return comparator;
  }

  public static DoubleBinaryOperator unsafeDoubleBinaryOperator(
      ThrowableDoubleBinaryOperator doubleBinaryOperator) {
    return doubleBinaryOperator;
  }

  public static DoubleConsumer unsafeDoubleConsumer(ThrowableDoubleConsumer doubleConsumer) {
    return doubleConsumer;
  }

  public static <R> DoubleFunction<R> unsafeDoubleFunction(
      ThrowableDoubleFunction<R> doubleFunction) {
    return doubleFunction;
  }

  public static DoublePredicate unsafeDoublePredicate(ThrowableDoublePredicate doublePredicate) {
    return doublePredicate;
  }

  public static DoubleSupplier unsafeDoubleSupplier(ThrowableDoubleSupplier doubleSupplier) {
    return doubleSupplier;
  }

  public static DoubleToIntFunction unsafeDoubleToIntFunction(
      ThrowableDoubleToIntFunction doubleToIntFunction) {
    return doubleToIntFunction;
  }

  public static DoubleToLongFunction unsafeDoubleToLongFunction(
      ThrowableDoubleToLongFunction doubleToLongFunction) {
    return doubleToLongFunction;
  }

  public static DoubleUnaryOperator unsafeDoubleUnaryOperator(
      ThrowableDoubleUnaryOperator doubleUnaryOperator) {
    return doubleUnaryOperator;
  }

  public static <T, R> Function<T, R> unsafeFunction(ThrowableFunction<T, R> function) {
    return function;
  }

  public static IntBinaryOperator unsafeIntBinaryOperator(
      ThrowableIntBinaryOperator intBinaryOperator) {
    return intBinaryOperator;
  }

  public static IntConsumer unsafeIntConsumer(ThrowableIntConsumer intConsumer) {
    return intConsumer;
  }

  public static <R> IntFunction<R> unsafeIntFunction(ThrowableIntFunction<R> intFunction) {
    return intFunction;
  }

  public static IntPredicate unsafeIntPredicate(ThrowableIntPredicate intPredicate) {
    return intPredicate;
  }

  public static IntSupplier unsafeIntSupplier(ThrowableIntSupplier intSupplier) {
    return intSupplier;
  }

  public static IntToDoubleFunction unsafeIntToDoubleFunction(
      ThrowableIntToDoubleFunction intToDoubleFunction) {
    return intToDoubleFunction;
  }

  public static IntToLongFunction unsafeIntToLongFunction(
      ThrowableIntToLongFunction intToLongFunction) {
    return intToLongFunction;
  }

  public static IntUnaryOperator unsafeIntUnaryOperator(
      ThrowableIntUnaryOperator intUnaryOperator) {
    return intUnaryOperator;
  }

  public static LongBinaryOperator unsafeLongBinaryOperator(
      ThrowableLongBinaryOperator longBinaryOperator) {
    return longBinaryOperator;
  }

  public static LongConsumer unsafeLongConsumer(ThrowableLongConsumer longConsumer) {
    return longConsumer;
  }

  public static <R> LongFunction<R> unsafeLongFunction(ThrowableLongFunction<R> longFunction) {
    return longFunction;
  }

  public static LongPredicate unsafeLongPredicate(ThrowableLongPredicate longPredicate) {
    return longPredicate;
  }

  public static LongSupplier unsafeLongSupplier(ThrowableLongSupplier longSupplier) {
    return longSupplier;
  }

  public static LongToDoubleFunction unsafeLongToDoubleFunction(
      ThrowableLongToDoubleFunction longToDoubleFunction) {
    return longToDoubleFunction;
  }

  public static LongToIntFunction unsafeLongToIntFunction(
      ThrowableLongToIntFunction longToIntFunction) {
    return longToIntFunction;
  }

  public static LongUnaryOperator unsafeLongUnaryOperator(
      ThrowableLongUnaryOperator longUnaryOperator) {
    return longUnaryOperator;
  }

  public static <T> ObjDoubleConsumer<T> unsafeObjDoubleConsumer(
      ThrowableObjDoubleConsumer<T> objDoubleConsumer) {
    return objDoubleConsumer;
  }

  public static <T> ObjIntConsumer<T> unsafeObjIntConsumer(
      ThrowableObjIntConsumer<T> objIntConsumer) {
    return objIntConsumer;
  }

  public static <T> ObjLongConsumer<T> unsafeObjLongConsumer(
      ThrowableObjLongConsumer<T> objLongConsumer) {
    return objLongConsumer;
  }

  public static <T> Predicate<T> unsafePredicate(ThrowablePredicate<T> predicate) {
    return predicate;
  }

  public static <T> Supplier<T> unsafeSupplier(ThrowableSupplier<T> supplier) {
    return supplier;
  }

  public static <T, U> ToDoubleBiFunction<T, U> unsafeToDoubleBiFunction(
      ThrowableToDoubleBiFunction<T, U> toDoubleBiFunction) {
    return toDoubleBiFunction;
  }

  public static <T> ToDoubleFunction<T> unsafeToDoubleFunction(
      ThrowableToDoubleFunction<T> toDoubleFunction) {
    return toDoubleFunction;
  }

  public static <T, U> ToIntBiFunction<T, U> unsafeToIntBiFunction(
      ThrowableToIntBiFunction<T, U> toIntBiFunction) {
    return toIntBiFunction;
  }

  public static <T> ToIntFunction<T> unsafeToIntFunction(ThrowableToIntFunction<T> toIntFunction) {
    return toIntFunction;
  }

  public static <T, U> ToLongBiFunction<T, U> unsafeToLongBiFunction(
      ThrowableToLongBiFunction<T, U> toLongBiFunction) {
    return toLongBiFunction;
  }

  public static <T> ToLongFunction<T> unsafeToLongFunction(
      ThrowableToLongFunction<T> toLongFunction) {
    return toLongFunction;
  }

  public static <T> UnaryOperator<T> unsafeUnaryOperator(ThrowableUnaryOperator<T> unaryOperator) {
    return unaryOperator;
  }
}
