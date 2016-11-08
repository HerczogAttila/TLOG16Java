/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.YearMonth;
import java.util.List;

/**
 *
 * @author Attila
 */
public class WorkMonth {
    private List<WorkDay> days;
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;
}
