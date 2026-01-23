import javax.swing.JOptionPane;
import java.util.ArrayList;
import codingmember1.InventoryManager;
import qawi.UserManager;
import qawi.User;
import qawi.Admin;
import ArfanPart.Item;
import ArfanPart.ComputerPart;
import ArfanPart.CarPart;

public class Menu {
    private InventoryManager inventoryManager;
    private UserManager userManager;
    private FileManager fileManager;

    public Menu(InventoryManager inventoryManager, UserManager userManager, FileManager fileManager) {
        this.inventoryManager = inventoryManager;
        this.userManager = userManager;
        this.fileManager = fileManager;
    }

    public void start() {
        // Simulate admin login to bypass authentication
        simulateAdminLogin();

        boolean running = true;
        while (running) {
            User currentUser = userManager.getLoggedInUser();

            String[] options;
            if (currentUser.getRole().equals("ADMIN")) {
                options = new String[]{"View Inventory", "Add Item", "Remove Item", "Update Quantity", "Search Item", "Logout"};
            } else {
                options = new String[]{"View Inventory", "Update Quantity", "Search Item", "Logout"};
            }

            int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")!\nSelect an option:",
                "Inventory Management System",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (currentUser.getRole().equals("ADMIN")) {
                switch (choice) {
                    case 0:
                        viewInventory();
                        break;
                    case 1:
                        addItem();
                        break;
                    case 2:
                        removeItem();
                        break;
                    case 3:
                        updateQuantity();
                        break;
                    case 4:
                        searchItem();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        if (choice == -1) {
                            running = false;
                        }
                        break;
                }
            } else {
                switch (choice) {
                    case 0:
                        viewInventory();
                        break;
                    case 1:
                        updateQuantity();
                        break;
                    case 2:
                        searchItem();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        if (choice == -1) {
                            running = false;
                        }
                        break;
                }
            }
        }
    }

    private void simulateAdminLogin() {
        // Create and set a default admin user to bypass login
        User adminUser = new Admin("admin", "admin123");
        // Since UserManager doesn't have a method to set logged in user directly,
        // we'll use the authenticate method with default admin credentials
        userManager.authenticate("admin", "admin123");
        JOptionPane.showMessageDialog(null, "Login bypassed. Welcome to the Inventory Management System!");
    }

    private void viewInventory() {
        ArrayList<Item> inventory = inventoryManager.getInventoryList();
        StringBuilder sb = new StringBuilder("Current Inventory:\n\n");

        if (inventory.isEmpty()) {
            sb.append("No items in inventory.");
        } else {
            for (Item item : inventory) {
                sb.append(itemToString(item)).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Inventory", JOptionPane.INFORMATION_MESSAGE);
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

    private void addItem() {
        String id = JOptionPane.showInputDialog(null, "Enter item ID (e.g., CP-001):");
        if (id == null) return;

        String name = JOptionPane.showInputDialog(null, "Enter item name:");
        if (name == null) return;

        String quantityStr = JOptionPane.showInputDialog(null, "Enter quantity:");
        if (quantityStr == null) return;
        int quantity = Integer.parseInt(quantityStr);

        String priceStr = JOptionPane.showInputDialog(null, "Enter price:");
        if (priceStr == null) return;
        double price = Double.parseDouble(priceStr);

        Object[] itemTypes = {"Computer Part", "Car Part"};
        int itemType = JOptionPane.showOptionDialog(
            null,
            "Select item type:",
            "Item Type",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            itemTypes,
            itemTypes[0]
        );

        if (itemType == 0) {
            String brand = JOptionPane.showInputDialog(null, "Enter brand:");
            if (brand == null) return;

            String specs = JOptionPane.showInputDialog(null, "Enter specifications:");
            if (specs == null) return;

            ComputerPart computerPart = new ComputerPart(id, name, quantity, price, brand, specs);
            inventoryManager.addItem(computerPart);
        } else if (itemType == 1) {
            String vehicleType = JOptionPane.showInputDialog(null, "Enter vehicle compatibility:");
            if (vehicleType == null) return;

            CarPart carPart = new CarPart(id, name, quantity, price, vehicleType);
            inventoryManager.addItem(carPart);
        }
    }

    private void removeItem() {
        String id = JOptionPane.showInputDialog(null, "Enter item ID to remove:");
        if (id != null) {
            inventoryManager.removeItem(id);
        }
    }

    private void updateQuantity() {
        String id = JOptionPane.showInputDialog(null, "Enter item ID to update quantity:");
        if (id == null) return;

        String quantityStr = JOptionPane.showInputDialog(null, "Enter new quantity:");
        if (quantityStr != null) {
            int newQuantity = Integer.parseInt(quantityStr);
            inventoryManager.updateQuantity(id, newQuantity);
        }
    }

    private void searchItem() {
        String keyword = JOptionPane.showInputDialog(null, "Enter search keyword (ID or Name):");
        if (keyword != null) {
            ArrayList<Item> results = inventoryManager.searchItem(keyword);

            StringBuilder sb = new StringBuilder("Search Results:\n\n");
            if (results.isEmpty()) {
                sb.append("No items found matching: ").append(keyword);
            } else {
                for (Item item : results) {
                    sb.append(itemToString(item)).append("\n");
                }
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}