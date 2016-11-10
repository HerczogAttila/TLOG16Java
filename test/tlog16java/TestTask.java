/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.InvalidTaskIdException;
import timelogger.exceptions.NoTaskIdException;
import timelogger.exceptions.NotExpectedTimeOrderException;
import static tlog16java.Task.stringToLocalTime;

/**
 *
 * @author Attila
 */
public class TestTask {
    
    @Test(expected = NotExpectedTimeOrderException.class)
    public void testTimeOrder2() {
        Task t = new Task("1234", "", 8, 45, 7, 30);
    }
    
    //test case 1
    @Test(expected = NotExpectedTimeOrderException.class)
    public void testTimeOrder1() {
        Task t = new Task("1234", "", "8:45", "7:30");
    }

    //test case 2
    @Test(expected = EmptyTimeFieldException.class)
    public void testEmptyTimeField() {
        Task t = new Task("1234", "", "8:00", "");
    }
    
    //test case 3
    @Test
    public void testGetMinPerTask() {
        Task t = new Task("1234", "", "7:30", "8:45");
        assertEquals(t.getMinPerTask(), 75);
    }
    
    //test case 4
    @Test
    public void testValidRedmineTaskId() {
        Task t = new Task("1548");
        assertEquals(t.isValidRedmineTaskId(), true);
    }
    
    //test case 5
    @Test(expected = InvalidTaskIdException.class)
    public void testInvalidRedmineTaskId() {
        Task t = new Task("154858");
        assertEquals(t.isValidRedmineTaskId(), false);
    }
    
    //test case 6
    @Test(expected = NoTaskIdException.class)
    public void testWithoutTaskId() {
        Task t = new Task("");
        t.isValidRedmineTaskId();
    }
    
    //test case 7
    @Test
    public void testValidLTTaskId() {
        Task t = new Task("LT-1548");
        assertEquals(t.isValidLTTaskId(), true);
    }
    
    //test case 8
    @Test(expected = InvalidTaskIdException.class)
    public void testInvalidLTTaskId() {
        Task t = new Task("LT-154858");
        assertEquals(t.isValidLTTaskId(), false);
    }
    
    //test case 9
    @Test(expected = NoTaskIdException.class)
    public void testWithoutTaskId2() {
        Task t = new Task("");
        t.isValidLTTaskId();
    }
    
    //test case 10
    @Test
    public void testValidTaskId() {
        Task t = new Task("LT-1548");
        assertEquals(t.isValidTaskId(), true);
    }
    
    //test case 11
    @Test(expected = InvalidTaskIdException.class)
    public void testInvalidTaskId() {
        Task t = new Task("154858");
        assertEquals(t.isValidTaskId(), false);
    }
    
    //test case 12
    @Test(expected = NoTaskIdException.class)
    public void testWithoutTaskId3() {
        Task t = new Task("");
        t.isValidTaskId();
    }
    
    //test case 13
    @Test
    public void testIsMultipleQuarterHour() {
        Task t = new Task("1234", "", "7:30", "8:45");
        assertEquals(t.isMultipleQuarterHour(), true);
    }
    
    //test case 14
    @Test
    public void testIsMultipleQuarterHour2() {
        Task t = new Task("1234", "", "7:35", "8:45");
        assertEquals(t.isMultipleQuarterHour(), false);
    }
    
    //test case 15
    @Test(expected = NoTaskIdException.class)
    public void testGetTaskId() {
        Task t = new Task("");
        t.getTaskId();
    }
    
    //test case 16
    @Test
    public void testGetComment() {
        Task t = new Task("1234");
        assertEquals(t.getComment(), "");
    }
    
    //test case 17
    @Test(expected = InvalidTaskIdException.class)
    public void testInvalidTaskId4() {
        Task t = new Task("2534323");
    }
    
    //test case 18
    @Test(expected = NoTaskIdException.class)
    public void testWithoutTaskId4() {
        Task t = new Task("");
    }
    
    //test case 19
    @Test
    public void testStringToLocalTime() {
        LocalTime lt = stringToLocalTime("9:00");
        assertEquals(lt, LocalTime.of(9, 0));
    }
}
