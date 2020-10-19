package com.github.suppie.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThrowableComparatorTest {
    @Test
    void sanityTest() {
        int first = 1;
        int second = 2;

        int firstOrderExpected = Integer.compare(first, second);
        int secondOrderExpected = Integer.compare(second, first);

        ThrowableComparator<Integer> throwableComparator = Integer::compare;

        Assertions.assertEquals(firstOrderExpected, throwableComparator.compare(first, second));
        Assertions.assertEquals(secondOrderExpected, throwableComparator.compare(second, first));
    }
}
