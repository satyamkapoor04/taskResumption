/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;

/**
 *
 * @author satyam
 */
public class GuiComponent {
    JFrame mainFrame;
    JPanel mainPanel;
    JButton clickButton;
    DateTimePicker startDate;
    DateTimePicker endDate;
    JTable table;
    CustomTableModel customTableModel;
    ArrayList<TableData> tableData;
    SQLDatabaseHelper sqlDatabaseHelper;
    TableColumnModel tableColumnModel;
    TableColumn column;
    
    public void go () {
        mainFrame = new JFrame ("Preview Action Pan");
        mainPanel = new JPanel (new MigLayout (""));
        
        clickButton = new JButton ("Submit");
        tableData = new ArrayList<>();
        customTableModel = new CustomTableModel(tableData);
        table = new JTable (customTableModel);
        
        float[] columnWidthPercentage = {5.0f,20.0f,50.0f,10.0f,10.0f,5.0f};
        
        
        JScrollPane scroller = new JScrollPane (table);
        scroller.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        clickButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                Date start = null;
                Date end = null;
                tableData.clear();
                tableData.trimToSize();
                sqlDatabaseHelper = SQLDatabaseHelper.getInstance();
                sqlDatabaseHelper.initialize();
                try {
                    start = DateTimePicker.simpleDateFormat.parse(startDate.getDateAndTime());
                    end = DateTimePicker.simpleDateFormat.parse (endDate.getDateAndTime());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                if (start.compareTo(end) > 0) {
                    new Toast ("Invalid Interval!",3000).setVisible(true);
                } else {
                    ArrayList<PastRetrieval> arrayList = sqlDatabaseHelper.getDataForGui(startDate.getDateAndTime(), endDate.getDateAndTime());
                    for (PastRetrieval p : arrayList)
                        tableData.add (new TableData (false,p.getProgram(),p.getTitle(),p.getStart(),p.getEnd(),true));
                    
                    int tw = table.getWidth();
                    tableColumnModel = table.getColumnModel();
                    for (int i=0; i<6; i++) {
                        column = tableColumnModel.getColumn(i);
                        int pWidth = Math.round (columnWidthPercentage[i]*tw);
                        column.setPreferredWidth(pWidth);
                    }
                  
                    table.revalidate();
                    table.repaint();
                }
            }
        });
        
        Date date = new Date();
        startDate = new DateTimePicker();
        startDate.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        startDate.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        
        endDate = new DateTimePicker();
        endDate.setFormats (DateFormat.getDateTimeInstance (DateFormat.SHORT, DateFormat.MEDIUM));
        endDate.setTimeFormat(DateFormat.getTimeInstance( DateFormat.MEDIUM));

        startDate.setDate(date);
        endDate.setDate (date);

        mainPanel.add(startDate,"split 3");
        mainPanel.add (endDate);
        mainPanel.add (clickButton,"wrap");
        mainPanel.add (scroller,"push, grow");
        
        mainFrame.getContentPane().add (mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocationRelativeTo (null);
        //mainFrame.pack();
        //mainFrame.setResizable(false);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setMinimumSize (new Dimension (700,400));
        mainFrame.setVisible(true);
    }
    
}
