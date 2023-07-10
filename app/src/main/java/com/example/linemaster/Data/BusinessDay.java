package com.example.linemaster.Data;

import java.time.DayOfWeek;

public class BusinessDay {
    private DayOfWeek dayOfWeek;
    private boolean active;
    private TimeRange timeRanges;

    public BusinessDay() {
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public BusinessDay setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public TimeRange getTimeRanges() {
        return timeRanges;
    }

    public BusinessDay setTimeRanges(TimeRange timeRanges) {
        this.timeRanges = timeRanges;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public BusinessDay setActive(boolean active) {
        this.active = active;
        return this;
    }
    public BusinessDay clone(){
        BusinessDay businessDay = new BusinessDay();
        businessDay.active = this.active;
        businessDay.dayOfWeek = this.dayOfWeek;
        businessDay.timeRanges = this.timeRanges.clone();
        return businessDay;
    }
}
