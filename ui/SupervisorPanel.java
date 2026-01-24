package ui;

import java.awt.*;
import java.time.LocalDate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import io.ErrorLogger;
import model.*;
import model.AllException.ProductNotFoundException;

public class SupervisorPanel extends JPanel {

    //styling the buttons
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    }

    private void styleEditField(JTextField field) {
        field.setBackground(new Color(25, 25, 97));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
    }


    private JTabbedPane tabbedPane;
    // (Inventory Tab Components)
    private JTable inventoryTable;
    private DefaultTableModel tableModel;//تنسيقات جاهزة
    private JButton btnRefresh;
    private JButton btnDelete;
    private JButton btnAdd;
    JTextArea area;

    JComboBox<String> cmbSearch = new JComboBox<>();


    FactoryController instance = FactoryController.getInstance();


    // (Missions Tab Components)
    private JTable missionsTable;
    private DefaultTableModel missionsModel;

    // Constructor
    public SupervisorPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(31, 31, 46));
        this.setOpaque(true);
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(31, 31, 46));
        tabbedPane.setForeground(new Color(180, 140, 255));
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setOpaque(true);
        JPanel inventoryPanel = createInventoryPanel();
        inventoryPanel.setBackground(new Color(31, 31, 46));
        JPanel missionPanel = createMissionsPanel();
        missionPanel.setBackground(new Color(31, 31, 46));
        JPanel search = createSearchPanel();
        search.setBackground(new Color(31, 31, 46));
        tabbedPane.addTab("Inventory Management", inventoryPanel);
        tabbedPane.addTab("Missions & Lines", missionPanel);
        tabbedPane.add("Search For", search);

        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(31, 31, 46));
        panel.setOpaque(true);

        // ===== Result Area =====
        area = new JTextArea();
        area.setBackground(new Color(38, 38, 58));
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scrollPane.getViewport().setBackground(new Color(31, 31, 46));


        // ===== Controls =====
        cmbSearch.setBackground(new Color(25, 25, 97));
        cmbSearch.setForeground(Color.WHITE);


        cmbSearch.addItem("Show ProductLine's Missions");//done
        cmbSearch.addItem("Show Product's Missions");//done
        cmbSearch.addItem("Show Completed Missions");//done
        cmbSearch.addItem("Show In-Progress Missions");//done
        cmbSearch.addItem("ProductLines That Did Missions For A Specific Product");//done
        cmbSearch.addItem("Show A Specific ProductLine's Done Products");
        cmbSearch.addItem("Show All ProductLines's Done Products");//done
        cmbSearch.addItem("Filter Items By Name");//done
        cmbSearch.addItem("Filter Items By Category");//done
        cmbSearch.addItem("Find Available Items");//done
        cmbSearch.addItem("Find Items With Minimum Limit Quantity");//done
        cmbSearch.addItem("Find Out Of Stock Items");//done
        cmbSearch.addItem("Show Most Ordered Products");//done

        JTextField txtId = new JTextField(8);

        JLabel lblId = new JLabel("ID \\ name ");
        lblId.setForeground(Color.WHITE);

        JButton btnSearch = new JButton("Search");
        styleButton(btnSearch, new Color(58, 164, 204));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(new Color(31, 31, 46));

        JLabel label1 = new JLabel(" From (YYYY-MM-DD):");
        label1.setForeground(Color.WHITE);
        JTextField txtStart = new JTextField(LocalDate.now().minusDays(30).toString(), 5);
        txtStart.setBackground(Color.LIGHT_GRAY);
        txtStart.setForeground(Color.DARK_GRAY);
        txtStart.setCaretColor(Color.WHITE);
        JLabel label2 = new JLabel(" To:");
        label2.setForeground(Color.WHITE);
        JTextField txtEnd = new JTextField(LocalDate.now().toString(), 5);
        txtEnd.setBackground(Color.lightGray);
        txtEnd.setForeground(Color.DARK_GRAY);
        txtEnd.setCaretColor(Color.WHITE);

        topPanel.add(cmbSearch);
        topPanel.add(lblId);
        topPanel.add(txtId);
        topPanel.add(btnSearch);
        topPanel.add(label1);
        topPanel.add(txtStart);
        topPanel.add(label2);
        topPanel.add(txtEnd);

        btnSearch.addActionListener(e -> {

            area.setText("");

            String selected = (String) cmbSearch.getSelectedItem();
            String input = txtId.getText().trim();

            if (selected == null) {
                DialogUtils.showError(this, "Please choose a search type!");
                return;
            }

            try {

                // ===== 1) ProductLine Missions =====
                if (selected.equals("Show ProductLine's Missions")) {

                    if (input.isEmpty()) {
                        DialogUtils.showError(this, "Please enter ProductLine ID!");
                        return;
                    }

                    int id = Integer.parseInt(input);
                    refreshSearch(id);
                }

                // ===== 2) Filter Items by name =====
                else if (selected.equals("Filter Items By Name")) {

                    if (input.isEmpty()) {
                        DialogUtils.showError(this, "Please enter item name!");
                        return;
                    }

                    List<Item> items = instance.findItemByName(input);

                    if (items.isEmpty()) {
                        area.append("No items found.\n");
                    } else {
                        for (Item i : items) {
                            area.append(i.toString() + "\n");
                        }
                    }
                }
                //======find items by category======
                else if (selected.equals("Filter Items By Category")) {

                    if (input.isEmpty()) {
                        DialogUtils.showError(this, "Please enter item category!");
                        return;
                    }

                    List<Item> items = instance.findItemByType(input);

                    if (items.isEmpty()) {
                        area.append("No items found.\n");
                    } else {
                        for (Item i : items) {
                            area.append("hi");
                            area.append(i.toString() + "\n");
                        }
                    }
                }

                //=====Find available Items=====
                else if (selected.equals("Find Available Items")) {
                    List<Item> items = instance.availableItems();

                    if (items.isEmpty()) {
                        area.append("No items found.\n");
                    } else {
                        for (Item i : items) {
                            area.append(i.toString() + "\n");
                        }
                    }
                }

                //=====Find Items with minimum limit quantity=====
                else if (selected.equals("Find Items With Minimum Limit Quantity")) {
                    List<Item> items = instance.lessOrEqualMinLimitItems();

                    if (items.isEmpty()) {
                        area.append("No items found.\n");
                    } else {
                        for (Item i : items) {
                            area.append(i.toString() + "\n");
                        }
                    }
                }

                //=====Find out of stock Items=====
                else if (selected.equals("Find Out Of Stock Items")) {
                    List<Item> items = instance.outOfStockItems();

                    if (items.isEmpty()) {
                        area.append("No items found.\n");
                    } else {
                        for (Item i : items) {
                            area.append(i.toString() + "\n");
                        }
                    }
                }
                //=====most ordered===
                else if (selected.equals("Show Most Ordered Products")) {
                    LocalDate start = LocalDate.parse(txtStart.getText().trim());
                    LocalDate end = LocalDate.parse(txtEnd.getText().trim());

                    area.append(String.format("=== MOST ORDERED PRODUCT (%s to %s) ===\n\n", start, end));

                    List<Product> mostOrdered = instance.mostOrderedProduct(start, end);

                    if (mostOrdered.isEmpty()) {
                        area.append("No data found for this period.\n");
                    } else {
                        area.append("Top Product(s):\n");
                        for (Product p : mostOrdered) {
                            area.append(String.format(" * %s (ID: %d)\n", p.getProductName(), p.getProductId()));
                        }
                    }
                }
                //====all product lines done products
                else if (selected.equals("Show All ProductLines's Done Products")) {
                    List<List<Product>> lists = instance.allProductLinesDoneProducts();
                    if (lists.isEmpty())
                        area.append("there are not any Done Products!");
                    else {
                        area.append(lists.toString());
                    }
                }


                //===== find Product's Missions====

                else if (selected.equals("Show Product's Missions")) {
                    area.setText("");
                    int id = Integer.parseInt(input);
                    List<List<Mission>> missions = instance.showProductMissions(id);
                    if (missions.isEmpty())
                        area.append("no missions for product with id : " + id);
                    else
                        area.append(missions + "\n");
                }

                //====completed missions==
                else if (selected.equals("Show Completed Missions")) {
                    area.setText("");
                    List<List<Mission>> missionat = instance.completedMissions();
                    if (missionat.isEmpty()) {
                        area.append("No Completed Missions Found Yet");
                        return;
                    }
                    area.append(missionat + "\n");
                }


                //====in progress missions
                else if (selected.equals("Show In-Progress Missions")) {
                    area.setText("");
                    List<List<Mission>> missionat = instance.inProgressMissions();
                    if (missionat.isEmpty()) {
                        area.append("No In-Progress Missions Found Yet");
                        return;
                    }
                    area.append(missionat + "\n");
                }

                //====== find product lines by product ID ======

                else if (selected.equals("ProductLines That Did Missions For A Specific Product")) {
                    int productId = Integer.parseInt(input);
                    List<ProductLine> lines = instance.productLineSpecificProduct(productId);
                    area.append("--- Search Results for Product ID: " + productId + " ---\n");
                    if (lines.isEmpty()) {
                        area.append("No product lines have produced this product yet.\n");
                    } else {
                        area.append(lines + "\n");
                    }
                }
                //===="Show A Specific ProductLine's Done Products"
                else if (selected.equals("Show A Specific ProductLine's Done Products")) {
                    int id = Integer.parseInt(input);
                    try {
                        List<Product> p = instance.specificProductLineDoneProducts(id);
                        if (p.isEmpty())
                            area.append("no done products for product line with id : " + id);
                        else
                            area.append(p + "\n");
                    } catch (AllException.ProductLineNotFoundException ex) {
                        DialogUtils.showError(this, "product line not found!");
                    }

                }

            } catch (NumberFormatException ex) {
                DialogUtils.showError(this, "ID must be a number!");
            } catch (AllException.InvalidDateException ex) {
                DialogUtils.showError(this, "invalid date!");
            }
        });


        // ===== Add to panel =====
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void refreshSearch(int id) {
        FactoryController instance = FactoryController.getInstance();



        try {
            List<Mission>pl = instance.showProductLineMissions(instance.findProductLine(id));
            if (pl.isEmpty()) {
                area.append("Missions have not been created !");
                area.append("\n");
            } else {
                for (Mission m : pl) {
                    area.append(m.toString());
                    area.append("\n");

                }
            }
        } catch (AllException.ProductLineNotFoundException rr) {
            DialogUtils.showError(this, "ProductLine Not Found!");
        }


    }


    // --- Inventory Methods ---

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 31, 46));
        panel.setOpaque(true);
        String[] columns = {"ID", "Name", "Category", "Price", "Quantity", "Min Limit"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
                //to prevent editing the table(read only)
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.getTableHeader().setBackground(new Color(116, 9, 227)); // تغيير لون الخلفية
        inventoryTable.getTableHeader().setForeground(Color.WHITE); // تغيير لون النص
        inventoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // تغيير الخط
        inventoryTable.setBackground(new Color(38, 38, 58));
        inventoryTable.setForeground(new Color(229, 231, 235));
        inventoryTable.setGridColor(new Color(60, 60, 80));
        inventoryTable.setSelectionBackground(new Color(130, 90, 240));
        inventoryTable.setSelectionForeground(Color.WHITE);
        inventoryTable.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.getViewport().setBackground(new Color(31, 31, 46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        scrollPane.setOpaque(true);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(31, 31, 46));
        btnRefresh = new JButton("Refresh Data");
        styleButton(btnRefresh, new Color(58, 164, 204));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete = new JButton("Delete Selected");
        styleButton(btnDelete, new Color(239, 68, 68));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd = new JButton("Add New Item");
        styleButton(btnAdd, new Color(69, 1, 255));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton btnEdit = new JButton("Edit Item");
        styleButton(btnEdit, new Color(17, 149, 98));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadInventoryData());
        btnDelete.addActionListener(e -> deleteSelectedItem());
        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> editItems());
        loadInventoryData();

        return panel;
    }

    private void editItems() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow == -1) {
            DialogUtils.showError(this, "Please select an item to edit");
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentName = tableModel.getValueAt(selectedRow, 1).toString();
        double currentPrice = Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString());
        int currentQuantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());

        showEditDialog(itemId, currentName, currentPrice, currentQuantity);
    }

    private void showEditDialog(int itemId, String name, double price, int quantity) {
        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Edit Item",
                true
        );
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 3, 10, 10));
        dialog.getContentPane().setBackground(new Color(16, 16, 64));

        // Radio buttons
        JRadioButton rbName = new JRadioButton("Name");
        JRadioButton rbPrice = new JRadioButton("Price");
        JRadioButton rbQuantity = new JRadioButton("Quantity");

        ButtonGroup group = new ButtonGroup();
        group.add(rbName);
        group.add(rbPrice);
        group.add(rbQuantity);

        rbName.setForeground(Color.WHITE);
        rbPrice.setForeground(Color.WHITE);
        rbQuantity.setForeground(Color.WHITE);

        rbName.setBackground(new Color(16, 16, 64));
        rbPrice.setBackground(new Color(16, 16, 64));
        rbQuantity.setBackground(new Color(16, 16, 64));

        JTextField txtName = new JTextField(name);
        JTextField txtPrice = new JTextField(String.valueOf(price));
        JTextField txtQuantity = new JTextField(String.valueOf(quantity));

        styleEditField(txtName);
        styleEditField(txtPrice);
        styleEditField(txtQuantity);

        dialog.add(rbName);
        dialog.add(new JLabel(""));
        dialog.add(txtName);

        dialog.add(rbPrice);
        dialog.add(new JLabel(""));
        dialog.add(txtPrice);

        dialog.add(rbQuantity);
        dialog.add(new JLabel(""));
        dialog.add(txtQuantity);

        JButton btnSave = new JButton("Save");
        styleButton(btnSave, new Color(17, 149, 98));

        dialog.add(new JLabel(""));
        dialog.add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                if (rbName.isSelected()) {
                    FactoryController.getInstance()
                            .editItem(itemId, txtName.getText().trim());
                } else if (rbPrice.isSelected()) {
                    FactoryController.getInstance()
                            .editItem(itemId, Double.parseDouble(txtPrice.getText()));
                } else if (rbQuantity.isSelected()) {
                    FactoryController.getInstance()
                            .editItemQuantity(itemId, Integer.parseInt(txtQuantity.getText()));
                } else {
                    DialogUtils.showError(dialog, "Please select what you want to edit");
                    return;
                }

                dialog.dispose();
                loadInventoryData();
                DialogUtils.showMessage(this, "Item updated successfully!");

            } catch (Exception ex) {
                DialogUtils.showError(dialog, ex.getMessage());
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
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
            DialogUtils.showError(this, "Item not found!");
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Item", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.getContentPane().setBackground(new Color(16, 16, 64));
        JTextField txtName = new JTextField();
        txtName.setBackground(new Color(25, 25, 97));
        txtName.setForeground(new Color(229, 231, 235));
        txtName.setCaretColor(Color.WHITE);
        String[] categories = {"Raw Material", "Electronics", "Wood", "Metal", "Plastic", "fabric"};
        JComboBox<String> cmbCategory = new JComboBox<>(categories);
        cmbCategory.setBackground(new Color(25, 25, 97));
        cmbCategory.setForeground(Color.WHITE);
        JTextField txtPrice = new JTextField();
        txtPrice.setBackground(new Color(25, 25, 97));
        txtPrice.setForeground(new Color(229, 231, 235));
        txtPrice.setCaretColor(Color.WHITE);
        JTextField txtQuantity = new JTextField();
        txtQuantity.setBackground(new Color(25, 25, 97));
        txtQuantity.setForeground(new Color(229, 231, 235));
        txtQuantity.setCaretColor(Color.WHITE);
        JTextField txtMinLimit = new JTextField();
        txtMinLimit.setBackground(new Color(25, 25, 97));
        txtMinLimit.setForeground(new Color(229, 231, 235));
        txtMinLimit.setCaretColor(Color.WHITE);
        JLabel label1 = new JLabel(" Item Name:");
        label1.setForeground(Color.WHITE);
        dialog.add(label1);
        dialog.add(txtName);
        JLabel label2 = new JLabel(" Category:");
        label2.setForeground(Color.WHITE);
        dialog.add(label2);
        dialog.add(cmbCategory);
        JLabel label3 = new JLabel(" Price:");
        label3.setForeground(Color.WHITE);
        dialog.add(label3);
        dialog.add(txtPrice);
        JLabel label4 = new JLabel(" Quantity:");
        label4.setForeground(Color.WHITE);
        dialog.add(label4);
        dialog.add(txtQuantity);
        JLabel label5 = new JLabel(" Minimum Limit:");
        label5.setForeground(Color.WHITE);
        dialog.add(label5);
        dialog.add(txtMinLimit);

        JButton btnSave = new JButton("Save Item");
        styleButton(btnSave, new Color(118, 39, 200));
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

                DialogUtils.showMessage(this, "Item Added Successfully!");
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
        panel.setBackground(new Color(31, 31, 46));
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
        missionsTable.setBackground(new Color(38, 38, 58));
        missionsTable.setForeground(new Color(229, 231, 235));
        missionsTable.setGridColor(new Color(60, 60, 80));
        missionsTable.setSelectionBackground(new Color(130, 90, 240));
        missionsTable.setSelectionForeground(Color.WHITE);
        missionsTable.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(missionsTable);
        scrollPane.getViewport().setBackground(new Color(31, 31, 46));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        scrollPane.setOpaque(true);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(38, 38, 58));
        JButton btnNewMission = new JButton("New Mission");
        styleButton(btnNewMission, new Color(69, 1, 255));
        JButton btnCancelMission = new JButton("Cancel Selected");
        styleButton(btnCancelMission, new Color(239, 68, 68));
        JButton btnRefreshMissions = new JButton("Refresh Status");
        styleButton(btnRefreshMissions, new Color(58, 164, 204));
        JButton btnCreateProduct = new JButton("Create Product");
        styleButton(btnCreateProduct, new Color(195, 14, 152));
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

    private void showCreateProductDialog() {
        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), "Add Product", true
        );
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(0, 2, 10, 10));
        dialog.getContentPane().setBackground(new Color(16, 16, 64));
        JTextField txtName = new JTextField();
        txtName.setBackground(new Color(25, 25, 97));
        txtName.setForeground(new Color(229, 231, 235));
        txtName.setCaretColor(Color.WHITE);
        JComboBox<String> cmbItems = new JComboBox<>();
        cmbItems.setBackground(new Color(25, 25, 97));
        cmbItems.setForeground(Color.WHITE);
        for (Item i : FactoryController.getInstance().showItems()) {
            cmbItems.addItem(i.getItemName());
        }

        JTextField txtQty = new JTextField();
        txtQty.setBackground(new Color(25, 25, 97));
        txtQty.setForeground(new Color(229, 231, 235));
        txtQty.setCaretColor(Color.WHITE);
        JButton btnAddRecipe = new JButton("Add To Recipe");
        styleButton(btnAddRecipe, new Color(25, 114, 145));
        JTextArea recipeArea = new JTextArea();
        recipeArea.setBackground(new Color(25, 25, 97));
        recipeArea.setForeground(new Color(229, 231, 235));
        recipeArea.setCaretColor(Color.WHITE);
        recipeArea.setEditable(false);

        Map<Item, Integer> recipe = new java.util.HashMap<>();

        btnAddRecipe.addActionListener(e -> {
            Item item = null;
            try {
                String name = (String) cmbItems.getSelectedItem();
                item = FactoryController.getInstance().findItem(name);
            } catch (AllException.ItemNotFoundException ee) {
                DialogUtils.showError(this, "Item not Found");

            }

            int q = Integer.parseInt(txtQty.getText().trim());
            recipe.put(item, q);
            if (item != null) {
                recipeArea.append(item.getItemName() + " : " + q + "\n");
            }
        });

        JButton btnSave = new JButton("Save Product");
        styleButton(btnSave, new Color(118, 39, 200));
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
        JLabel label1 = new JLabel(" Product Name:");
        label1.setForeground(Color.WHITE);
        dialog.add(label1);
        dialog.add(txtName);
        JLabel label2 = new JLabel(" Item:");
        label2.setForeground(Color.WHITE);
        dialog.add(label2);
        dialog.add(cmbItems);
        JLabel label3 = new JLabel(" Quantity:");
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
        dialog.getContentPane().setBackground(new Color(16, 16, 64));
        // Fields
        JComboBox<String> cmbProducts = new JComboBox<>();
        cmbProducts.setBackground(new Color(25, 25, 97));
        cmbProducts.setForeground(Color.WHITE);
        List<Product> products = FactoryController.getInstance().getAllProducts();
        HashSet<String> s = new HashSet<>();
        for (Product pName : products) {
            s.add(pName.getProductName());
        }
        for (String p : s)
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
        txtQuantity.setForeground(new Color(229, 231, 235));
        txtQuantity.setCaretColor(Color.WHITE);
        JTextField txtClient = new JTextField();
        txtClient.setBackground(new Color(25, 25, 97));
        txtClient.setForeground(new Color(229, 231, 235));
        txtClient.setCaretColor(Color.WHITE);
        JTextField txtStartDate = new JTextField(LocalDate.now().toString());
        txtStartDate.setBackground(new Color(25, 25, 97));
        txtStartDate.setForeground(new Color(229, 231, 235));
        txtStartDate.setCaretColor(Color.WHITE);
        JTextField txtEndDate = new JTextField();
        txtEndDate.setBackground(new Color(25, 25, 97));
        txtEndDate.setForeground(new Color(229, 231, 235));
        txtEndDate.setCaretColor(Color.WHITE);

        // Adding components
        JLabel label1 = new JLabel(" Select Product");
        label1.setForeground(Color.WHITE);
        dialog.add(label1);
        dialog.add(cmbProducts);
        JLabel label2 = new JLabel(" Select Product Line:");
        label2.setForeground(Color.WHITE);
        dialog.add(label2);
        dialog.add(cmbLines);
        JLabel label3 = new JLabel(" Quantity");
        label3.setForeground(Color.WHITE);
        dialog.add(label3);
        dialog.add(txtQuantity);
        JLabel label4 = new JLabel(" Client Name:");
        label4.setForeground(Color.WHITE);
        dialog.add(label4);
        dialog.add(txtClient);
        JLabel label5 = new JLabel(" Start Date (YYYY-MM-DD):");
        label5.setForeground(Color.WHITE);
        dialog.add(label5);
        dialog.add(txtStartDate);
        JLabel label6 = new JLabel(" End Date (YYYY-MM-DD):");
        label6.setForeground(Color.WHITE);
        dialog.add(label6);
        dialog.add(txtEndDate);

        JButton btnStart = new JButton("Start Mission");
        styleButton(btnStart, new Color(53, 163, 26));
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

                String pName = (String) cmbProducts.getSelectedItem();
                Product product1 = null;
                try {
                    product1 = FactoryController.getInstance().findProduct(pName);
                } catch (ProductNotFoundException rr) {
                    DialogUtils.showError(this, "Product does not exist!");
                }


                FactoryController.getInstance().addMission(product1, quantity, client, start, end, selectedLine);

                // Start the thread if not running
                if (selectedLine != null) {
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
        } catch (HeadlessException | AllException.MissionNotFoundException |
                 AllException.ProductLineNotFoundException ex) {
            DialogUtils.showError(this, ex.getMessage());
        }
    }
}

