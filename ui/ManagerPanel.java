package ui;

import io.ErrorLogger;
import io.ErrorsReader;

import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import model.*;


public class ManagerPanel extends JPanel {
    private Timer autoRefreshTimerProgress;
    private Timer autoRefreshTimerInventory;
    private Timer autoRefreshTimerLines;
    private Timer autoRefreshTimerMissions;
    private FactoryController controller;
    private JTextArea missionsArea;
    private JTextArea inventoryArea;
    private JTextArea errorsArea;
    private JTextArea linesArea;
    private JPanel progressContainer;


    //styling the buttons
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    }


    //constructor
    public ManagerPanel(FactoryController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(31, 31, 46));
        tabs.setForeground(new Color(180, 140, 255));
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.addTab("Missions", createMissionsPanel());
        tabs.addTab("Inventory", createInventoryPanel());
        tabs.addTab("Errors", createErrorsPanel());
        tabs.addTab("Production Lines", createLinesPanel());
        tabs.addTab("Progress", createProgressPanel());
        tabs.addTab("reports", createReportsPanel());
        tabs.setOpaque(true);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createProgressPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 31, 46));

        progressContainer = new JPanel();
        progressContainer.setLayout(new BoxLayout(progressContainer, BoxLayout.Y_AXIS));
        progressContainer.setBackground(new Color(31, 31, 46));

        JScrollPane scroll = new JScrollPane(progressContainer);
        scroll.getViewport().setBackground(new Color(31, 31, 46));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JButton refreshBtn = new JButton("Refresh Progress");
        styleButton(refreshBtn, new Color(56, 189, 248));
        autoRefreshTimerProgress = new Timer(500, e -> refreshProgress());
        autoRefreshTimerInventory = new Timer(500, e -> refreshInventory());
        autoRefreshTimerLines = new Timer(500, e -> refreshLines(linesArea));
        autoRefreshTimerMissions = new Timer(500, e -> refreshMissions());
        autoRefreshTimerInventory.start();
        autoRefreshTimerProgress.start();
        autoRefreshTimerLines.start();
        autoRefreshTimerMissions.start();


        panel.add(scroll, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        refreshProgress();
        return panel;
    }

    // --- Reports Methods ---
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 31, 46));
        panel.setOpaque(true);
        JPanel controlsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        controlsPanel.setBackground(new Color(31, 31, 46));
        panel.setOpaque(true);

        JRadioButton rdoPerformance = new JRadioButton("Line Performance (Accomplishment Levels)");
        rdoPerformance.setForeground(Color.WHITE);
        rdoPerformance.setBackground(new Color(31, 31, 46));
        rdoPerformance.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rdoPerformance.setFocusPainted(false);
        JRadioButton report = new JRadioButton("Write A Report Or A Rate");
        report.setForeground(Color.WHITE);
        report.setBackground(new Color(31, 31, 46));
        report.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        report.setFocusPainted(false);

        ButtonGroup group = new ButtonGroup();
        group.add(rdoPerformance);
        group.add(report);
        rdoPerformance.setSelected(true);

        controlsPanel.add(rdoPerformance);
        controlsPanel.add(report);

        JButton btnGenerate = new JButton("SHOW");
        styleButton(btnGenerate, new Color(116, 9, 227));
        controlsPanel.add(btnGenerate);

        panel.add(controlsPanel, BorderLayout.NORTH);

        JTextArea txtOutput = new JTextArea();
        txtOutput.setBackground(new Color(38, 38, 58));
        txtOutput.setForeground(new Color(229, 231, 235));
        txtOutput.setCaretColor(Color.WHITE);
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtOutput.setBackground(new Color(33, 33, 50));
        panel.add(new JScrollPane(txtOutput), BorderLayout.CENTER);

        btnGenerate.addActionListener(e -> {
            txtOutput.setText("");
            StringBuilder sb = new StringBuilder();
            FactoryController controller = FactoryController.getInstance();

            try {
                if (rdoPerformance.isSelected()) {
                    sb.append("=== LINES PERFORMANCE REPORT ===\n\n");
                    Map<ProductLine, Double> levels = controller.showAllAccomplishLevels();

                    if (levels.isEmpty()) sb.append("No active product lines found.\n");

                    for (Map.Entry<ProductLine, Double> entry : levels.entrySet()) {
                        sb.append(String.format("Line ID: %d | Name: %s\n", entry.getKey().getId(), "Line-" + entry.getKey().getLineName()));
                        sb.append(String.format("Status: %s\n", entry.getKey().getLineState()));
                        sb.append(String.format("Accomplishment Level: %.2f %%\n", entry.getValue()));
                        sb.append("------------------------------------------------\n");
                    }
                } else if (report.isSelected()) {
                    JDialog dialog = new JDialog(
                            (Frame) SwingUtilities.getWindowAncestor(this),
                            "Write Report",
                            true
                    );

                    dialog.setSize(400, 300);
                    dialog.setLayout(new BorderLayout());

                    JTextArea textArea = new JTextArea();
                    textArea.setBackground(new Color(25, 25, 97));
                    textArea.setForeground(Color.WHITE);
                    textArea.setCaretColor(Color.WHITE);

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    dialog.add(scrollPane, BorderLayout.CENTER);

                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);

                    String reportText = textArea.getText();
                    sb.append("the report: ");
                    sb.append(reportText);
                }

                txtOutput.setText(sb.toString());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }


    private JPanel createMissionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 31, 46));
        missionsArea = new JTextArea();
        missionsArea.setBackground(new Color(38, 38, 58));
        missionsArea.setForeground(new Color(229, 231, 235));
        missionsArea.setCaretColor(Color.WHITE);
        missionsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn, new Color(56, 189, 248));
        refreshBtn.addActionListener(e -> refreshMissions());
        JScrollPane scrollPane = new JScrollPane(missionsArea);

        scrollPane.getViewport().setBackground(new Color(31, 31, 46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        refreshMissions();
        return panel;
    }


    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 31, 46));
        inventoryArea = new JTextArea();
        inventoryArea.setBackground(new Color(38, 38, 58));
        inventoryArea.setForeground(new Color(229, 231, 235));
        inventoryArea.setCaretColor(Color.WHITE);
        inventoryArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn, new Color(56, 189, 248));
        refreshBtn.addActionListener(e -> refreshInventory());
        JScrollPane scrollPane = new JScrollPane(inventoryArea);
        scrollPane.getViewport().setBackground(new Color(31, 31, 46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        refreshInventory();
        return panel;
    }

    private JPanel createErrorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 31, 46));
        errorsArea = new JTextArea();
        errorsArea.setBackground(new Color(38, 38, 58));
        errorsArea.setForeground(new Color(229, 231, 235));
        errorsArea.setCaretColor(Color.WHITE);
        errorsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        styleButton(refreshBtn, new Color(56, 189, 248));
        refreshBtn.addActionListener(e -> refreshErrors());
        JScrollPane scrollPane = new JScrollPane(errorsArea);
        scrollPane.getViewport().setBackground(new Color(31, 31, 46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        refreshErrors();
        return panel;
    }


    private JPanel createLinesPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(31, 31, 46));


        JTextField nameField = new JTextField(15);
        nameField.setBackground(new Color(38, 38, 58));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);

        JButton addBtn = new JButton("Add Line");
        styleButton(addBtn, new Color(130, 90, 240));

        JPanel addPanel = new JPanel();
        addPanel.setBackground(new Color(31, 31, 46));
        JLabel l1=new JLabel("Line Name: ");
        l1.setForeground(Color.WHITE);
        addPanel.add(l1);
        addPanel.add(nameField);
        addPanel.add(addBtn);

        JTextField lineIdField = new JTextField(5);
        lineIdField.setBackground(new Color(38, 38, 58));
        lineIdField.setForeground(Color.WHITE);
        lineIdField.setCaretColor(Color.WHITE);

        JButton startBtn = new JButton("START");
        styleButton(startBtn, new Color(34, 197, 94));

        JButton stopBtn = new JButton("STOP");
        styleButton(stopBtn, new Color(239, 68, 68));

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(31, 31, 46));
        JLabel l2=new JLabel("Line Id: ");
        l2.setForeground(Color.WHITE);
        controlPanel.add(l2);
        controlPanel.add(lineIdField);
        controlPanel.add(startBtn);
        controlPanel.add(stopBtn);

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(new Color(31, 31, 46));

        topContainer.add(addPanel);
        topContainer.add(controlPanel);

        panel.add(topContainer, BorderLayout.NORTH);

        linesArea = new JTextArea();
        linesArea.setEditable(false);
        linesArea.setBackground(new Color(38, 38, 58));
        linesArea.setForeground(Color.WHITE);
        linesArea.setCaretColor(Color.WHITE);
        linesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(linesArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scrollPane.getViewport().setBackground(new Color(31, 31, 46));

        panel.add(scrollPane, BorderLayout.CENTER);

        JTextField missionIdField = new JTextField(5);
        JTextField lineIdField2 = new JTextField(5);

        missionIdField.setBackground(new Color(38, 38, 58));
        missionIdField.setForeground(Color.WHITE);
        missionIdField.setCaretColor(Color.WHITE);

        lineIdField2.setBackground(new Color(38, 38, 58));
        lineIdField2.setForeground(Color.WHITE);
        lineIdField2.setCaretColor(Color.WHITE);

        JButton assignBtn = new JButton("Assign Mission");
        styleButton(assignBtn, new Color(34, 197, 94));

        JPanel assignPanel = new JPanel();
        assignPanel.setBackground(new Color(31, 31, 46));
        JLabel l3=new JLabel("Mission Id");
        l3.setForeground(Color.WHITE);
        assignPanel.add(l3);
        assignPanel.add(missionIdField);
        JLabel l4=new JLabel("Line Id: ");
        l4.setForeground(Color.WHITE);
        assignPanel.add(l4);
        assignPanel.add(lineIdField2);
        assignPanel.add(assignBtn);

        panel.add(assignPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            if (!nameField.getText().trim().isEmpty()) {
                controller.addProductLine(nameField.getText().trim());
                refreshLines(linesArea);
                nameField.setText("");
            }
        });

        startBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(lineIdField.getText());
                controller.changeLineState(id, LineState.ACTIVE);
                controller.startProductLine(id);
                refreshLines(linesArea);
            } catch (Exception ex) {
                DialogUtils.showError(this, "Invalid Line ID");
                ErrorLogger.logError(ex);
            }
        });

        stopBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(lineIdField.getText());
                controller.changeLineState(id, LineState.STOP);
                refreshLines(linesArea);
            } catch (Exception ex) {
                DialogUtils.showError(this, "Invalid Line ID");
                ErrorLogger.logError(ex);
            }
        });

        assignBtn.addActionListener(e -> {
            try {
                int missionId = Integer.parseInt(missionIdField.getText());
                int lineId = Integer.parseInt(lineIdField2.getText());

                controller.assignMissionToLine(missionId, lineId);
                JOptionPane.showMessageDialog(this,
                        "Mission " + missionId + " assigned to Line " + lineId);

            } catch (Exception ex) {
                DialogUtils.showError(this, "Invalid input");
                ErrorLogger.logError(ex);
            }
        });

        refreshLines(linesArea);
        return panel;
    }

    private void refreshProgress() {
        progressContainer.removeAll();

        List<ProductLine> lines = controller.getProductLineList();

        if (lines.isEmpty()) {
            JLabel label = new JLabel("No production lines available");
            label.setForeground(Color.WHITE);
            progressContainer.add(label);
        }

        for (ProductLine line : lines) {
            int value = (int) controller.showSpecificAccomplishLevel(line);

            JLabel title = new JLabel(
                    "Line " + line.getId() + " - " + line.getLineName()
            );
            title.setForeground(Color.WHITE);

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(value);
            bar.setStringPainted(true);
            bar.setString(value + "%");
            bar.setForeground(new Color(130, 58, 240));
            bar.setBackground(new Color(38, 38, 58));
            bar.setBorderPainted(false);

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(new Color(31, 31, 46));
            wrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
            wrapper.add(title, BorderLayout.NORTH);
            wrapper.add(bar, BorderLayout.CENTER);

            progressContainer.add(wrapper);
        }

        progressContainer.revalidate();
        progressContainer.repaint();
    }

    private void refreshErrors() {
        errorsArea.setText(ErrorsReader.errorReader());


    }

    private void refreshMissions() {
        missionsArea.setText("");
        List<Mission> missions = controller.getAllMissions();
        for (Mission m : missions) {
            missionsArea.append(m.toString());
        }
    }

    private void refreshLines(JTextArea area) {
        area.setText("");
        List<ProductLine> productLineList = controller.getProductLineList();
        for (ProductLine line : productLineList) {
            area.append(line.toString());
            area.append(" | Progress: " + String.format("%.2f", controller.showSpecificAccomplishLevel(line)) + "%" + "\n");
        }
    }

    private void refreshInventory() {
        inventoryArea.setText("");
        List<Item> items = controller.showItems();
        for (Item i : items) {
            inventoryArea.append(i.toString());
        }
    }


}