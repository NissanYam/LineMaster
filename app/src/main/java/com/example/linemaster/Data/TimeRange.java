package com.example.linemaster.Data;
public class TimeRange {
    private int startHour = 0;
    private int startMinutes = 0;
    private int endHour = 0;
    private int endMinutes = 0;

    public TimeRange() {
    }

    public int getStartHour() {
        return startHour;
    }

    public TimeRange setStartHour(int startHour) {
        this.startHour = startHour;
        return this;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public TimeRange setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
        return this;
    }

    public int getEndHour() {
        return endHour;
    }

    public TimeRange setEndHour(int endHour) {
        this.endHour = endHour;
        return this;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public TimeRange setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
        return this;
    }

    public boolean equals(TimeRange timeRange){
        if(timeRange.getStartHour() == this.getStartHour()
        && timeRange.getStartMinutes() == this.getStartMinutes()
        && timeRange.getEndHour() == this.getEndHour()
        && timeRange.getEndMinutes() == this.getEndMinutes())
            return true;
        return false;
    }
    public TimeRange clone(){
        TimeRange timeRange = new TimeRange();
        timeRange.endHour = this.endHour;
        timeRange.endMinutes = this.endMinutes;
        timeRange.startHour = this.startHour;
        timeRange.startMinutes = this.startMinutes;
        return timeRange;
    }
}
