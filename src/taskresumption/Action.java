/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author satyam
 */
public class Action implements Serializable {
    
    private String program;
    private String title;
    private Date startDate;
    
    
    public Action (String p, String t, Date s) {
        this.program = p;
        this.title = t;
        this.startDate = s;
    }
    
    
    
    public void setProgram (String program) {
        this.program = program;
    }
    
    public String getProgram () {
        return this.program;
    }
    
    public void setTitle (String title) {
        this.title = title;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public void setStartDate (Date date) {
        this.startDate = date;
    }
    
    public Date getStartDate () {
        return this.startDate;
    }
}
