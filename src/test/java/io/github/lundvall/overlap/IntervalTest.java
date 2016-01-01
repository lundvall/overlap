package io.github.lundvall.overlap;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class IntervalTest {
    @Test
    public void overlap() throws Exception {
        long currentTime = System.currentTimeMillis();

        Interval low = Interval.of(currentTime, currentTime + 1000);
        Interval high = Interval.of(currentTime + 1000, currentTime + 2000);

        assertTrue(low.overlapsWith(high));
    }

    @Test
    public void noOverlap() throws Exception {
        assertFalse(Interval.of(1, 10).overlapsWith(Interval.of(11, 20)));
    }

    @Test
    public void merging() throws Exception {
        Interval merged = Interval.of(1, 10).mergeWith(Interval.of(5, 20));

        assertEquals(1, merged.low);
        assertEquals(20, merged.high);
    }

    @Test
    public void mergeLotsOfIntervals() {
        List<Interval> merged = Interval.merge(intervals());

        assertEquals(3, merged.size());
        assertEquals(1, merged.get(0).low);
        assertEquals(200, merged.get(1).low);
        assertEquals(500, merged.get(2).high);
    }

    private List<Interval> intervals() {
        return Arrays.asList(
            Interval.of(2, 10), Interval.of(5, 15),
            Interval.of(9, 21), Interval.of(1, 21),
            Interval.of(200, 303), Interval.of(2, 24),
            Interval.of(4, 24), Interval.of(3, 29),
            Interval.of(3, 49), Interval.of(408, 500),
            Interval.of(1, 14), Interval.of(1, 19));
    }
}
