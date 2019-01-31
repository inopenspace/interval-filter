package com.alex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntervalFilter {

    private IntervalFilter next;
    private List<IntervalModel> models = new ArrayList<>();
    private long start;
    private long end;

    public IntervalFilter(long start, long end, long range) {
        this.start = start;
        this.end = start + range;
        if (this.end < end) {
            next = new IntervalFilter(this.end, end, range);
        }
    }

    public void filter(IntervalModel model) {
        if (model.getEnd() < getStart()) { //interval is less
            return;
        }
        if (model.getStart() >= getEnd()) { //interval is more
            if(next!=null){
                next.filter(model);
            }
            return;
        }

        if (model.getStart() >= getStart() && model.getEnd() <= getEnd()) {//included
            models.add(model);
            return;
        }
        if (model.getStart() <= getStart() && model.getEnd() >= getEnd()) {//included
            models.add(model);
            if(next!=null){
                next.filter(model);
            }
            return;
        }
        if (model.getStart() <= getStart() && model.getEnd() <= getEnd() && model.getEnd() > getStart()) { //include left
            models.add(model);
            return;
        }

        if (model.getStart() >= getStart() && model.getEnd() >= getEnd() && model.getStart() < getEnd()) { //interval right
            models.add(model);
            if(next!=null){
                next.filter(model);
            }

        }
    }

    public Map<Integer, List<IntervalModel>> getRanges(Map<Integer, List<IntervalModel>> integerListMap) {
        if (integerListMap == null) {
            integerListMap = new HashMap<>();

        }
        integerListMap.put(integerListMap.size() + 1, models);
        if (next != null) {
            next.getRanges(integerListMap);
        }

        return integerListMap;
    }


    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
