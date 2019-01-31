package com.alex;

public class IntervalModel {

    private long start;
    private long end;
    private long id;

    public IntervalModel(long start, long end) {
        this.start = start;
        this.end = end;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected IntervalModel clone() {
        return new IntervalModel(start, end);
    }
}
