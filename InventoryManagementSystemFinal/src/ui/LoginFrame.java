package ui;
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

        JPanel panel = new JPanel(new BorderLayout(10,10));//منحدد الحدود بين الي منضيفن10
        panel.setBackground(new Color(25,25,49));
        JLabel title = new JLabel("Select User Type", JLabel.CENTER);
        title.setForeground(new Color(229,231,235));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));
        panel.add(title, BorderLayout.NORTH);

        managerBtn = new JRadioButton("Manager");
        managerBtn.setForeground(new Color(180,140,255));
        managerBtn.setBackground(new Color(25,25,49));
        managerBtn.setFont(new Font("Segoe UI",Font.PLAIN,16));
        managerBtn.setFocusPainted(false);
        supervisorBtn = new JRadioButton("Supervisor");
        supervisorBtn.setForeground(new Color(180,140,255));
        supervisorBtn.setBackground(new Color(25,25,49));
        supervisorBtn.setFont(new Font("Segoe UI",Font.PLAIN,16));
        supervisorBtn.setFocusPainted(false);
        //بيتعامل معن كلوجيك وبخلي التحديد لشغلة وحدة بس
        ButtonGroup group = new ButtonGroup();
        group.add(managerBtn);
        group.add(supervisorBtn);
        JPanel center = new JPanel();
        center.setBackground(new Color(23,23,47));
        center.add(managerBtn);
        center.add(supervisorBtn);
        panel.add(center, BorderLayout.CENTER);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(130,58,240));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI",Font.BOLD,14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        panel.add(loginBtn, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> handleLogin());

        add(panel);
    }

    private void handleLogin() {
        if (!managerBtn.isSelected() && !supervisorBtn.isSelected()) {
            //this موقع الاوبشن بين
            // بنص الشي الي بتطلع فيه
            DialogUtils.showError(this,"please select a user type!");
            return;
        }

        boolean isManager = managerBtn.isSelected();

        new MainFrame(isManager).setVisible(true);
        dispose();
    }
}
