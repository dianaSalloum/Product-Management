
package ui;
import io.ErrorLogger;
import io.ErrorsReader;
import model.FactoryController;
import model.Mission;
import model.Item;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AllException;
import model.LineState;
import model.ProductLine;


public class ManagerPanel extends JPanel {
    private FactoryController controller;
    private JTextArea missionsArea;
    private JTextArea inventoryArea;
    private JTextArea errorsArea;
    private JProgressBar progressBar;

    //styling the buttons
    private void styleButton(JButton btn,Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    }


//constructor
    public ManagerPanel(FactoryController controller){
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(31,31,46));
        tabs.setForeground(new Color(180,140,255));
        tabs.setFont(new Font("Segoe UI",Font.BOLD,13));
        tabs.addTab("Missions", createMissionsPanel());
        tabs.addTab("Inventory", createInventoryPanel());
        tabs.addTab("Errors", createErrorsPanel());
        tabs.addTab("Production Lines", createLinesPanel());
        tabs.addTab("Progress", createProgressPanel());
        tabs.setOpaque(true);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createMissionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        missionsArea = new JTextArea();
        missionsArea.setBackground(new Color(38,38,58));
        missionsArea.setForeground(new Color(229,231,235));
        missionsArea.setCaretColor(Color.WHITE);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn,new Color(58, 164, 204));
        refreshBtn.addActionListener(e -> refreshMissions());
        JScrollPane scrollPane=new JScrollPane(missionsArea);

        scrollPane.getViewport().setBackground(new Color(31,31,46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        refreshMissions();
        return panel;
    }


    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        inventoryArea= new JTextArea();
        inventoryArea.setBackground(new Color(38,38,58));
        inventoryArea.setForeground(new Color(229,231,235));
        inventoryArea.setCaretColor(Color.WHITE);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn,new Color(58, 164, 204));
        refreshBtn.addActionListener(e -> refreshInventory());
        JScrollPane scrollPane=new JScrollPane(inventoryArea);
        scrollPane.getViewport().setBackground(new Color(31,31,46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        refreshInventory();
        return panel;
    }

    private JPanel createErrorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        errorsArea = new JTextArea();
        errorsArea.setBackground(new Color(38,38,58));
        errorsArea.setForeground(new Color(229,231,235));
        errorsArea.setCaretColor(Color.WHITE);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn,new Color(58, 164, 204));
        refreshBtn.addActionListener(e -> refreshErrors());
        JScrollPane scrollPane=new JScrollPane(errorsArea);
        scrollPane.getViewport().setBackground(new Color(31,31,46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        refreshErrors();
        return panel;
    }


    private JPanel createLinesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        JLabel lineIdLabel = new JLabel("Line Name:");
        lineIdLabel.setForeground(new Color(229,231,235));
        lineIdLabel.setFont(new Font("Segoe UI",Font.PLAIN,13));
        JTextField lineIdField = new JTextField(5);
        lineIdField.setBackground(new Color(38,38,58));
        lineIdField.setForeground(new Color(229,231,235));
        lineIdField.setCaretColor(Color.WHITE);
        JButton startBtn = new JButton("START");
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(startBtn,new Color(34,197,94));
        JButton stopBtn = new JButton("STOP");
        stopBtn.setFocusPainted(false);
        stopBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(stopBtn,new Color(239,68,68));
        JTextArea linesArea = new JTextArea();
        linesArea.setBackground(new Color(38,38,58));
        linesArea.setForeground(new Color(229,231,235));
        linesArea.setCaretColor(Color.WHITE);
        JTextField nameField = new JTextField(15);
        nameField.setBackground(new Color(38,38,58));
        nameField.setForeground(new Color(229,231,235));
        nameField.setCaretColor(Color.WHITE);
        JButton addBtn = new JButton("Add Line");
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(addBtn,new Color(130,90,240));
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                controller.addProductLine(name);
                refreshLines(linesArea);
                nameField.setText("");
            }
        });
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(31,31,46));
        controlPanel.add(lineIdLabel);
        controlPanel.add(lineIdField);
        controlPanel.add(startBtn);
        controlPanel.add(stopBtn);


        JPanel top = new JPanel();
        top.setBackground(new Color(31,31,46));
        JLabel label=new JLabel("line ID:");
        label.setForeground(new Color(229,231,235));
        label.setFont(new Font("Segoe UI",Font.PLAIN,13));
        top.add(label);
        top.add(nameField);
        top.add(addBtn);
        JScrollPane scrollPane=new JScrollPane(linesArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        scrollPane.getViewport().setBackground(new Color(31,31,46));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(controlPanel,BorderLayout.AFTER_LINE_ENDS);
        refreshLines(linesArea);
        startBtn.addActionListener(e -> {
            try{
                int selectedLineId = Integer.parseInt(lineIdField.getText());
                controller.changeLineState(selectedLineId, LineState.ACTIVE);
                DialogUtils.showMessage(this, "Line " + selectedLineId + " activated");
                //here....
                refreshLines(linesArea);
            }catch(NumberFormatException ex){
                DialogUtils.showError(this,"please enter a valid line ID");
                ErrorLogger.logError(ex);
            }catch(AllException.ProductLineNotFoundException ee){
                DialogUtils.showError(this, "product line not found!");
                ErrorLogger.logError(ee);
            }

        });

        stopBtn.addActionListener(e -> {
            try {
                int selectedLineId = Integer.parseInt(lineIdField.getText());
                controller.changeLineState(selectedLineId, LineState.STOP);
            } catch (AllException.ProductLineNotFoundException ex) {
                ErrorLogger.logError(ex);
                DialogUtils.showError(this, "product line not found!");
            } catch(NumberFormatException ex){
                DialogUtils.showError(this, "Please enter a valid Line ID");
                ErrorLogger.logError(ex);
            }
            refreshLines(linesArea);
        });
        JLabel missionIdLabel = new JLabel("Mission ID:");
        missionIdLabel.setForeground(new Color(229,231,235));
        missionIdLabel.setFont(new Font("Segoe UI",Font.PLAIN,13));
        JTextField missionIdField = new JTextField(5);
        missionIdField.setBackground(new Color(38,38,58));
        missionIdField.setForeground(new Color(229,231,235));
        missionIdField.setCaretColor(Color.WHITE);
        JLabel lineIdLabel2 = new JLabel("Line ID:");
        lineIdLabel2.setForeground(new Color(229,231,235));
        lineIdLabel2.setFont(new Font("Segoe UI",Font.PLAIN,13));
        JTextField lineIdField2 = new JTextField(5);
        lineIdField2.setBackground(new Color(38,38,58));
        lineIdField2.setForeground(new Color(229,231,235));
        lineIdField2.setCaretColor(Color.WHITE);
        JButton assignBtn = new JButton("Assign Mission");
        assignBtn.setFocusPainted(false);
        assignBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(assignBtn,new Color(34,197,94));
        JPanel assignPanel = new JPanel();
        assignPanel.setBackground(new Color(31,31,46));
        assignPanel.add(missionIdLabel);
        assignPanel.add(missionIdField);
        assignPanel.add(lineIdLabel2);
        assignPanel.add(lineIdField2);
        assignPanel.add(assignBtn);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(31,31,46));

        topPanel.setOpaque(true);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(top);
        topPanel.add(assignPanel);


        panel.add(topPanel, BorderLayout.NORTH);

        assignBtn.addActionListener(e -> {
            try {
                int missionId = Integer.parseInt(missionIdField.getText());
                int lineId = Integer.parseInt(lineIdField.getText());

                controller.assignMissionToLine(missionId, lineId);

                DialogUtils.showMessage(this,
                        "Mission " + missionId + " assigned to Line " + lineId);

            }catch(AllException.ProductLineNotFoundException exe){
                DialogUtils.showError(this,"ProductLine not found!");
                ErrorLogger.logError(exe);
            }
            catch(AllException.MissionNotFoundException exe){
                DialogUtils.showError(this,"Mission not found!");
                ErrorLogger.logError(exe);
            }
            catch (NumberFormatException exe) {
                DialogUtils.showError(this,"Please enter a valid ID");
                ErrorLogger.logError(exe);
            }
        });



        return panel;
    }
    private JPanel createProgressPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setBackground(new Color(31,31,46));
        progressBar.setForeground(new Color(130,58,240));
        progressBar.setBorderPainted(false);
        JButton refreshBtn = new JButton("Refresh Progress");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn,new Color(58, 164, 204));
        refreshBtn.addActionListener(e -> refreshProgress());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }



    private void refreshErrors() {
        errorsArea.setText("");
        ErrorsReader.errorReader();

    }
    private void refreshMissions() {
        missionsArea.setText("");
        List<Mission> missions = controller.getAllMissions();
        for (Mission m : missions) {
            missionsArea.append(m.toString() + "\n");
        }
    }
    private void refreshLines(JTextArea area) {
        area.setText("");
        for (ProductLine line : controller.getProductLineList()) {
            area.append(line.toString() + "\n");
            area.append( " | Progress: " + String.format("%.2f", controller.showSpecificAccomplishLevel(line)) + "%" +"\n");
        }
    }
    private void refreshInventory() {
        inventoryArea.setText("");
        List<Item> items = controller.showItems();
        for (Item i : items) {
            inventoryArea.append(i.toString() + "\n");
        }
    }
    private void refreshProgress() {
        for (ProductLine line : controller.getProductLineList()) {
            if (line.getId() == 1) {
                int value = (int) controller.showSpecificAccomplishLevel(line);
                progressBar.setValue(value);
                progressBar.setString("Line " + line.getId() + ": " + value + "%");
            }
        }
    }



}