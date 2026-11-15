package com.bobocode.se;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class BusinessDaysIterator implements Iterator<LocalDate> {
    // Before the first call this is the start date. After each call it is
    // the business day we just returned.
    private LocalDate currentDate;

    public BusinessDaysIterator(LocalDate startDate) {
        this.currentDate = Objects.requireNonNull(startDate, "startDate");
    }

    @Override
    public boolean hasNext() {
        // LocalDate.MAX is a Friday, so it is the last possible business day.
        // Checking availability leaves currentDate untouched.
        return currentDate.isBefore(LocalDate.MAX);
    }

    @Override
    public LocalDate next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more business days within the LocalDate range");
        }

        DayOfWeek day;
        do {
            // Advance before checking the day so the start date is excluded.
            // LocalDate is immutable, so plusDays returns a new date.
            currentDate = currentDate.plusDays(1);
            day = currentDate.getDayOfWeek();
        } while (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

        return currentDate;
    }
}
