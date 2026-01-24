package ui;

import java.awt.*;
import javax.swing.*;


import model.FactoryController;

public class MainFrame extends JFrame {

    private int previousTab = 0;
    private boolean isVerifying = false;

    public MainFrame(boolean isManager)  {

        UIManager.put("TabbedPane.selected", new Color(31, 31, 46));
        UIManager.put("TabbedPane.background", new Color(25, 25, 35));
        UIManager.put("TabbedPane.foreground", new Color(229, 231, 235));
        UIManager.put("TabbedPane.highlight", new Color(130, 58, 240));
        UIManager.put("TabbedPane.shadow", new Color(20, 20, 30));
        setTitle("Production Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(31, 31, 46));
        tabs.setOpaque(true);

        JPanel supervisorPanel = new SupervisorPanel();
        FactoryController controller = FactoryController.getInstance();
        JPanel managerPanel = new ManagerPanel(controller);

        tabs.addTab("Supervisor", supervisorPanel);
        tabs.addTab("Manager", managerPanel);

        previousTab = isManager ? 1 : 0;
        tabs.setSelectedIndex(previousTab);

        tabs.addChangeListener(e -> {
            if (isVerifying) {
                return;
            }

            int currentTab = tabs.getSelectedIndex();

            if (currentTab != previousTab) {
                isVerifying = true;

                String role = (currentTab == 1) ? "Manager" : "SUPERVISOR";
                if (showAuthDialog(role)) {
                    previousTab = currentTab;
                } else {
                    tabs.setSelectedIndex(previousTab);
                }

                isVerifying = false;
            }
        });

        add(tabs);
    }

    private boolean showAuthDialog(String role) {
        JPasswordField pf = new JPasswordField(15);
        pf.setBackground(new Color(23, 23, 47));
        pf.setForeground(Color.WHITE);
        pf.setCaretColor(Color.WHITE);

        //بيطلع المؤشر لحالو بالمربع
        pf.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                pf.requestFocusInWindow();
            }

            public void ancestorMoved(javax.swing.event.AncestorEvent e) {
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
            }
        });

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(25, 25, 49));
        JLabel label = new JLabel("Enter " + role + " Password:");
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);
        panel.add(pf, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Security Shield",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            return FactoryController.getInstance().checkPassword(role, password);
        }
        return false;
    }
}