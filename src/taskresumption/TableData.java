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
public class TableData {
    
    private Boolean delete;
    private String application;
    private String file;
    private String start;
    private String end;
    private Boolean resume;
    
    public TableData (Boolean d, String a, String f, String s, String e, Boolean r) {
        this.delete = d;
        this.application = a;
        this.file = f;
        this.start = s;
        this.end = e;
        this.resume = r;
    }
    
    public boolean getDelete () {
        return this.delete;
    }
    
    public String getApplication () {
        return this.application;
    }
    
    public String getFile () {
        return this.file;
    }
    
    public boolean getResume () {
        return this.resume;
    }
    
    public String getStart () {
        return this.start;
    }
    
    public String getEnd () {
        return this.end;
    }

    void setDelete(boolean b) {
         this.delete = b;
    }
    
    void setResume (boolean b) {
        this.resume = b;
    }
}
