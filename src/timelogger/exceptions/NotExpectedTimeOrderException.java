/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger.exceptions;

import java.time.LocalTime;

/**
 *
 * @author Attila
 */
@lombok.Getter
public class NotExpectedTimeOrderException extends RuntimeException {
    private LocalTime start, end;
    
    public NotExpectedTimeOrderException(LocalTime start, LocalTime end) {
        super("Not expected time order! start=" + start + ", end=" + end);
        this.start = start;
        this.end = end;
    }
}
