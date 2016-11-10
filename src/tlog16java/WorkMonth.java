/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import timelogger.exceptions.NotNewDateException;
import timelogger.exceptions.NotTheSameMonthException;
import timelogger.exceptions.WeekendNotEnabledException;

/**
 *
 * @author Attila
 */
public class WorkMonth {
    private final List<WorkDay> days;
    private final YearMonth date;
    
    public WorkMonth(int year, int month) {
        days = new ArrayList<>();
        date = YearMonth.of(year, month);
    }

    public long getExtraMinPerMonth() {
        return getSumPerMonth() - getRequiredMinPerMonth();
    }

    public boolean isNewDate(WorkDay day) {
        return days.stream().noneMatch((d) -> (d.getActualDay().getDayOfMonth() == day.getActualDay().getDayOfMonth()));
    }

    public boolean isSameMonth(WorkDay day) {
        return day.getActualDay().getMonthValue() == date.getMonthValue();
    }

    public void addWorkDay(WorkDay day) { addWorkDay(day, false); }
    public void addWorkDay(WorkDay day, boolean isWeekendEnabled) {
        if(!isNewDate(day))
            throw new NotNewDateException();
        
        if(!isSameMonth(day))
            throw new NotTheSameMonthException();
        
        if(!isWeekendEnabled && !day.isWeekDay()) 
            throw new WeekendNotEnabledException();
        
        days.add(day);
    }

    public long getSumPerMonth() {
        return days.stream().mapToLong(s -> s.getSumPerDay()).sum();
    }
    public long getRequiredMinPerMonth() {
        return days.stream().mapToLong(s -> s.getRequiredMinPerDay()).sum();
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }
}
