/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.util.ArrayList;
import java.util.List;
import timelogger.exceptions.NotNewMonthException;

/**
 *
 * @author Attila
 */
@lombok.Getter
public class TimeLogger {
    private final List<WorkMonth> months;
    
    /**
     * Default Constructor.
     */
    public TimeLogger() {
        months = new ArrayList<>();
    }

    /**
    * Decides, if this month already exists or not.
    * @param month 
    * @return boolean
    */
    public boolean isNewMonth(WorkMonth month) {
        return months.stream().noneMatch((d) -> (d.getDate().getMonthValue() == month.getDate().getMonthValue() &&
                d.getDate().getYear() == month.getDate().getYear()));
    }

    /**
     * Adds a new month to the months list if it is new.
     * @param month 
     * @exception NotNewMonthException
     */
    public void addMonth(WorkMonth month) {
        if(!isNewMonth(month))
            throw new NotNewMonthException();
        
        months.add(month);
    }
}
