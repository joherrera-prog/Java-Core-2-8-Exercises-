package com.bobocode.se;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class BufferingIterator<T> implements Iterator<List<T>> {
    private final Iterator<? extends T> source;
    private final int batchSize;

    public BufferingIterator(Iterator<? extends T> source, int batchSize) {
        this.source = Objects.requireNonNull(source, "source");

        // A positive size lets each call to next() consume at least one value.
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be positive");
        }
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        // One remaining value is enough to form a final partial batch.
        return source.hasNext();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more batches");
        }

        // Give each call its own list so later batches leave earlier ones intact.
        List<T> batch = new ArrayList<>();

        // Stop when the batch is full or the source runs out of values.
        while (batch.size() < batchSize && source.hasNext()) {
            batch.add(source.next());
        }

        return batch;
    }
}
