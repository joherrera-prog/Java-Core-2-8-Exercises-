package com.bobocode.se;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;

public final class ZippingIterator<A, B, C> implements Iterator<C> {
    private final Iterator<? extends A> first;
    private final Iterator<? extends B> second;

    // A and B are the two input types. C is the combined result type.
    private final BiFunction<? super A, ? super B, ? extends C> combiner;

    public ZippingIterator(
            Iterator<? extends A> first,
            Iterator<? extends B> second,
            BiFunction<? super A, ? super B, ? extends C> combiner) {
        this.first = Objects.requireNonNull(first, "first");
        this.second = Objects.requireNonNull(second, "second");
        this.combiner = Objects.requireNonNull(combiner, "combiner");
    }

    @Override
    public boolean hasNext() {
        // A result needs one value from each iterator. Checking consumes neither.
        return first.hasNext() && second.hasNext();
    }

    @Override
    public C next() {
        // Check both inputs before reading either one. Any unpaired value
        // stays in its original iterator.
        if (!hasNext()) {
            throw new NoSuchElementException("No more pairs");
        }

        A firstValue = first.next();
        B secondValue = second.next();

        // apply runs the caller's function with this pair.
        return combiner.apply(firstValue, secondValue);
    }
}
