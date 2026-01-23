import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import codingmember1.InventoryManager;
import qawi.UserManager;
import qawi.User;
import qawi.Admin;
import ArfanPart.Item;
import ArfanPart.ComputerPart;
import ArfanPart.CarPart;

public class Menu extends JFrame {
    private InventoryManager inventoryManager;
    private UserManager userManager;
    private FileManager fileManager;

    // GUI Components
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

        // Set layout
        setLayout(new BorderLayout());

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top panel for input section
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Center panel for table
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Bottom panel for status and controls
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Simulate admin login to bypass authentication
        simulateAdminLogin();
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Item"));
        inputPanel.setPreferredSize(new Dimension(1100, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Item Name and Price
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

        // Row 1: Item Type
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Item Type:"), gbc);

        gbc.gridx = 1;
        itemTypeCombo = new JComboBox<>(new String[]{"Computer Part", "Car Part"});
        itemTypeCombo.addActionListener(e -> updateAdditionalFields());
        inputPanel.add(itemTypeCombo, gbc);

        // Row 2: Additional Fields (Brand/Specs or Vehicle)
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
        vehicleField.setVisible(false); // Initially hidden
        additionalFieldsPanel.add(vehicleField, gbc2);

        inputPanel.add(additionalFieldsPanel, gbc);

        // Row 3: Add to List button
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JButton addButton = new JButton("Add to List");
        addButton.setBackground(new Color(70, 130, 180)); // Steel blue
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(120, 30));
        inputPanel.add(addButton, gbc);

        // Add action listener to the button
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

        // Define column names
        String[] columnNames = {"ID", "Name", "Price", "Quantity", "Total", "Type", "Details"};

        // Create table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        // Create table
        inventoryTable = new JTable(tableModel);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setFillsViewportHeight(true);

        // Set column widths
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Price
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Quantity
        inventoryTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Total
        inventoryTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Type
        inventoryTable.getColumnModel().getColumn(6).setPreferredWidth(200); // Details

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setPreferredSize(new Dimension(1050, 350));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add button panel below the table
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(220, 20, 60)); // Crimson
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteSelectedItem());
        buttonPanel.add(deleteButton);

        JButton updateQtyButton = new JButton("Update Quantity");
        updateQtyButton.setBackground(new Color(255, 165, 0)); // Orange
        updateQtyButton.setForeground(Color.WHITE);
        updateQtyButton.addActionListener(e -> updateSelectedQuantity());
        buttonPanel.add(updateQtyButton);

        JButton searchButton = new JButton("Search Item");
        searchButton.setBackground(new Color(106, 90, 205)); // Slate Blue
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> searchItem());
        buttonPanel.add(searchButton);

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        return tablePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Status label
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(statusLabel, BorderLayout.WEST);

        // Welcome label
        welcomeLabel = new JLabel("Welcome to the Inventory Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Control buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        controlPanel.add(refreshButton);

        JButton saveButton = new JButton("Save Data");
        saveButton.setBackground(new Color(34, 139, 34)); // Forest green
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveData());
        controlPanel.add(saveButton);

        bottomPanel.add(controlPanel, BorderLayout.EAST);

        return bottomPanel;
    }

    private void setupEventHandlers() {
        // Add Enter key listener to trigger add action
        ActionListener addListener = e -> addToInventory();
        nameField.addActionListener(addListener);
        priceField.addActionListener(addListener);
        quantityField.addActionListener(addListener);
    }

    private void simulateAdminLogin() {
        // Create and set a default admin user to bypass login
        User adminUser = new Admin("admin", "admin123");
        userManager.authenticate("admin", "admin123");
        updateUserStatus();
    }

    private void updateUserStatus() {
        User currentUser = userManager.getLoggedInUser();
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        }
    }

    private void addToInventory() {
        try {
            // Validate input fields
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an item name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                nameField.requestFocus();
                return;
            }

            String priceText = priceField.getText().trim();
            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a price.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                priceField.requestFocus();
                return;
            }

            String quantityText = quantityField.getText().trim();
            if (quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a quantity.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                quantityField.requestFocus();
                return;
            }

            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than zero.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                priceField.requestFocus();
                return;
            }

            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                quantityField.requestFocus();
                return;
            }

            String selectedType = (String) itemTypeCombo.getSelectedItem();
            Item newItem;

            // Generate a unique ID
            String id = generateItemId();

            if ("Computer Part".equals(selectedType)) {
                String brand = brandField.getText().trim();
                String specs = specsField.getText().trim();

                if (brand.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a brand for the computer part.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    brandField.requestFocus();
                    return;
                }

                if (specs.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter specifications for the computer part.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    specsField.requestFocus();
                    return;
                }

                newItem = new ComputerPart(id, name, quantity, price, brand, specs);
            } else { // Car Part
                String vehicle = vehicleField.getText().trim();

                if (vehicle.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter vehicle compatibility for the car part.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    vehicleField.requestFocus();
                    return;
                }

                newItem = new CarPart(id, name, quantity, price, vehicle);
            }

            // Add to inventory
            inventoryManager.addItem(newItem);

            // Refresh table
            refreshTable();

            // Clear input fields
            clearInputFields();

            statusLabel.setText("Item added successfully: " + name);
            JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateItemId() {
        // Generate a unique ID based on current inventory size
        int count = inventoryManager.getInventoryList().size() + 1;
        return "ITEM-" + String.format("%04d", count);
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
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add all items from inventory to table
        ArrayList<Item> inventory = inventoryManager.getInventoryList();
        for (Item item : inventory) {
            Object[] rowData = {
                item.getId(),
                item.getName(),
                String.format("$%.2f", item.getPrice()),
                item.getQuantity(),
                String.format("$%.2f", item.getPrice() * item.getQuantity()),
                getItemType(item),
                getItemDetails(item)
            };
            tableModel.addRow(rowData);
        }

        statusLabel.setText("Displaying " + inventory.size() + " items in inventory");
    }

    private String getItemType(Item item) {
        if (item instanceof ComputerPart) {
            return "Computer Part";
        } else if (item instanceof CarPart) {
            return "Car Part";
        } else {
            return "General Item";
        }
    }

    private String getItemDetails(Item item) {
        if (item instanceof ComputerPart) {
            ComputerPart cp = (ComputerPart) item;
            return "Brand: " + cp.getBrand() + ", Specs: " + cp.getSpecs();
        } else if (item instanceof CarPart) {
            CarPart carPart = (CarPart) item;
            return "Vehicle: " + carPart.getModelCompatibility();
        } else {
            return "N/A";
        }
    }

    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String itemId = (String) tableModel.getValueAt(selectedRow, 0); // Get ID from first column

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
            String itemId = (String) tableModel.getValueAt(selectedRow, 0); // Get ID from first column
            String itemName = (String) tableModel.getValueAt(selectedRow, 1); // Get name from second column

            String newQuantityStr = JOptionPane.showInputDialog(
                this,
                "Enter new quantity for " + itemName + ":",
                String.valueOf(tableModel.getValueAt(selectedRow, 3)) // Current quantity
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
                // Create a dialog to show search results
                StringBuilder sb = new StringBuilder("Search Results:\n\n");
                for (Item item : results) {
                    sb.append(itemToString(item)).append("\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private String itemToString(Item item) {
        if (item instanceof ComputerPart) {
            ComputerPart cp = (ComputerPart) item;
            return String.format("ID: %s | Name: %s | Qty: %d | Price: $%.2f | Brand: %s | Specs: %s",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice(),
                    cp.getBrand(), cp.getSpecs());
        } else if (item instanceof CarPart) {
            CarPart carPart = (CarPart) item;
            return String.format("ID: %s | Name: %s | Qty: %d | Price: $%.2f | Vehicle: %s",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice(),
                    carPart.getModelCompatibility());
        } else {
            return String.format("ID: %s | Name: %s | Qty: %d | Price: $%.2f",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice());
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