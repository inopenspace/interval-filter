package com.alex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        long rage = 4;
        long start = 0;
        long end = 24;
        List<IntervalModel> intervals = new ArrayList<>();
        intervals.add(new IntervalModel(0, 25));
        IntervalFilter intervalFilter = new IntervalFilter(start, end, rage);

        intervals.forEach(intervalFilter::filter);
        Map<Integer, List<IntervalModel>> ranges= intervalFilter.getRanges(null);
        System.out.println("");

    }
}
