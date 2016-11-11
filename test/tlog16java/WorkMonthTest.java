/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import timelogger.exceptions.NotNewDateException;
import timelogger.exceptions.NotTheSameMonthException;
import timelogger.exceptions.WeekendNotEnabledException;

/**
 *
 * @author Attila
 */
public class WorkMonthTest {
    
    //test case 1
    @Test
    public void testSumPerMonth() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day1 = new WorkDay(420, 2016, 9, 1);
        day1.addTask(new Task("1234", "", "7:30", "8:45"));
        
        WorkDay day2 = new WorkDay(420, 2016, 9, 2);
        day2.addTask(new Task("1234", "", "8:45", "9:45"));
        
        month.addWorkDay(day1);
        month.addWorkDay(day2);
        
        assertEquals(month.getSumPerMonth(), 135);
    }
    
    //test case 2
    @Test
    public void testSumPerMonth2() {
        WorkMonth month = new WorkMonth(2016, 9);
        assertEquals(month.getSumPerMonth(), 0);
    }
    
    //test case 3
    @Test
    public void testGetExtraMinPerMonth() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day1 = new WorkDay(420, 2016, 9, 1);
        day1.addTask(new Task("1234", "", "7:30", "8:45"));
        
        WorkDay day2 = new WorkDay(420, 2016, 9, 2);
        day2.addTask(new Task("1234", "", "8:45", "9:45"));
        
        month.addWorkDay(day1);
        month.addWorkDay(day2);
        
        assertEquals(month.getExtraMinPerMonth(), -705);
    }
    
    //test case 4
    @Test
    public void testGetExtraMinPerMonth2() {
        WorkMonth month = new WorkMonth(2016, 9);
        assertEquals(month.getExtraMinPerMonth(), 0);
    }
    
    //test case 5
    @Test
    public void testGetRequiredMinPerMonth() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(420, 2016, 9, 1));
        month.addWorkDay(new WorkDay(420, 2016, 9, 2));
        
        assertEquals(month.getRequiredMinPerMonth(), 840);
    }
    
    //test case 6
    @Test
    public void testGetRequiredMinPerMonth2() {
        WorkMonth month = new WorkMonth(2016, 9);
        assertEquals(month.getRequiredMinPerMonth(), 0);
    }
    
    //test case 7
    @Test
    public void testGetSumPerMonth() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day1 = new WorkDay(450, 2016, 9, 9);
        day1.addTask(new Task("1234", "", "7:30", "8:45"));
                
        month.addWorkDay(day1);
        
        assertEquals(month.getSumPerMonth(), day1.getSumPerDay());
    }
    
    //test case 8
    @Test
    public void testGetSumPerMonth2() {
        WorkMonth month = new WorkMonth(2016, 8);
        WorkDay day1 = new WorkDay(450, 2016, 8, 28);
        day1.addTask(new Task("1234", "", "7:30", "8:45"));
                
        month.addWorkDay(day1, true);
        
        assertEquals(month.getSumPerMonth(), day1.getSumPerDay());
    }
    
    //test case 9
    @Test(expected = WeekendNotEnabledException.class)
    public void testGetSumPerMonth3() {
        WorkMonth month = new WorkMonth(2016, 8);
        WorkDay day1 = new WorkDay(450, 2016, 8, 28);
        day1.addTask(new Task("1234", "", "7:30", "8:45"));
                
        month.addWorkDay(day1);
    }
    
    //test case 10
    @Test
    public void testIsSameMonth() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        WorkDay day = new WorkDay(450, 2016, 9, 2);
        assertEquals(month.isSameMonth(day), true);
    }
    
    //test case 11
    @Test
    public void testIsntSameMonth() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        WorkDay day = new WorkDay(450, 2016, 8, 30);
        assertEquals(month.isSameMonth(day), false);
    }
    
    //test case 12
    @Test
    public void testIsSameMonth2() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day = new WorkDay(450, 2016, 9, 1);
        assertEquals(month.isSameMonth(day), true);
    }
    
    //test case 13
    @Test
    public void testIsSameMonth3() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day = new WorkDay(450, 2016, 8, 1);
        assertEquals(month.isSameMonth(day), false);
    }
    
    //test case 14
    @Test
    public void testIsntNewDate() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        WorkDay day = new WorkDay(450, 2016, 9, 1);
        assertEquals(month.isNewDate(day), false);
    }
    
    //test case 15
    @Test
    public void testIsNewDate() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        WorkDay day = new WorkDay(450, 2016, 9, 2);
        assertEquals(month.isNewDate(day), true);
    }
    
    //test case 16
    @Test
    public void testIsNewDate2() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day = new WorkDay(450, 2016, 9, 1);
        assertEquals(month.isNewDate(day), true);
    }
    
    //test case 17
    @Test(expected = NotNewDateException.class)
    public void testNotNewDate() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
    }
    
    //test case 18
    @Test
    public void testGetRequiredMinPerMonth3() {
        WorkMonth month = new WorkMonth(2016, 9);
        WorkDay day1 = new WorkDay(450, 2016, 9, 1);
        WorkDay day2 = new WorkDay(450, 2016, 9, 2);
        
        month.addWorkDay(day1);
        month.addWorkDay(day2);
        
        assertEquals(month.getRequiredMinPerMonth(), day1.getRequiredMinPerDay() + day2.getRequiredMinPerDay());
    }
    
    //test case 19
    @Test(expected = NotTheSameMonthException.class)
    public void test() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        month.addWorkDay(new WorkDay(450, 2016, 8, 30));
    }
    
    @Test
    public void testIsntSameMonth2() {
        WorkMonth month = new WorkMonth(2016, 9);
        month.addWorkDay(new WorkDay(450, 2016, 9, 1));
        WorkDay day = new WorkDay(450, 2016, 8, 1);
        assertEquals(month.isSameMonth(day), false);
    }
}
