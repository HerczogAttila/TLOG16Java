/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import timelogger.exceptions.FutureWorkException;
import timelogger.exceptions.NegativeMinutesOfWorkException;
import timelogger.exceptions.NotMultipleQuarterHourException;
import timelogger.exceptions.NotSeparatedTimesException;

/**
 *
 * @author Attila
 */
public class WorkDayTest {
    
    //test case 1
    @Test
    public void testGetExtraMinutesPerDay() {
        WorkDay wd = new WorkDay(75);
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        assertEquals(wd.getExtraMinPerDay(), 0);
    }
    
    //test case 2
    @Test
    public void testGetExtraMinutesPerDay2() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        assertEquals(wd.getExtraMinPerDay(), -375);
    }
    
    //test case 3
    @Test
    public void testGetExtraMinutesPerDay3() {
        WorkDay wd = new WorkDay(60);
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        assertEquals(wd.getExtraMinPerDay(), 15);
    }
    
    //test case 4
    @Test
    public void testGetExtraMinutesPerDay4() {
        WorkDay wd = new WorkDay();
        assertEquals(wd.getExtraMinPerDay(), -wd.getRequiredMinPerDay());
    }
    
    //test case 5
    @Test(expected = NegativeMinutesOfWorkException.class)
    public void testNegativeMinutePerDay() {
        WorkDay wd = new WorkDay(-45);
        wd.getRequiredMinPerDay();
    }
    
    //test case 6
    @Test(expected = NegativeMinutesOfWorkException.class)
    public void testNegativeMinutePerDay2() {
        WorkDay wd = new WorkDay(-45);
    }
    
    //test case 7
    @Test(expected = FutureWorkException.class)
    public void testFutureWorkDay() {
        WorkDay wd = new WorkDay();
        wd.setActualDay(LocalDate.now().plusDays(5));
    }
    
    //test case 8
    @Test(expected = FutureWorkException.class)
    public void testFutureWorkDay2() {
        LocalDate date = LocalDate.now().plusDays(5);
        WorkDay wd = new WorkDay(0, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
    
    //test case 9
    @Test
    public void testGetSumPerDay() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        wd.addTask(new Task("1235", "", "8:45", "9:45"));
        assertEquals(wd.getSumPerDay(), 135);
    }
    
    //test case 10
    @Test
    public void testGetSumPerDay2() {
        WorkDay wd = new WorkDay();
        assertEquals(wd.getSumPerDay(), 0);
    }
    
    //test case 11
    @Test
    public void testGetSumPerDay3() {
        WorkDay wd = new WorkDay();
        Task t = new Task("1234", "", "8:00", "8:45");
        wd.addTask(t);
        assertEquals(wd.getSumPerDay(), t.getMinPerTask());
    }
    
    //test case 12
    @Test(expected = NotMultipleQuarterHourException.class)
    public void testNoMultipleQuarterHour() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:35", "8:45"));
    }
    
    //test case 13
    @Test
    public void testIsWeekDay() {
        WorkDay wd = new WorkDay(450, 2016, 11, 10);
        assertEquals(wd.isWeekDay(), true);
    }
    
    //test case 14
    @Test
    public void testIsWeekend() {
        WorkDay wd = new WorkDay(450, 2016, 11, 5);
        assertEquals(wd.isWeekDay(), false);
    }
    
    //test case 15
    @Test
    public void testEndTimeOfTheLastTask() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        wd.addTask(new Task("1235", "", "9:30", "11:45"));
        LocalTime lt = LocalTime.of(11, 45);
        assertEquals(wd.lastTaskEndTime(), lt);
    }
    
    //test case 16
    @Test
    public void testEndTimeOfTheLastTask2() {
        WorkDay wd = new WorkDay();
        assertEquals(wd.lastTaskEndTime(), null);
    }
    
    //test case 17
    @Test
    public void testIsSeparatedTime() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        Task t = new Task("1235", "", "8:45", "9:45");
        assertEquals(wd.isSeparatedTime(t), true);
    }
    
    //test case 18
    @Test
    public void testIsntSeparatedTime() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        Task t = new Task("1235", "", "8:30", "9:45");
        assertEquals(wd.isSeparatedTime(t), false);
    }
    
    //test case 19
    @Test
    public void testIsntSeparatedTime2() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "8:30", "9:45"));
        Task t = new Task("1235", "", "7:30", "8:45");
        assertEquals(wd.isSeparatedTime(t), false);
    }
    
    //test case 20
    @Test
    public void testIsntSeparatedTime3() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        Task t = new Task("1235", "", "7:30", "8:45");
        assertEquals(wd.isSeparatedTime(t), false);
    }
    
    //test case 21
    @Test(expected = NotSeparatedTimesException.class)
    public void testNotSeparatedTimes() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        wd.addTask(new Task("1235", "", "8:30", "9:45"));
    }
    
    @Test
    public void testIsntSeparatedTime4() {
        WorkDay wd = new WorkDay();
        wd.addTask(new Task("1234", "", "7:30", "8:45"));
        Task t = new Task("1235", "", "7:45", "8:15");
        assertEquals(wd.isSeparatedTime(t), false);
    }
}
