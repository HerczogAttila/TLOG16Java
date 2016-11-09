/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalTime;
import java.util.Scanner;

/**
 *
 * @author Attila
 */
public class TimeLoggerUI {
    private final TimeLogger tLogger;
    private final Scanner in;
    
    public TimeLoggerUI() {
        in = new Scanner(System.in);
        tLogger = new TimeLogger();
        test();
    }
    
    public void printMenu() {
        int command = -1;
        
        while(command != 0) {
            System.out.println("0: Exit");
            System.out.println("1: List months");
            System.out.println("2: List days");
            System.out.println("3: List tasks");
            System.out.println("4: Add new month");
            System.out.println("5: Add day to month");
            System.out.println("6: Start a task");
            System.out.println("7: Finish a specific task");
            System.out.println("8: Delete a task");
            System.out.println("9: Modify task");
            System.out.println("10: Statistics");
            
            command = in.nextInt();
            System.out.println();
            switch(command) {
                case 1: listMonths(); break;
                case 2: listDays(); break;
                case 3: listTasks(); break;
                case 4: addMonth(); break;
                case 5: addDay(); break;
                case 6: startTask(); break;
                case 7: finishTask(); break;
                case 8: deleteTask(); break;
                case 9: modifyTask(); break;
                case 10: statistics(); break;
            }
            System.out.println();
        }
    }
    
    private void test() {
        WorkMonth wm = new WorkMonth(2016, 1);
        WorkDay day = new WorkDay(450, 2016, 1, 1);
        Task t = new Task("1287", "teszt", "8:00", "9:00");
        Task t2 = new Task("1534");
        t2.setStartTime("9:00");
        
        try {
            day.addTask(t);
            day.addTask(t2);
        } catch(Exception e) { System.out.println(e); }
        
        wm.addWorkDay(day);
        day = new WorkDay(450, 2016, 1, 2);
        wm.addWorkDay(day, true);
        
        tLogger.addMonth(wm);
        
        wm = new WorkMonth(2016, 2);
        tLogger.addMonth(wm);
        
        wm = new WorkMonth(2016, 3);
        tLogger.addMonth(wm);
    }
    
    private void listMonths() {
        int c = 1;
        for(WorkMonth month : tLogger.getMonths()) {
            System.out.println(c + ". " + month.getDate());
            c++;
        }
    }
    
    private void listDays() {
        WorkMonth month = selectMonth();
        listDays(month);
    }
    
    private void listDays(WorkMonth month) {
        month.getDays().stream().forEach((day) -> {
            System.out.println(day.getActualDay());
        });
    }
    
    private void listTasks() {
        try {
            WorkDay day = selectDay();
            listTasks(day);
        } catch(Exception e) { System.out.println(e); }
    }
    
    private void listTasks(WorkDay day) {
        day.getTasks().stream().forEach((task) -> {
            System.out.println(task);
        });
    }
    
    private WorkMonth selectMonth() {
        listMonths();
        
        System.out.println("Select a number!");
        int i = in.nextInt() - 1;
        
        return tLogger.getMonths().get(i);
    }
    
    private WorkDay selectDay() throws Exception {
        WorkMonth month = selectMonth();
        listDays(month);
        
        System.out.println("Please enter the day!");
        int d = in.nextInt();
        
        for(WorkDay day : month.getDays()) {
            if(day.getActualDay().getDayOfMonth() == d)
                return day;
        }
        
        throw new Exception("The day is not found!");
    }
    
    private void addMonth() {
        System.out.println("Please enter the year!");
        int y = in.nextInt();
        System.out.println("Please enter the month!");
        int m = in.nextInt();
        
        tLogger.addMonth(new WorkMonth(y, m));
    }
    
    private void addDay() {
        WorkMonth month = selectMonth();
        int y = month.getDate().getYear();
        int m = month.getDate().getMonthValue();
        
        System.out.println("Please enter the day!");
        int d = in.nextInt();
        
        float h = 7.5f;
        System.out.println("Please enter the required working hours! (Default=7.5)");
        in.nextLine();
        String hour = in.nextLine();
        if(!hour.isEmpty())
            h = Float.valueOf(hour);
        //float h = in.nextFloat();
        
        WorkDay day = new WorkDay((int)(h * 60), y, m, d);
        month.addWorkDay(day);
    }
    
