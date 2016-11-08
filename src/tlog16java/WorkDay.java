/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Attila
 */
public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    
    public WorkDay() { this(450); }
    public WorkDay(int requiredMinPerDay) {
        this(requiredMinPerDay, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    public WorkDay(int requiredMinPerDay, int year, int month, int day) {
        tasks = new ArrayList<>();
        this.requiredMinPerDay = requiredMinPerDay;
        actualDay = LocalDate.of(year, month, day);
    }
    public WorkDay(int requiredMinPerDay, String actualDay) {
        tasks = new ArrayList<>();
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = stringToLocalDate(actualDay);
    }

    public static LocalDate stringToLocalDate(String date) {
        String[] parts = date.split(".");
        int y = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int d = Integer.parseInt(parts[2]);
        return LocalDate.of(y, m, d);
    }

    public long getExtraMinPerDay() {
        return requiredMinPerDay - getSumPerDay();
    }
    public boolean isSeparatedTime(Task task) {
        return tasks.stream().noneMatch((k) -> (k.getStartTime().compareTo(task.getStartTime()) < k.getEndTime().compareTo(task.getStartTime()) ||
                k.getStartTime().compareTo(task.getEndTime()) < k.getEndTime().compareTo(task.getEndTime()))
        );
    }
    public void addTask(Task task) throws Exception {
        if(!task.isMultipleQuarterHour())
            throw new Exception("Isn't multiple quarter hour!");

        if(!isSeparatedTime(task))
            throw new Exception("The task time intervals have common parts!");

        tasks.add(task);
    }
    public boolean isWeekday() {
        return !(actualDay.getDayOfWeek() == DayOfWeek.SUNDAY ||
                actualDay.getDayOfWeek() == DayOfWeek.SATURDAY);
    }

    public long getSumPerDay() {
        int minPerTask = 0;
        for(Task k : tasks) {
            minPerTask += k.getMinPerTask();
        }

        return minPerTask;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public void setActualDay(LocalDate actualDay) {
        this.actualDay = actualDay;
     }
}
