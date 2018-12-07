/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author satyam
 */
public class KnnTableModel extends AbstractTableModel {
    private String[] columns = {"Application","Title","Launch"};
    private ArrayList<KnnClass> data;
    
    public KnnTableModel (ArrayList<KnnClass> a) {
        super();
        this.data = a;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.get(rowIndex).getProgram();
            case 1:
                return data.get(rowIndex).getTitle();
            case 2:
                data.get(rowIndex).getLaunch().addActionListener(new ActionListener () {
                    public void actionPerformed (ActionEvent e) {
                        //System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);
                        //Perform Execution
                        if (data.get(rowIndex).getExecutable() == null) {
                            new Toast("Execute Manually!",3000).setVisible (true);
                        } else {
                            if (data.get(rowIndex).getFile() == null) {
                                try {
                                    Runtime.getRuntime().exec("cmd /c \"start \"" + data.get(rowIndex).getExecutable() + "\"\"");
                                    //System.out.println (data.get(rowIndex).getExecutable());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                try {
                                    if (data.get(rowIndex).getExecutable().equals("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")) {
                                        System.out.println (data.get(rowIndex).getFile());
                                        Runtime.getRuntime().exec("cmd /c powershell \"start '" + data.get(rowIndex).getExecutable() + "' \'" + data.get(rowIndex).getFile() + "\'\"");
                                    } else {
                                        Runtime.getRuntime().exec("cmd /c \"start \"" + data.get(rowIndex).getExecutable() + "\" \"" + data.get(rowIndex).getFile() + "\"\"");
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });
                return (JButton) data.get(rowIndex).getLaunch();
            default:
                
        }
        return null;
    }
    
    public String getColumnName (int col) {
        return columns[col];
    }
    
    public Class<?> getColumnClass (int col) {
        return getValueAt(0,col).getClass();
    }
    
    public boolean isCellEditable (int row, int col) {   
        return false;
    }
}
