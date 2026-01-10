package ui;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
public class SupervisorPanel extends JPanel {

    //styling the buttons
    private void styleButton(JButton btn,Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    }


    private JTabbedPane tabbedPane;
    // (Inventory Tab Components)
    private JTable inventoryTable;
    private DefaultTableModel tableModel;//تنسيقات جاهزة
    private JButton btnRefresh;
    private JButton btnDelete;
    private JButton btnAdd;

    // (Missions Tab Components)
    private JTable missionsTable;
    private DefaultTableModel missionsModel;

    // Constructor
    public SupervisorPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(31,31,46));
        this.setOpaque(true);
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(31,31,46));
        tabbedPane.setForeground(new Color(180,140,255));
        tabbedPane.setFont(new Font("Segoe UI",Font.BOLD,13));
        tabbedPane.setOpaque(true);
        JPanel inventoryPanel = createInventoryPanel();
        inventoryPanel.setBackground(new Color(31,31,46));
        JPanel missionPanel = createMissionsPanel();
        missionPanel.setBackground(new Color(31,31,46));
        JPanel reportsPanel = createReportsPanel();
        reportsPanel.setBackground(new Color(31,31,46));
        tabbedPane.addTab("Inventory Management", inventoryPanel);
        tabbedPane.addTab("Missions & Lines", missionPanel);
        tabbedPane.addTab("Reports & Statistics", reportsPanel);

        this.add(tabbedPane, BorderLayout.CENTER);
    }

    // --- Inventory Methods ---

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        panel.setOpaque(true);
        String[] columns = {"ID", "Name", "Category", "Price", "Quantity", "Min Limit"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
                //to prevent editing the table(read only)
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.getTableHeader().setBackground(new Color(116, 9, 227)); // تغيير لون الخلفية
        inventoryTable.getTableHeader().setForeground(Color.WHITE); // تغيير لون النص
        inventoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // تغيير الخط
        inventoryTable.setBackground(new Color(38,38,58));
        inventoryTable.setForeground(new Color(229,231,235));
        inventoryTable.setGridColor(new Color(60,60,80));
        inventoryTable.setSelectionBackground(new Color(130,90,240));
        inventoryTable.setSelectionForeground(Color.WHITE);
        inventoryTable.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.getViewport().setBackground(new Color(31,31,46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE,0));
        scrollPane.setOpaque(true);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(31,31,46));
        btnRefresh = new JButton("Refresh Data");
        styleButton(btnRefresh,new Color(58, 164, 204));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete = new JButton("Delete Selected");
        styleButton(btnDelete,new Color(239,68,68));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd = new JButton("Add New Item");
        styleButton(btnAdd,new Color(69, 1, 255));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnDelete);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadInventoryData());
        btnDelete.addActionListener(e -> deleteSelectedItem());
        btnAdd.addActionListener(e -> showAddDialog());

        loadInventoryData();

        return panel;
    }

    private void loadInventoryData() {
        tableModel.setRowCount(0);
        List<Item> items = FactoryController.getInstance().showItems();

        for (Item item : items) {
            Object[] row = {
                    item.getId(),
                    item.getItemName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getMinQuantity()
            };
            tableModel.addRow(row);
        }
    }

    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow == -1) {
            DialogUtils.showError(this, "Please select an item to delete");
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            FactoryController.getInstance().removeItems(itemId);
            loadInventoryData();
            DialogUtils.showMessage(this, "Item Deleted Successfully!");
        } catch (AllException.ItemNotFoundException ex) {
            DialogUtils.showError(this,"Item not found!");
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Item", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.getContentPane().setBackground(new Color(16, 16, 64));
        JTextField txtName = new JTextField();
        txtName.setBackground(new Color(25, 25, 97));
        txtName.setForeground(new Color(229,231,235));
        txtName.setCaretColor(Color.WHITE);
        String[] categories = {"Raw Material", "Electronics", "Wood", "Metal", "Plastic"};
        JComboBox<String> cmbCategory = new JComboBox<>(categories);
        cmbCategory.setBackground(new Color(25, 25, 97));
        cmbCategory.setForeground(Color.WHITE);
        JTextField txtPrice = new JTextField();
        txtPrice.setBackground(new Color(25, 25, 97));
        txtPrice.setForeground(new Color(229,231,235));
        txtPrice.setCaretColor(Color.WHITE);
        JTextField txtQuantity = new JTextField();
        txtQuantity.setBackground(new Color(25, 25, 97));
        txtQuantity.setForeground(new Color(229,231,235));
        txtQuantity.setCaretColor(Color.WHITE);
        JTextField txtMinLimit = new JTextField();
        txtMinLimit.setBackground(new Color(25, 25, 97));
        txtMinLimit.setForeground(new Color(229,231,235));
        txtMinLimit.setCaretColor(Color.WHITE);
        JLabel label1=new JLabel(" Item Name:");
        label1.setForeground(Color.WHITE);
        dialog.add(label1);
        dialog.add(txtName);
        JLabel label2=new JLabel(" Category:");
        label2.setForeground(Color.WHITE);
        dialog.add(label2);
        dialog.add(cmbCategory);
        JLabel label3=new JLabel(" Price:");
        label3.setForeground(Color.WHITE);
        dialog.add(label3);
        dialog.add(txtPrice);
        JLabel label4=new JLabel(" Quantity:");
        label4.setForeground(Color.WHITE);
        dialog.add(label4);
        dialog.add(txtQuantity);
        JLabel label5=new JLabel(" Minimum Limit:");
        label5.setForeground(Color.WHITE);
        dialog.add(label5);
        dialog.add(txtMinLimit);

        JButton btnSave = new JButton("Save Item");
        styleButton(btnSave,new Color(118, 39, 200));
        dialog.add(new JLabel(""));
        dialog.add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                String name = txtName.getText().trim();
                String category = (String) cmbCategory.getSelectedItem();

                if (name.isEmpty()) {
                    throw new Exception("Name cannot be empty!");
                }

                double price = Double.parseDouble(txtPrice.getText().trim());
                int quantity = Integer.parseInt(txtQuantity.getText().trim());
                int minLimit = Integer.parseInt(txtMinLimit.getText().trim());

                FactoryController.getInstance().addItem(name, category, price, quantity, minLimit);

                JOptionPane.showMessageDialog(dialog, "Item Added Successfully!");
                dialog.dispose();
                loadInventoryData();
            } catch (NumberFormatException ex) {
                DialogUtils.showError(dialog, "Please enter valid numbers for Price/Quantity!");
            } catch (Exception ex) {
                DialogUtils.showError(dialog, ex.getMessage());
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // --- Missions Methods ---

    private JPanel createMissionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        String[] columns = {"Mission ID", "Product", "Line", "Client", "Quantity", "Status", "Progress %"};

        missionsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        missionsTable = new JTable(missionsModel);
        missionsTable.getTableHeader().setBackground(new Color(116, 9, 227)); // تغيير لون الخلفية
        missionsTable.getTableHeader().setForeground(Color.WHITE); // تغيير لون النص
        missionsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // تغيير الخط
        missionsTable.setBackground(new Color(38,38,58));
        missionsTable.setForeground(new Color(229,231,235));
        missionsTable.setGridColor(new Color(60,60,80));
        missionsTable.setSelectionBackground(new Color(130,90,240));
        missionsTable.setSelectionForeground(Color.WHITE);
        missionsTable.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(missionsTable);
        scrollPane.getViewport().setBackground(new Color(31,31,46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE,0));
        scrollPane.setOpaque(true);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(38,38,58));
        JButton btnNewMission = new JButton("New Mission");
        styleButton(btnNewMission,new Color(69, 1, 255));
        JButton btnCancelMission = new JButton("Cancel Selected");
        styleButton(btnCancelMission,new Color(239,68,68));
        JButton btnRefreshMissions = new JButton("Refresh Status");
        styleButton(btnRefreshMissions,new Color(58, 164, 204));
        JButton btnCreateProduct=new JButton("Create Product");
        styleButton(btnCreateProduct,new Color(195, 14, 152));
        buttonPanel.add(btnCreateProduct);
        buttonPanel.add(btnNewMission);
        buttonPanel.add(btnCancelMission);
        buttonPanel.add(btnRefreshMissions);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        btnRefreshMissions.addActionListener(e -> loadMissionsData());
        btnNewMission.addActionListener(e -> showAddMissionDialog());
        btnCancelMission.addActionListener(e -> cancelSelectedMission());
        btnCreateProduct.addActionListener(e -> showCreateProductDialog());
        loadMissionsData();

        return panel;
    }
    private void showCreateProductDialog(){
        JDialog dialog=new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),"Add Product",true
        );
        dialog.setSize(400,300);
        dialog.setLayout(new GridLayout(0,2,10,10));
        dialog.getContentPane().setBackground(new Color(16,16,64));
        JTextField txtName = new JTextField();
        txtName.setBackground(new Color(25, 25, 97));
        txtName.setForeground(new Color(229,231,235));
        txtName.setCaretColor(Color.WHITE);
        JComboBox<Item> cmbItems = new JComboBox<>();
        cmbItems.setBackground(new Color(25, 25, 97));
        cmbItems.setForeground(Color.WHITE);
        for (Item i : FactoryController.getInstance().showItems()) {
            cmbItems.addItem(i);
        }

        JTextField txtQty = new JTextField();
        txtQty.setBackground(new Color(25, 25, 97));
        txtQty.setForeground(new Color(229,231,235));
        txtQty.setCaretColor(Color.WHITE);
        JButton btnAddRecipe = new JButton("Add To Recipe");
        styleButton(btnAddRecipe,new Color(25, 114, 145));
        JTextArea recipeArea = new JTextArea();
        recipeArea.setBackground(new Color(25, 25, 97));
        recipeArea.setForeground(new Color(229,231,235));
        recipeArea.setCaretColor(Color.WHITE);
        recipeArea.setEditable(false);

        Map<Item, Integer> recipe = new java.util.HashMap<>();

        btnAddRecipe.addActionListener(e -> {
            Item item = (Item) cmbItems.getSelectedItem();
            int q = Integer.parseInt(txtQty.getText().trim());
            recipe.put(item, q);
            recipeArea.append(item.getItemName() + " : " + q + "\n");
        });

        JButton btnSave = new JButton("Save Product");
        styleButton(btnSave,new Color(118, 39, 200));
        btnSave.addActionListener(e -> {
            try {
                FactoryController.getInstance().addProduct(
                        txtName.getText().trim(),
                        recipe
                );
                DialogUtils.showMessage(dialog, "Product Created!");
                dialog.dispose();
            } catch (Exception ex) {
                DialogUtils.showError(dialog, ex.getMessage());
            }
        });
        JLabel label1=new JLabel(" Product Name:");
        label1.setForeground(Color.WHITE);
        dialog.add(label1);
        dialog.add(txtName);
        JLabel label2=new JLabel(" Item:");
        label2.setForeground(Color.WHITE);
        dialog.add(label2);
        dialog.add(cmbItems);
        JLabel label3= new JLabel(" Quantity:");
        label3.setForeground(Color.WHITE);
        dialog.add(label3);
        dialog.add(txtQty);
        dialog.add(btnAddRecipe);
        dialog.add(new JScrollPane(recipeArea));
        dialog.add(new JLabel(""));
        dialog.add(btnSave);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void loadMissionsData() {
        missionsModel.setRowCount(0);
        List<model.Mission> missions = FactoryController.getInstance().getAllMissions();

        for (model.Mission m : missions) {
            Object[] row = {
                    m.getMissionId(),
                    m.getAquiredProduct().getProductName(),
                    m.getProductLine().getId(),
                    m.getClient(),
                    m.getOrderedQuantity(),
                    m.getState(),
                    String.format("%.1f %%", m.getAccomplishLevel())
            };
            missionsModel.addRow(row);
        }
    }

    private void showAddMissionDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Assign New Mission", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.getContentPane().setBackground(new Color(16,16,64));
        // Fields
        JComboBox<Product> cmbProducts = new JComboBox<>();
        cmbProducts.setBackground(new Color(25, 25, 97));
        cmbProducts.setForeground(Color.WHITE);
        List<Product>products=  FactoryController.getInstance().getAllProducts();
        for(Product p:products)
            cmbProducts.addItem(p);
        JComboBox<String> cmbLines = new JComboBox<>();
        cmbLines.setBackground(new Color(25, 25, 97));
        cmbLines.setForeground(Color.WHITE);
        List<ProductLine> lines = FactoryController.getInstance().getProductLineList();
        for (ProductLine line : lines) {
            cmbLines.addItem("Line ID: " + line.getId());
        }

        JTextField txtQuantity = new JTextField();
        txtQuantity.setBackground(new Color(25, 25, 97));
        txtQuantity.setForeground(new Color(229,231,235));
        txtQuantity.setCaretColor(Color.WHITE);
        JTextField txtClient = new JTextField();
        txtClient.setBackground(new Color(25, 25, 97));
        txtClient.setForeground(new Color(229,231,235));
        txtClient.setCaretColor(Color.WHITE);
        JTextField txtStartDate = new JTextField(LocalDate.now().toString());
        txtStartDate.setBackground(new Color(25, 25, 97));
        txtStartDate.setForeground(new Color(229,231,235));
        txtStartDate.setCaretColor(Color.WHITE);
        JTextField txtEndDate = new JTextField();
        txtEndDate.setBackground(new Color(25, 25, 97));
        txtEndDate.setForeground(new Color(229,231,235));
        txtEndDate.setCaretColor(Color.WHITE);

        // Adding components
        JLabel label1=new JLabel(" Select Product");
        label1.setForeground(Color.WHITE);
        dialog.add(label1);
        dialog.add(cmbProducts);
        JLabel label2=new JLabel(" Select Product Line:");
        label2.setForeground(Color.WHITE);
        dialog.add(label2);
        dialog.add(cmbLines);
        JLabel label3=new JLabel(" Quantity");
        label3.setForeground(Color.WHITE);
        dialog.add(label3);
        dialog.add(txtQuantity);
        JLabel label4=new JLabel(" Client Name:");
        label4.setForeground(Color.WHITE);
        dialog.add(label4);
        dialog.add(txtClient);
        JLabel label5=new JLabel(" Start Date (YYYY-MM-DD):");
        label5.setForeground(Color.WHITE);
        dialog.add(label5);
        dialog.add(txtStartDate);
        JLabel label6=new JLabel(" End Date (YYYY-MM-DD):");
        label6.setForeground(Color.WHITE);
        dialog.add(label6);
        dialog.add(txtEndDate);

        JButton btnStart = new JButton("Start Mission");
        styleButton(btnStart,new Color(53, 163, 26));
        dialog.add(new JLabel(""));
        dialog.add(btnStart);

        // Action Listener
        btnStart.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(txtQuantity.getText().trim());
                String client = txtClient.getText().trim();
                LocalDate start = LocalDate.parse(txtStartDate.getText().trim());
                LocalDate end = LocalDate.parse(txtEndDate.getText().trim());

                int selectedLineIndex = cmbLines.getSelectedIndex();
                if (selectedLineIndex == -1) throw new Exception("Please select a product line!");
                ProductLine selectedLine = lines.get(selectedLineIndex);

                Product product1 = (Product) cmbProducts.getSelectedItem();

                FactoryController.getInstance().addMission(product1, quantity, client, start, end, selectedLine);

                // Start the thread if not running
                if (selectedLine instanceof ProductLine) {
                    FactoryController.getInstance().changeLineState(selectedLine.getId(), LineState.ACTIVE);
                    Thread t = new Thread(selectedLine);
                    t.start();
                }

                DialogUtils.showMessage(dialog, "Mission Started Successfully!");
                dialog.dispose();
                loadMissionsData();

            } catch (Exception ex) {
                DialogUtils.showError(dialog, ex.getMessage());
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // private Product createDemoProduct(String selection) throws AllException.ItemNotFoundException {
    // Map<Item, Integer> recipe = new HashMap<>();
    //     List<Item> items = FactoryController.getInstance().showItems();

    //     if (items.isEmpty()) {
    //         throw new RuntimeException("Inventory is empty! Add items first.");
    //     }

    //     if (selection.equals("Car")) {
    //         recipe.put(items.get(0), 5);
    //     } else {
    //         recipe.put(items.get(1), 2);
    //     }
    //     return new Product(selection, recipe);
    // }

    private void cancelSelectedMission() {
        int row = missionsTable.getSelectedRow();
        if (row == -1) {
            DialogUtils.showMessage(this, "Select a mission to cancel.");
            return;
        }

        int missionId = (int) missionsModel.getValueAt(row, 0);
        int lineId = (int) missionsModel.getValueAt(row, 2);

        try {
            FactoryController.getInstance().removeMission(lineId, missionId);
            DialogUtils.showMessage(this, "Mission Cancelled.");
            loadMissionsData();
        } catch (HeadlessException | AllException.MissionNotFoundException | AllException.ProductLineNotFoundException ex) {
            DialogUtils.showError(this, ex.getMessage());
        }
    }
    // --- Reports Methods ---
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31,31,46));
        panel.setOpaque(true);
        JPanel controlsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        controlsPanel.setBackground(new Color(31,31,46));
        panel.setOpaque(true);

        JRadioButton rdoPerformance = new JRadioButton("Line Performance (Accomplishment Levels)");
        rdoPerformance.setForeground(Color.WHITE);
        rdoPerformance.setBackground(new Color(31,31,46));
        rdoPerformance.setFont(new Font("Segoe UI",Font.PLAIN,16));
        rdoPerformance.setFocusPainted(false);
        JRadioButton rdoProductionHistory = new JRadioButton("Production History (All Lines)");
        rdoProductionHistory.setForeground(Color.WHITE);
        rdoProductionHistory.setBackground(new Color(31,31,46));
        rdoProductionHistory.setFont(new Font("Segoe UI",Font.PLAIN,16));
        rdoProductionHistory.setFocusPainted(false);
        JRadioButton rdoMostOrdered = new JRadioButton("Most Ordered Product (By Date)");
        rdoMostOrdered.setForeground(Color.WHITE);
        rdoMostOrdered.setBackground(new Color(31,31,46));
        rdoMostOrdered.setFont(new Font("Segoe UI",Font.PLAIN,16));
        rdoMostOrdered.setFocusPainted(false);

        ButtonGroup group = new ButtonGroup();
        group.add(rdoPerformance);
        group.add(rdoProductionHistory);
        group.add(rdoMostOrdered);
        rdoPerformance.setSelected(true);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(new Color(31,31,46));
        JLabel label1=new JLabel(" From (YYYY-MM-DD):");
        label1.setForeground(Color.WHITE);
        datePanel.add(label1);
        JTextField txtStart = new JTextField(LocalDate.now().minusDays(30).toString(), 10);
        txtStart.setBackground(Color.LIGHT_GRAY);
        txtStart.setForeground(Color.DARK_GRAY);
        txtStart.setCaretColor(Color.WHITE);
        datePanel.add(txtStart);
        JLabel label2=new JLabel(" To:");
        label2.setForeground(Color.WHITE);
        datePanel.add(label2);
        JTextField txtEnd = new JTextField(LocalDate.now().toString(), 10);
        txtEnd.setBackground(Color.lightGray);
        txtEnd.setForeground(Color.DARK_GRAY);
        txtEnd.setCaretColor(Color.WHITE);
        datePanel.add(txtEnd);

        controlsPanel.add(rdoPerformance);
        controlsPanel.add(rdoProductionHistory);
        controlsPanel.add(rdoMostOrdered);
        controlsPanel.add(datePanel);

        JButton btnGenerate = new JButton("Generate Report");
        styleButton(btnGenerate,new Color(116, 9, 227));
        controlsPanel.add(btnGenerate);

        panel.add(controlsPanel, BorderLayout.NORTH);

        JTextArea txtOutput = new JTextArea();
        txtOutput.setBackground(new Color(38,38,58));
        txtOutput.setForeground(new Color(229,231,235));
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
                        sb.append(String.format("Line ID: %d | Name: %s\n", entry.getKey().getId(), "Line-"+entry.getKey().getLineName()));
                        sb.append(String.format("Status: %s\n", entry.getKey().getLineState()));
                        sb.append(String.format("Accomplishment Level: %.2f %%\n", entry.getValue()));
                        sb.append("------------------------------------------------\n");
                    }
                }
                else if (rdoProductionHistory.isSelected()) {
                    sb.append("=== PRODUCTION HISTORY (Done Products) ===\n\n");
                    List<List<Product>> allLists = controller.allProductLinesDoneProducts();

                    int lineIndex = 1;
                    boolean foundAny = false;
                    for (List<Product> prodList : allLists) {
                        if (!prodList.isEmpty()) {
                            foundAny = true;
                            sb.append(String.format(">> Product Line #%d produced:\n", lineIndex));
                            for (Product p : prodList) {
                                sb.append(String.format("   - [ID: %d] %s\n", p.getProductId(), p.getProductName()));
                            }
                            sb.append("\n");
                        }
                        lineIndex++;
                    }
                    if (!foundAny) sb.append("No products have been finished yet.\n");
                }
                else if (rdoMostOrdered.isSelected()) {
                    LocalDate start = LocalDate.parse(txtStart.getText().trim());
                    LocalDate end = LocalDate.parse(txtEnd.getText().trim());

                    sb.append(String.format("=== MOST ORDERED PRODUCT (%s to %s) ===\n\n", start, end));

                    List<Product> mostOrdered = controller.mostOrderedProduct(start, end);

                    if (mostOrdered.isEmpty()) {
                        sb.append("No data found for this period.\n");
                    } else {
                        sb.append("Top Product(s):\n");
                        for (Product p : mostOrdered) {
                            sb.append(String.format(" * %s (ID: %d)\n", p.getProductName(), p.getProductId()));
                        }
                    }
                }

                txtOutput.setText(sb.toString());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}
