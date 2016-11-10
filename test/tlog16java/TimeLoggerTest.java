/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import timelogger.exceptions.NotNewMonthException;

/**
 *
 * @author Attila
 */
public class TimeLoggerTest {
    
    //test case 1
    @Test
    public void testGetSumPerMonth() {
        TimeLogger tl = new TimeLogger();
        WorkMonth month = new WorkMonth(2016, 4);
        WorkDay day = new WorkDay(450, 2016, 4, 1);
        Task t = new Task("1234", "", "7:30", "10:30");
        
        day.addTask(t);
        month.addWorkDay(day);
        tl.addMonth(month);
        
        assertEquals(tl.getMonths().get(0).getSumPerMonth(), t.getMinPerTask());
    }
    
    //test case 2
    @Test(expected = NotNewMonthException.class)
    public void testNotNewMonthException() {
        TimeLogger tl = new TimeLogger();
        tl.addMonth(new WorkMonth(2016, 4));
        tl.addMonth(new WorkMonth(2016, 4));
    }
    
    //test case 3
    @Test
    public void testIsNewMonth() {
        TimeLogger tl = new TimeLogger();
        tl.addMonth(new WorkMonth(2016, 4));
        WorkMonth month = new WorkMonth(2016, 9);
        
        assertEquals(tl.isNewMonth(month), true);
    }
    
    //test case 4
    @Test
    public void testIsntNewMonth() {
        TimeLogger tl = new TimeLogger();
        tl.addMonth(new WorkMonth(2016, 4));
        WorkMonth month = new WorkMonth(2016, 4);
        
        assertEquals(tl.isNewMonth(month), false);
    }
}
