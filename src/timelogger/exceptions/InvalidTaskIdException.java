/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger.exceptions;

/**
 *
 * @author Attila
 */
public class InvalidTaskIdException extends RuntimeException {
    private String taskId;
    
    public InvalidTaskIdException(String taskId) {
        super("Invalid task id: " + taskId + "!");
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}
