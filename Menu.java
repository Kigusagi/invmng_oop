import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import codingmember1.InventoryManager;
import qawi.UserManager;
import ArfanPart.Item;
import ArfanPart.ComputerPart;
import ArfanPart.CarPart;

public class Menu extends JFrame {
    private InventoryManager inventoryManager;
    private UserManager userManager;
    private FileManager fileManager;

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JComboBox<String> itemTypeCombo;
    private JTextField brandField;
    private JTextField specsField;
    private JTextField vehicleField;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JLabel welcomeLabel;
    private JLabel statusLabel;

    public Menu(InventoryManager inventoryManager, UserManager userManager, FileManager fileManager) {
        this.inventoryManager = inventoryManager;
        this.userManager = userManager;
        this.fileManager = fileManager;

        initializeGUI();
        setupEventHandlers();
        refreshTable();
    }

    private void initializeGUI() {
        setTitle("Inventory Management System - Point of Sale Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        userManager.simulateAdminLogin();
        updateUserStatus();
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Item"));
        inputPanel.setPreferredSize(new Dimension(1100, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Item Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setToolTipText("Enter the name of the item");
        inputPanel.add(nameField, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 3;
        priceField = new JTextField(10);
        priceField.setToolTipText("Enter the price of the item");
        inputPanel.add(priceField, gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 5;
        quantityField = new JTextField(10);
        quantityField.setToolTipText("Enter the quantity of the item");
        inputPanel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Item Type:"), gbc);

        gbc.gridx = 1;
        itemTypeCombo = new JComboBox<>(new String[]{"Computer Part", "Car Part"});
        itemTypeCombo.addActionListener(e -> updateAdditionalFields());
        inputPanel.add(itemTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel additionalFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;

        gbc2.gridx = 0; gbc2.gridy = 0;
        additionalFieldsPanel.add(new JLabel("Brand:"), gbc2);

        gbc2.gridx = 1;
        brandField = new JTextField(15);
        brandField.setToolTipText("Enter the brand of the computer part");
        additionalFieldsPanel.add(brandField, gbc2);

        gbc2.gridx = 2;
        additionalFieldsPanel.add(new JLabel("Specs:"), gbc2);

        gbc2.gridx = 3;
        specsField = new JTextField(20);
        specsField.setToolTipText("Enter the specifications of the computer part");
        additionalFieldsPanel.add(specsField, gbc2);

        gbc2.gridx = 4;
        additionalFieldsPanel.add(new JLabel("Vehicle:"), gbc2);

        gbc2.gridx = 5;
        vehicleField = new JTextField(15);
        vehicleField.setToolTipText("Enter the vehicle compatibility for car part");
        vehicleField.setVisible(false);
        additionalFieldsPanel.add(vehicleField, gbc2);

        inputPanel.add(additionalFieldsPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JButton addButton = new JButton("Add to List");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(120, 30));
        inputPanel.add(addButton, gbc);

        addButton.addActionListener(e -> addToInventory());

        return inputPanel;
    }

    private void updateAdditionalFields() {
        String selectedType = (String) itemTypeCombo.getSelectedItem();
        if ("Computer Part".equals(selectedType)) {
            brandField.setVisible(true);
            specsField.setVisible(true);
            vehicleField.setVisible(false);
        } else if ("Car Part".equals(selectedType)) {
            brandField.setVisible(true);
            specsField.setVisible(false);
            vehicleField.setVisible(true);
        }
        validate();
        repaint();
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Inventory Items"));

        String[] columnNames = {"ID", "Name", "Price", "Quantity", "Total", "Type", "Details"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setFillsViewportHeight(true);

        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        inventoryTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        inventoryTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        inventoryTable.getColumnModel().getColumn(6).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setPreferredSize(new Dimension(1050, 350));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteSelectedItem());
        buttonPanel.add(deleteButton);

        JButton updateQtyButton = new JButton("Update Quantity");
        updateQtyButton.setBackground(new Color(255, 165, 0));
        updateQtyButton.setForeground(Color.WHITE);
        updateQtyButton.addActionListener(e -> updateSelectedQuantity());
        buttonPanel.add(updateQtyButton);

        JButton searchButton = new JButton("Search Item");
        searchButton.setBackground(new Color(106, 90, 205));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> searchItem());
        buttonPanel.add(searchButton);

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        return tablePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(statusLabel, BorderLayout.WEST);

        welcomeLabel = new JLabel("Welcome to the Inventory Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        controlPanel.add(refreshButton);

        JButton saveButton = new JButton("Save Data");
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveData());
        controlPanel.add(saveButton);

        bottomPanel.add(controlPanel, BorderLayout.EAST);

        return bottomPanel;
    }

    private void setupEventHandlers() {
        ActionListener addListener = e -> addToInventory();
        nameField.addActionListener(addListener);
        priceField.addActionListener(addListener);
        quantityField.addActionListener(addListener);
    }

    private void updateUserStatus() {
        welcomeLabel.setText(userManager.getUserStatusMessage());
    }

    private void addToInventory() {
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String selectedType = (String) itemTypeCombo.getSelectedItem();
        String brand = brandField.getText().trim();
        String specs = specsField.getText().trim();
        String vehicle = vehicleField.getText().trim();

        String result = inventoryManager.validateAndAddItem(name, priceText, quantityText, selectedType, brand, specs, vehicle);

        if ("SUCCESS".equals(result)) {
            refreshTable();
            clearInputFields();
            statusLabel.setText("Item added successfully: " + name);
            JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, result, "Validation Error", JOptionPane.WARNING_MESSAGE);
            if (result.contains("item name")) {
                nameField.requestFocus();
            } else if (result.contains("price")) {
                priceField.requestFocus();
            } else if (result.contains("quantity")) {
                quantityField.requestFocus();
            } else if (result.contains("brand")) {
                brandField.requestFocus();
            } else if (result.contains("specifications")) {
                specsField.requestFocus();
            } else if (result.contains("vehicle")) {
                vehicleField.requestFocus();
            }
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        brandField.setText("");
        specsField.setText("");
        vehicleField.setText("");
        nameField.requestFocus();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        ArrayList<Item> inventory = inventoryManager.getInventoryList();
        for (Item item : inventory) {
            Object[] rowData = {
                item.getId(),
                item.getName(),
                String.format("$%.2f", item.getPrice()),
                item.getQuantity(),
                String.format("$%.2f", item.getPrice() * item.getQuantity()),
                inventoryManager.getItemType(item),
                inventoryManager.getItemDetails(item)
            };
            tableModel.addRow(rowData);
        }

        statusLabel.setText("Displaying " + inventory.size() + " items in inventory");
    }

    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String itemId = (String) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete item " + itemId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                inventoryManager.removeItem(itemId);
                refreshTable();
                statusLabel.setText("Item " + itemId + " deleted successfully");
                JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateSelectedQuantity() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String itemId = (String) tableModel.getValueAt(selectedRow, 0);
            String itemName = (String) tableModel.getValueAt(selectedRow, 1);

            String newQuantityStr = JOptionPane.showInputDialog(
                this,
                "Enter new quantity for " + itemName + ":",
                String.valueOf(tableModel.getValueAt(selectedRow, 3))
            );

            if (newQuantityStr != null && !newQuantityStr.trim().isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(newQuantityStr);
                    if (newQuantity < 0) {
                        JOptionPane.showMessageDialog(this, "Quantity cannot be negative.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    inventoryManager.updateQuantity(itemId, newQuantity);
                    refreshTable();
                    statusLabel.setText("Quantity updated for item " + itemId);
                    JOptionPane.showMessageDialog(this, "Quantity updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update quantity.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchItem() {
        String keyword = JOptionPane.showInputDialog(this, "Enter search keyword (ID or Name):");
        if (keyword != null && !keyword.trim().isEmpty()) {
            ArrayList<Item> results = inventoryManager.searchItem(keyword);

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items found matching: " + keyword, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Search Results:\n\n");
                for (Item item : results) {
                    sb.append(inventoryManager.itemToString(item)).append("\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void saveData() {
        try {
            fileManager.saveInventory(inventoryManager.getInventoryList());
            statusLabel.setText("Data saved successfully at " + new java.util.Date());
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void start() {
        setVisible(true);
    }
}