/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author satyam
 */
public class KnnFrame {
    JFrame myFrame;
    JPanel mainPanel;
    JTable table;
    KnnTableModel knnTableModel;
    ArrayList<KnnClass> knnList;
    TableColumnModel tableColumnModel;
    TableColumn column;
    private float[] columnWidthPercentage = {20.0f,60.0f,20.0f};
    
    public void go (ArrayList<KnnClass> list) {
        myFrame = new JFrame("Recommendation");
        mainPanel = new JPanel (new MigLayout (""));
        knnList = list;
        
        knnTableModel = new KnnTableModel (list);
        table = new JTable(knnTableModel);
        table.getColumn ("Launch").setCellRenderer(new KnnFrame.JTableButtonRenderer());
        table.addMouseListener (new KnnFrame.JTableButtonMouseListener(table));
        
        
       JScrollPane scroller = new JScrollPane (table);
       scroller.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       
       mainPanel.add (scroller,"push, grow, wrap");
        
       myFrame.getContentPane().add (mainPanel);
       myFrame.setSize (700,180);
       myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       myFrame.setLocationRelativeTo(null);
       myFrame.setVisible (true);
       
       int tw = table.getWidth();
        tableColumnModel = table.getColumnModel();
        for (int i=0; i<3; i++) {
            column = tableColumnModel.getColumn(i);
            int pWidth = Math.round (columnWidthPercentage[i]*tw);
            column.setPreferredWidth(pWidth);
        }
        table.revalidate();
        table.repaint();
    }
    
    public class JTableButtonRenderer implements TableCellRenderer {		
    @Override 
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton)value;
        if (isSelected) {
         button.setForeground(table.getSelectionForeground());
         button.setBackground(table.getSelectionBackground());
        } else {
          button.setForeground(table.getForeground());
          button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;	
        }
    }
        
    public class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;
		
        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override 
        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            int row    = e.getY()/table.getRowHeight(); 

            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    JButton currentButton = (JButton) value;
                    int len = currentButton.getActionListeners().length;
                    for( int i=len-1; i>0; i-- ) {
                        currentButton.removeActionListener( currentButton.getActionListeners()[i] );
                    }
                    ((JButton)value).doClick();
                }
            }
        }
    }
}
