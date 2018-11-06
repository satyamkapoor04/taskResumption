/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

/**
 *
 * @author satyam
 */
public class PastRetrieval {
    private String program;
    private String title;
    private String start;
    private String end;
    
    public PastRetrieval (String p, String t, String s, String e) {
        this.program = p;
        this.title = t;
        this.start = s;
        this.end = e;
    }
    
    public String getProgram () {
        return this.program;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public String getStart () {
        return this.start;
    }
    
    public String getEnd () {
        return this.end;
    }
    
}
