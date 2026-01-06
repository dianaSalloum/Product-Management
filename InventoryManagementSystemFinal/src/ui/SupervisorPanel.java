package ui;

import io.ErrorLogger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import model.Mission;

public class SupervisorPanel extends JPanel {

    private JTextField missionIdField;
    private JTextField clientField;
    private JTextField quantityField;

    public SupervisorPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Create Mission", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));

        form.add(new JLabel("Mission ID:"));
        missionIdField = new JTextField();
        form.add(missionIdField);

        form.add(new JLabel("Client Name:"));
        clientField = new JTextField();
        form.add(clientField);

        form.add(new JLabel("Ordered Quantity:"));
        quantityField = new JTextField();
        form.add(quantityField);

        JButton createButton = new JButton("Create Mission");
        createButton.addActionListener(e -> {
            try {
                int missionId = Integer.parseInt(missionIdField.getText());
                String name = clientField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                Mission mission = new Mission(null ,quantity ,name, LocalDate.now(),
                        LocalDate.now().plusDays(7),null);

                JOptionPane.showMessageDialog(this, "Mission craeted seccusfully");


            }
            catch(Exception ee){
                ErrorLogger.logError(ee);
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }

        });
        form.add(new JLabel()); // empty
        form.add(createButton);

        add(form, BorderLayout.CENTER);
    }
}