package com.bobocode.se;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IteratorsTest {
    @Test
    void returnsMondayAndTuesdayAfterSaturday() {
        Iterator<LocalDate> days = new BusinessDaysIterator(LocalDate.of(2022, 1, 1));

        assertTrue(days.hasNext());
        assertTrue(days.hasNext());
        assertEquals(LocalDate.of(2022, 1, 3), days.next());
        assertEquals(LocalDate.of(2022, 1, 4), days.next());
    }

    @Test
    void skipsFromFridayToMonday() {
        Iterator<LocalDate> days = new BusinessDaysIterator(LocalDate.of(2022, 1, 7));

        assertEquals(LocalDate.of(2022, 1, 10), days.next());
    }

    @Test
    void stopsAtTheLastRepresentableDate() {
        Iterator<LocalDate> days = new BusinessDaysIterator(LocalDate.MAX.minusDays(1));

        assertEquals(LocalDate.MAX, days.next());
        assertFalse(days.hasNext());
        assertThrows(NoSuchElementException.class, days::next);
    }

    @Test
    void zipsDifferentTypesUsingAFormattingFunction() {
        Iterator<String> zipped = new ZippingIterator<>(
                List.of("Ana", "Luis").iterator(),
                List.of(10, 20).iterator(),
                (name, score) -> name + ": " + score);

        assertTrue(zipped.hasNext());
        assertTrue(zipped.hasNext());
        assertEquals("Ana: 10", zipped.next());
        assertEquals("Luis: 20", zipped.next());
        assertFalse(zipped.hasNext());
    }

    @Test
    void stopsWhenTheSecondInputEndsAndLeavesTheExtraValueUnread() {
        Iterator<Integer> first = List.of(1, 2).iterator();
        Iterator<Integer> zipped = new ZippingIterator<>(
                first, List.of(10).iterator(), Integer::sum);

        assertEquals(11, zipped.next());
        assertFalse(zipped.hasNext());
        assertThrows(NoSuchElementException.class, zipped::next);
        // The first input still has the value that could not be paired.
        assertEquals(2, first.next());
    }

    @Test
    void stopsImmediatelyWhenTheFirstInputIsEmpty() {
        Iterator<Integer> second = List.of(10).iterator();
        Iterator<Integer> zipped = new ZippingIterator<>(
                List.<Integer>of().iterator(), second, Integer::sum);

        assertFalse(zipped.hasNext());
        assertThrows(NoSuchElementException.class, zipped::next);
        assertEquals(10, second.next());
    }

    @Test
    void returnsAFullBatchAndThenAPartialBatch() {
        Iterator<List<Integer>> batches = new BufferingIterator<>(
                List.of(1, 2, 3, 4, 5).iterator(), 3);

        assertTrue(batches.hasNext());
        assertTrue(batches.hasNext());
        List<Integer> firstBatch = batches.next();

        // Reading the partial batch leaves the first batch intact.
        assertEquals(List.of(4, 5), batches.next());
        assertEquals(List.of(1, 2, 3), firstBatch);
        assertFalse(batches.hasNext());
        assertThrows(NoSuchElementException.class, batches::next);
    }

    @Test
    void returnsNoBatchForAnEmptySource() {
        Iterator<List<Integer>> batches = new BufferingIterator<>(
                List.<Integer>of().iterator(), 3);

        assertFalse(batches.hasNext());
        assertThrows(NoSuchElementException.class, batches::next);
    }

    @Test
    void rejectsZeroAndNegativeBatchSizes() {
        assertThrows(IllegalArgumentException.class,
                () -> new BufferingIterator<>(List.of(1).iterator(), 0));
        assertThrows(IllegalArgumentException.class,
                () -> new BufferingIterator<>(List.of(1).iterator(), -1));
    }
}
