
package ui;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.*;
import model.FactoryController;

public class MainFrame extends JFrame {

    public MainFrame(boolean isManager) {

        UIManager.put("TabbedPane.selected", new Color(31,31,46));
        UIManager.put("TabbedPane.background", new Color(25,25,35));
        UIManager.put("TabbedPane.foreground", new Color(229,231,235));
        UIManager.put("TabbedPane.highlight", new Color(130,58,240));
        UIManager.put("TabbedPane.shadow", new Color(20,20,30));
        setTitle("Production Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(31,31,46));
        tabs.setOpaque(true);
        JPanel supervisorPanel = new SupervisorPanel();

        FactoryController controller = FactoryController.getInstance();
        JPanel managerPanel = new ManagerPanel(controller);

        tabs.addTab("Supervisor", supervisorPanel);
        tabs.addTab("Manager", managerPanel);

        if (isManager) {
            tabs.setSelectedIndex(1); // Manager tab
        } else {
            tabs.setSelectedIndex(0); // Supervisor tab
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FactoryController.getInstance().saveState();
                System.exit(0);
            }
        });

        add(tabs);
    }
}