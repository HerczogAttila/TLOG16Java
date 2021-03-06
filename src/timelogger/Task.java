/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

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
@lombok.Getter
@lombok.Setter
public final class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;
    
    /**
     * @param taskId 
     * @exception NoTaskIdException
     * @exception InvalidTaskIdException
     * @exception NotExpectedTimeOrderException
     * @exception EmptyTimeFieldException
     */
    public Task(String taskId) {
        this.taskId = taskId;
        comment = "";
        
        if(this.taskId.isEmpty())
            throw new NoTaskIdException();

        if(!isValidTaskId())
            throw new InvalidTaskIdException(taskId);
    }
    
    /**
     * @param taskId
     * @param comment
     * @param startHour
     * @param startMinute
     * @param endHour
     * @param endMinute 
     * @exception NoTaskIdException
     * @exception InvalidTaskIdException
     * @exception NotExpectedTimeOrderException
     * @exception EmptyTimeFieldException
     */
    public Task(String taskId, String comment, int startHour, int startMinute, int endHour, int endMinute) {
        this(taskId, comment, startHour + ":" + startMinute, endHour + ":" + endMinute);
    }
    
    /**
     * @param taskId
     * @param comment
     * @param startTime
     * @param endTime 
     * @exception NoTaskIdException
     * @exception InvalidTaskIdException
     * @exception NotExpectedTimeOrderException
     * @exception EmptyTimeFieldException
     */
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
    
    /**
     * Convert time string to LocalTime.
     * @param time
     * @return 
     */
    public static LocalTime stringToLocalTime(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return LocalTime.of(h, m);
    }
    
    /**
     * @return boolean
     */
    public boolean isValidTaskId() {
        //return taskId.matches("^\\d{4}|^LT-\\d{4}");
        return isValidRedmineTaskId() || isValidLTTaskId();
    }
    
    /**
     * @return boolean
     * @exception NoTaskIdException
     */
    public boolean isValidRedmineTaskId() {
        if(taskId.isEmpty())
            throw new NoTaskIdException();
        
        return taskId.matches("^\\d{4}");
    }
    
    /**
     * @return boolean
     * @exception NoTaskIdException
     */
    public boolean isValidLTTaskId() {
        if(taskId.isEmpty())
            throw new NoTaskIdException();
        
        return taskId.matches("^LT-\\d{4}");
    }
    
    /**
     * Time interval should be the multiple of the quarter hour.
     * @return 
     */
    public boolean isMultipleQuarterHour() {
        return (getMinPerTask() % 15 == 0);
    }
    
    /**
     * Calculate the duration of a task. If start or end time is null, then return zero.
     * @return long
     */
    public long getMinPerTask() {
        if(startTime == null || endTime == null)
            return 0;
        
        return MINUTES.between(startTime, endTime);
    }
    
    /**
     * @return String
     */
    @Override
    public String toString() {
        return taskId + "\t" + startTime + "\t" + endTime + "\t" + comment;
    }

    /**
     * Set the start time
     * @param hour
     * @param min 
     */
    public void setStartTime(int hour, int min) {
        startTime = LocalTime.of(hour, min);
    }

    /**
     * Set the start time.
     * @param startTime 
     */
    public void setStartTime(String startTime) {
        this.startTime = stringToLocalTime(startTime);
    }

    /**
     * Set the end time.
     * @param hour
     * @param min 
     */
    public void setEndTime(int hour, int min) {
        endTime = LocalTime.of(hour, min);
    }

    /**
     * Set the end time.
     * @param endTime 
     */
    public void setEndTime(String endTime) {
        this.endTime = stringToLocalTime(endTime);
    }

    /**
     * @return String The task id.
     * @exception NoTaskIdException 
     */
    public String getTaskId() {
        if(this.taskId.isEmpty())
            throw new NoTaskIdException();
        
        return taskId;
    }
}
