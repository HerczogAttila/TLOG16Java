/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.InvalidTaskIdException;
import timelogger.exceptions.NoTaskIdException;
import timelogger.exceptions.NotExpectedTimeOrderException;

/**
 *
 * @author Attila
 */
public final class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;
    
    public Task(String taskId) {
        this.taskId = taskId;
        comment = "";
        
        if(this.taskId.isEmpty())
            throw new NoTaskIdException();

        if(!isValidTaskId())
            throw new InvalidTaskIdException(taskId);
    }
    public Task(String taskId, String comment, int startHour, int startMinute, int endHour, int endMinute) {
        this(taskId, comment, startHour + ":" + startMinute, endHour + ":" + endMinute);
    }
    public Task(String taskId, String comment, String startTime, String endTime) {
        this.taskId = taskId;
        this.comment = comment;
        
        if(this.taskId.isEmpty())
            throw new NoTaskIdException();
                
        if(!isValidTaskId())
            throw new InvalidTaskIdException(taskId);
        
        try {
            this.startTime = stringToLocalTime(startTime);
            this.endTime = stringToLocalTime(endTime);
        } catch(Exception e) { System.out.println(e); }

        if(this.startTime != null & this.endTime != null) {
            if(this.startTime.compareTo(this.endTime) > 0) {
                throw new NotExpectedTimeOrderException(this.startTime, this.endTime);
            }
        } else {
            throw new EmptyTimeFieldException();
        }
    }
    
    public static LocalTime stringToLocalTime(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return LocalTime.of(h, m);
    }
    
    public boolean isValidTaskId() {
        //return taskId.matches("^\\d{4}|^LT-\\d{4}");
        return isValidRedmineTaskId() || isValidLTTaskId();
    }
    public boolean isValidRedmineTaskId() {
        if(taskId.isEmpty())
            throw new NoTaskIdException();
        
        return taskId.matches("^\\d{4}");
    }
    public boolean isValidLTTaskId() {
        if(taskId.isEmpty())
            throw new NoTaskIdException();
        
        return taskId.matches("^LT-\\d{4}");
    }
    
    public boolean isMultipleQuarterHour() {
        return (getMinPerTask() % 15 == 0);
    }
    
    public long getMinPerTask() {
        if(startTime == null || endTime == null)
            return 0;
        
        return MINUTES.between(startTime, endTime);
    }
    
    @Override
    public String toString() {
        return taskId + "\t" + startTime + "\t" + endTime + "\t" + comment;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getTaskId() throws NoTaskIdException {
        if(this.taskId.isEmpty())
            throw new NoTaskIdException();
        
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