    private void startTask() {
        try {
            WorkDay day = selectDay();
            
            System.out.println("Please enter the task id!");
            String taskId = in.next();
            Task task = new Task(taskId);
            
            System.out.println("Please enter what you do! (comment)");
            String comment = in.next();
            task.setComment(comment);
            
            String lastTimeString = "";
            LocalTime lastTime = null;
            if(!day.getTasks().isEmpty()) {
                lastTime = day.lastTaskEndTime();
                lastTimeString = "(" + day.lastTaskEndTime() + ")";
            }
            
            System.out.println("Please enter the start time! " + lastTimeString);
            in.nextLine();
            String startTime = in.nextLine();
            if(startTime.isEmpty() && lastTime != null)
                startTime = lastTime + "";
            
            task.setStartTime(startTime);

            day.addTask(task);
        } catch(Exception e) { System.out.println(e); }
    }
    
    private void finishTask() {
        try {
            WorkDay day = selectDay();
            System.out.println();
            
            int c = 1;
            for(Task t : day.getTasks()) {
                if(t.getEndTime() == null) {
                    System.out.println(c + "\t" + t);
                }
                c++;
            }
            
            System.out.println("Select a number!");
            int i = in.nextInt() - 1;
            Task task = day.getTasks().get(i);
            
            System.out.println("Please enter the end time!");
            String endTime = in.next();
            
            task.setEndTime(endTime);            
        } catch(Exception e) { System.out.println(e); }
    }
    
    private void deleteTask() {
        try {
            WorkDay day = selectDay();
            System.out.println();
            
            int c = 1;
            for(Task t : day.getTasks()) {
                System.out.println(c + "\t" + t);
                c++;
            }
            
            System.out.println("Select a number!");
            int i = in.nextInt() - 1;
            
            System.out.println("Delete this task? (true/false)");
            boolean confirm = in.nextBoolean();
            
            if(confirm)
                day.getTasks().remove(i);
            
        } catch(Exception e) { System.out.println(e); }
    }
    
    private void modifyTask() {
        try {
            WorkDay day = selectDay();
            System.out.println();
            
            int c = 1;
            for(Task t : day.getTasks()) {
                System.out.println(c + "\t" + t);
                c++;
            }
            
            System.out.println("Select a number!");
            int i = in.nextInt() - 1;
            Task task = day.getTasks().get(i);
            
            System.out.println("Please enter the task id! (" + task.getTaskId() + ")");
            in.nextLine();
            String taskId = in.nextLine();
            if(!taskId.isEmpty())
                task.setTaskId(taskId);
            
            System.out.println("Please enter the comment! (" + task.getComment()+ ")");
            String comment = in.nextLine();
            if(!comment.isEmpty())
                task.setComment(comment);
            
            System.out.println("Please enter the start time! (" + task.getStartTime()+ ")");
            String startTime = in.nextLine();
            if(!startTime.isEmpty())
                task.setStartTime(startTime);
            
            System.out.println("Please enter the end time! (" + task.getEndTime()+ ")");
            String endTime = in.nextLine();
            if(!endTime.isEmpty())
                task.setEndTime(endTime);
            
        } catch(Exception e) { System.out.println(e); }
    }
    
    private void statistics() {
        WorkMonth month = selectMonth();
        
        System.out.println(month.getDate());
        System.out.println("Workdays: " + month.getDays().size());
        System.out.println("Required minute: " + month.getRequiredMinPerMonth());
        System.out.println("Minute: " + month.getSumPerMonth());
        System.out.println("Extra minute: " + month.getExtraMinPerMonth());

        month.getDays().stream().forEach((d) -> {
            System.out.println(d.getActualDay() + "\t" + d.getTasks().size() + "\t" + d.getRequiredMinPerDay() + "\t" + d.getSumPerDay()
                    + "\t" + d.getExtraMinPerDay());
        });
    }
}
