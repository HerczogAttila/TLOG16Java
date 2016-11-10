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
public class NotNewDateException extends RuntimeException {
    
    public NotNewDateException() {
        super("Not new date!");
    }
}
