/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import timelogger.exceptions.FutureWorkException;
import timelogger.exceptions.NegativeMinutesOfWorkException;
import timelogger.exceptions.NotMultipleQuarterHourException;
import timelogger.exceptions.NotSeparatedTimesException;

/**
 *
 * @author Attila
 */
@lombok.Getter
public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    
    public WorkDay() { this(450); }
    public WorkDay(int requiredMinPerDay) {
        this(requiredMinPerDay, LocalDate.now().getYear() + ". " + LocalDate.now().getMonthValue() + ". " + LocalDate.now().getDayOfMonth());
    }
    public WorkDay(int requiredMinPerDay, int year, int month, int day) {
        this(requiredMinPerDay, year + ". " + month + ". " + day);
    }
    public WorkDay(int requiredMinPerDay, String actualDay) {
        if(requiredMinPerDay < 0)
            throw new NegativeMinutesOfWorkException();

        tasks = new ArrayList<>();
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = stringToLocalDate(actualDay);
        
        if(this.actualDay.compareTo(LocalDate.now()) > 0)
            throw new FutureWorkException();
    }

    public static LocalDate stringToLocalDate(String date) {
        String[] parts = date.split(". ");
        int y = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int d = Integer.parseInt(parts[2]);
        return LocalDate.of(y, m, d);
    }

    public long getExtraMinPerDay() {
        return getSumPerDay() - requiredMinPerDay;
    }
    
    public boolean isSeparatedTime(Task task) {
        return tasks.stream().noneMatch((t) -> (
                (t.getEndTime().isAfter(task.getStartTime()) && t.getStartTime().isBefore(task.getStartTime())) ||
                (t.getEndTime().isAfter(task.getEndTime()) && t.getStartTime().isBefore(task.getEndTime())) ||
                (t.getStartTime().equals(task.getStartTime()) && t.getEndTime().equals(task.getEndTime()))
            ));
    }
    
    public void addTask(Task task) {
        if(!task.isMultipleQuarterHour())
            throw new NotMultipleQuarterHourException();

        if(!isSeparatedTime(task))
            throw new NotSeparatedTimesException();

        tasks.add(task);
    }
    
    public boolean isWeekDay() {
        return !(actualDay.getDayOfWeek() == DayOfWeek.SUNDAY ||
                actualDay.getDayOfWeek() == DayOfWeek.SATURDAY);
    }

    public long getSumPerDay() {
        return tasks.stream().mapToLong(s -> s.getMinPerTask()).sum();
    }
    
    public LocalTime lastTaskEndTime() {
        if(tasks.isEmpty())
            return null;
        
        return tasks.get(tasks.size() - 1).getEndTime();
    }

    public long getRequiredMinPerDay() {
        if(requiredMinPerDay < 0)
            throw new NegativeMinutesOfWorkException();
        
        return requiredMinPerDay;
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public void setActualDay(LocalDate actualDay) {
        if(actualDay.compareTo(LocalDate.now()) > 0)
            throw new FutureWorkException();
        
        this.actualDay = actualDay;
     }
}
