/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Attila
 */
public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalTime actualDay;
    private long sumPerDay;
    
    public WorkDay() {
        requiredMinPerDay = 450;    //7.5 hour
    }
}
