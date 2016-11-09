/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Attila
 */
public class WorkMonth {
    private List<WorkDay> days;
    private YearMonth date;
    
    public WorkMonth(int year, int month) {
        days = new ArrayList<>();
        date = YearMonth.of(year, month);
    }

    public long getExtraMinPerMonth() {
        return getSumPerMonth() - getRequiredMinPerMonth();
    }

    public boolean isNewDate(WorkDay day) {
        return days.stream().noneMatch((d) -> (d.getActualDay().getDayOfMonth() == day.getActualDay().getDayOfYear()));
    }

    public boolean isSameMonth(WorkDay day) {
        return day.getActualDay().getMonthValue() == date.getMonthValue();
    }

    public void addWorkDay(WorkDay day) { addWorkDay(day, false); }
    public void addWorkDay(WorkDay day, boolean isWeekendEnabled) {
        if(isWeekendEnabled || day.isWeekday()) {
            if(isSameMonth(day) && isNewDate(day))
                days.add(day);
        }
    }

    public long getSumPerMonth() {
        long sumPerDay = 0;
        for(WorkDay wd : days) {
            sumPerDay += wd.getSumPerDay();
        }

        return sumPerDay;
    }
    public long getRequiredMinPerMonth() {
        long requiredMinPerDay = 0;
        for(WorkDay wd : days) {
            requiredMinPerDay += wd.getRequiredMinPerDay();
        }

        return requiredMinPerDay;
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }
}
