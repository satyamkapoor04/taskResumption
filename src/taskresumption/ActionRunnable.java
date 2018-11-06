/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author satyam
 */
public class ActionRunnable implements Runnable {
    
    public  HashMap<String,String> elementList;
    private SQLDatabaseHelper sqlDatabaseHelper;
    private  ArrayList<Action> temporaryUse;
    
    public ActionRunnable () {
        super();
        sqlDatabaseHelper = SQLDatabaseHelper.getInstance();
    }
    
    public void run () {
        try {
            Process process = Runtime.getRuntime().exec("cmd /c powershell \"gps | where {$_.MainWindowTitle } | select name,mainwindowTitle");
            BufferedReader reader = new BufferedReader (new InputStreamReader (process.getInputStream()));
            String line;
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            
            elementList = new HashMap<>();
            temporaryUse = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
               
                if (line.length() == 0)
                    break;
                
                String program;
                String title;
                
                int len = line.length();
                int i=0;
                while (i<len && line.charAt(i) != ' ')
                    i++;
                program = line.substring (0,i);
                while (i<len && line.charAt(i) == ' ')
                    i++;
                int j = len-1;
                while (j>=0 && line.charAt(j) == ' ')
                    j--;
                title = line.substring(i,j+1);
                
                elementList.put (program,title);
                
                
            }
            
            //System.out.println (elementList.size());
            
            
            //Make a text file with current time and all running process
           
            File file = new File ("output.data");
            if (!file.exists()) {
                file.createNewFile();
            }
         
            try {
                putInSQL (file);
            } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
            } catch (EOFException ex) {
                //Nothing Here. No worries if exception is raised...
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    putInLocalDatabase(file);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (EOFException ex) {
                    ex.printStackTrace();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
            }
            
            //Put new process from running to already created arrayList
            //Put processes which are not in running but in arrayList to database
            //Clear the processes that are gone to database
            
            //Run this thread at regular interval.
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        run();
        
    } 
    
    public void putInSQL (File file) throws ClassNotFoundException,EOFException,FileNotFoundException,Exception {
        FileInputStream fis = new FileInputStream (file);
        ObjectInputStream ois = new ObjectInputStream (fis);
        KeyValueClass keyValueClass = (KeyValueClass) ois.readObject();
        sqlDatabaseHelper.initialize();
        //System.out.println (keyValueClass.arrayList.size());
        /*for (Action action : keyValueClass.arrayList) {
            System.out.println (action.getProgram());
        }*/
        for (Action e : keyValueClass.arrayList) {
            boolean isPresent = false;
            for (Map.Entry<String,String> entry : elementList.entrySet()) {
                //System.out.println (entry.getKey());
                if (e.getProgram().equals(entry.getKey()) && e.getTitle().equals(entry.getValue())) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                //Put in SQL with start date and end date.
                sqlDatabaseHelper.putData (e.getProgram(), e.getTitle(), e.getStartDate(), keyValueClass.newDate, isPresent);
            } else {
                Action newAction = new Action (e.getProgram(),e.getTitle(),e.getStartDate());
                temporaryUse.add(newAction);
            }
        }
        //System.out.println (temporaryUse.size());
        sqlDatabaseHelper.closeConnection();
        fis.close();
        ois.close();
    }
    
    public void putInLocalDatabase (File file) throws ClassNotFoundException,EOFException,FileNotFoundException,Exception {
        
        
        FileOutputStream fout = new FileOutputStream (file);
        ObjectOutputStream oos = new ObjectOutputStream (fout);
        
        
        for (Map.Entry<String,String> entry : elementList.entrySet()) {
            //System.out.println (entry.getKey() + "-" + entry.getValue());
            boolean isPresent = false;
            for (Action action : temporaryUse) {
                if (action.getProgram().equals(entry.getKey()) && action.getTitle().equals(entry.getValue())) {
                    isPresent = true;
                }
            }
            
            if (!isPresent) {
                Action newAction = new Action (entry.getKey(),entry.getValue(),new Date());
                temporaryUse.add (newAction);
            }
        }
        
        //System.out.println (temporaryUse.size());
        KeyValueClass keyValueClass = new KeyValueClass (temporaryUse,new Date());
        oos.writeObject (keyValueClass);
        oos.flush();
        fout.close();
        oos.close();
    }
}
