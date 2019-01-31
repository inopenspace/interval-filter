package com.alex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class IntervalFilter {

    private IntervalFilter next;
    private List<IntervalModel> models = new ArrayList<>();
    private long start;
    private long end;

    private Predicate<IntervalModel> isOld = model -> model.getEnd() < getStart();
    private Predicate<IntervalModel> outOfEnd = model -> model.getStart() >= getEnd();

    public IntervalFilter(long start, long end, long range) {
        this.start = start;
        this.end = start + range;
        if (this.end < end) {
            next = new IntervalFilter(this.end, end, range);
        }
    }

    public void filter(IntervalModel model) throws FilterException {
        //время окончания интервала меньше времени начала нашего
        if (model.getEnd() < getStart()) {
            return;
        }
        //начинается позже чем заканчивается наш
        if (model.getStart() >= getEnd()) {
            if (next != null) {
                next.filter(model);
            }
        }
        //полностью включен
        if (model.getStart() >= getStart() && model.getEnd() <= getEnd()) {
            //просто вставляем
            models.add(model.clone());
            return;
        }
        //огибает с обеих сторон
        if (model.getStart() <= getStart() && model.getEnd() >= getEnd()) {
            if (next == null) {
                throw new FilterException("Перебор, интервал ушел за край"); //дальше идти некуда
            }
            // устанавливаем новые границы и отдаем дальше
            IntervalModel newModel = model.clone();
            newModel.setStart(start);
            newModel.setEnd(end);
            models.add(newModel);
            next.filter(model);
            return;
        }
        //включена дата окончания
        if (model.getStart() <= getStart() && model.getEnd() <= getEnd() && model.getEnd() > getStart()) {
            IntervalModel newModel = model.clone();
            newModel.setStart(start);
            models.add(newModel);
            return;
        }

        //включена дата начала
        if (model.getStart() >= getStart() && model.getEnd() >= getEnd() && model.getStart() < getEnd()) {
            IntervalModel newModel = model.clone();
            newModel.setEnd(end);
            models.add(newModel);

            if (next != null) {
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
