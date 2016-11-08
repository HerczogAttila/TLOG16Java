/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Attila
 */
public class TimeLogger {
    private List<WorkMonth> months;
    
    public TimeLogger() {
        months = new ArrayList<>();
    }

    public List<WorkMonth> getMonths() {
        return months;
    }

    public boolean isNewMonth(WorkMonth month) {
        return !months.contains(month);
    }

    public void addMonth(WorkMonth month) {
        if(isNewMonth(month))
            months.add(month);
    }
}
