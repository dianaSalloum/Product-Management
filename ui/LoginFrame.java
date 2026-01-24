package ui;

import model.AllException;
import model.FactoryController;
import io.ErrorLogger;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JRadioButton managerBtn;
    private JRadioButton supervisorBtn;

    public LoginFrame() {
        setTitle("Login");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//middle of the screen
        JPanel panel = new JPanel(new BorderLayout(10, 10));//منحدد الحدود بين الي منضيفن10
        panel.setBackground(new Color(25, 25, 49));
        JLabel title = new JLabel("Select User Type", JLabel.CENTER);
        title.setForeground(new Color(229, 231, 235));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);
        managerBtn = new JRadioButton("Manager");
        managerBtn.setForeground(new Color(180, 140, 255));
        managerBtn.setBackground(new Color(25, 25, 49));
        managerBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        managerBtn.setFocusPainted(false);
        supervisorBtn = new JRadioButton("Supervisor");
        supervisorBtn.setForeground(new Color(180, 140, 255));
        supervisorBtn.setBackground(new Color(25, 25, 49));
        supervisorBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        supervisorBtn.setFocusPainted(false);
        //بيتعامل معن كلوجيك وبخلي التحديد لشغلة وحدة بس
        ButtonGroup group = new ButtonGroup();
        group.add(managerBtn);
        group.add(supervisorBtn);
        JPanel center = new JPanel();
        center.setBackground(new Color(23, 23, 47));
        center.add(managerBtn);
        center.add(supervisorBtn);
        panel.add(center, BorderLayout.CENTER);
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(130, 58, 240));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(loginBtn, BorderLayout.SOUTH);
        loginBtn.addActionListener(e -> {
                handleLogin();
        });
        add(panel);
    }

    private void handleLogin() {
        //Make sure to select the user type firstً
        if (!managerBtn.isSelected() && !supervisorBtn.isSelected()) {
            DialogUtils.showError(this, "Please select a user type!");
            return;
        }

        boolean isManager = managerBtn.isSelected();
        String role = isManager ? "Manager" : "SUPERVISOR";

        //Request the password using a pop-up window
        JPasswordField pf = new JPasswordField(15); //Black dots appear instead of the characters typed by the user

        pf.setBackground(new Color(25, 25, 49));
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


        JPanel customPanel = new JPanel();
        customPanel.setBackground(new Color(25, 25, 49));
        customPanel.setLayout(new BorderLayout(5, 5));
        customPanel.add(new JLabel("Enter Password:"), BorderLayout.NORTH);
        customPanel.getComponent(0).setForeground(Color.WHITE);
        customPanel.add(pf, BorderLayout.CENTER);
        int selection = JOptionPane.showConfirmDialog(
                this,
                customPanel,
                "Hope you enjoy in our program 😊",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        //Check the "OK" button press and the password
        if (selection == JOptionPane.OK_OPTION) {
            String inputPassword = new String(pf.getPassword());

            if (FactoryController.getInstance().checkPassword(role, inputPassword)) {
                new MainFrame(isManager).setVisible(true);
                dispose();
            } else {
                DialogUtils.showError(this, "Invalid Password!");
            }
        }
    }
}