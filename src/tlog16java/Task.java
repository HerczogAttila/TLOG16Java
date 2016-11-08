/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 *
 * @author Attila
 */
public class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;
    
    public Task(String taskId) {
        this.taskId = taskId;
    }
    public Task(String taskId, String comment, int startHour, int startMinute, int endHour, int endMinute) {
        this.taskId = taskId;
        this.comment = comment;
        startTime = LocalTime.of(startHour, startMinute);
        endTime = LocalTime.of(endHour, endMinute);
    }
    public Task(String taskId, String comment, String startTime, String endTime) {
        this.taskId = taskId;
        this.comment = comment;
        this.startTime = stringToLocalTime(startTime);
        this.endTime = stringToLocalTime(endTime);
    }
    
    public static LocalTime stringToLocalTime(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return LocalTime.of(h, m);
    }
    
    public boolean isValidTaskId() {
        return taskId.matches("^\\d{4}|^LT-\\d{4}");
    }
    
    public boolean isMultipleQuarterHour() {
        return (getMinPerTask() % 15 == 0);
    }
    
    public long getMinPerTask() {
        return MINUTES.between(startTime, endTime);
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStartTime(int hour, int min) {
        startTime = LocalTime.of(hour, min);
    }

    public void setStartTime(String startTime) {
        this.startTime = stringToLocalTime(startTime);
    }

    public void setEndTime(int hour, int min) {
        endTime = LocalTime.of(hour, min);
    }

    public void setEndTime(String endTime) {
        this.endTime = stringToLocalTime(endTime);
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }
}
