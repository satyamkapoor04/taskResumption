/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import javax.swing.JButton;

/**
 *
 * @author satyam
 */
public class KnnClass {
    private String program;
    private String title;
    private String executable;
    private String file;
    private int count;
    private JButton launch;
    
    public KnnClass (String p, String t, String e, String f, int c) {
        this.program = p;
        this.title = t;
        this.executable = e;
        this.file = f;
        this.count = c;
    }
    
    public KnnClass (String p, String t, String e, String f, JButton l) {
        this.program = p;
        this.title = t;
        this.executable = e;
        this.file = f;
        this.launch = l;
        launch.setText ("Launch");
    }
    
    public void setProgram (String p) {
        this.program = p;
    }
    
    
    
    public String getProgram () {
        return this.program;
    }
    
    public void setTitle (String t) {
        this.title = t;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public void setExecutable (String e) {
        this.executable = e;
    }
    
    public String getExecutable () {
        return this.executable;
    }
    
    public void setFile (String f) {
        this.file = f;
    }
    
    public String getFile () {
        return this.file;
    }
    
    public void setCount (int c) {
        this.count = c;
    }
    
    public int getCount () {
        return this.count;
    }
    
    public JButton getLaunch () {
        return this.launch;
    }
    
}
