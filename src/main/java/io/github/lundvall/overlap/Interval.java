package io.github.lundvall.overlap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Interval {
    public final long low;
    public final long high;

    public Interval(long low, long high) {
        this.low = low;
        this.high = high;
    }

    public boolean overlapsWith(Interval other) {
        return low <= other.high && other.low <= high;
    }

    public Interval mergeWith(Interval other) {
        return of(Math.min(low, other.low), Math.max(high, other.high));
    }

    public String toString() {
        return "Interval{low=" + low + ", high=" + high + '}';
    }

    public static List<Interval> merge(List<Interval> intervals) {
        List<Interval> sortedIntervals = sortedCopyOf(intervals);
        Stack<Interval> stack = stackWithFirstElementOf(sortedIntervals);
        return mergeOverlappingIntervals(sortedIntervals, stack);
    }

    private static List<Interval> sortedCopyOf(List<Interval> intervals) {
        List<Interval> sorted = new ArrayList<>(intervals);
        sorted.sort(lowestFirst);
        return sorted;
    }

    private static Comparator<Interval> lowestFirst = (a, b) -> a.low < b.low ? -1 : 1;

    private static Stack<Interval> stackWithFirstElementOf(List<Interval> intervals) {
        Stack<Interval> stack = new Stack<>();
        stack.push(intervals.remove(0));
        return stack;
    }

    private static List<Interval> mergeOverlappingIntervals(List<Interval> intervals, Stack<Interval> stack) {
        for (Interval interval : intervals)
            if (interval.overlapsWith(stack.lastElement()))
                stack.push(interval.mergeWith(stack.pop()));
            else
                stack.push(interval);
        return stack;
    }

    public static Interval of(long low, long high) {
        return new Interval(low, high);
    }
}
