package codingmember1;

import java.util.ArrayList;
import ArfanPart.Item;
import ArfanPart.ComputerPart;
import ArfanPart.CarPart;

public class InventoryManager {
    private ArrayList<Item> inventoryList;

    public InventoryManager(ArrayList<Item> startingInventory) {
        this.inventoryList = startingInventory;
    }

    public void addItem(Item item) {
        inventoryList.add(item);
        System.out.println("Item added successfully.");
    }

    public void removeItem(String id) {
        boolean removed = inventoryList.removeIf(item -> item.getId().equals(id));
        if(removed) {
            System.out.println("Item removed.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void updateQuantity(String id, int newQuantity) {
        for (Item item : inventoryList) {
            if (item.getId().equals(id)) {
                item.setQuantity(newQuantity);
                return;
            }
        }
        System.out.println("Item ID not found.");
    }

    public ArrayList<Item> searchItem(String keyword) {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : inventoryList) {
            if (item.getName().contains(keyword) || item.getId().contains(keyword)) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }

    public String validateAndAddItem(String name, String priceText, String quantityText, String selectedType,
                                   String brand, String specs, String vehicle) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return "Please enter an item name.";
            }

            if (priceText == null || priceText.trim().isEmpty()) {
                return "Please enter a price.";
            }

            if (quantityText == null || quantityText.trim().isEmpty()) {
                return "Please enter a quantity.";
            }

            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                return "Price must be greater than zero.";
            }

            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                return "Quantity must be greater than zero.";
            }

            String id = generateItemId();

            if ("Computer Part".equals(selectedType)) {
                if (brand == null || brand.trim().isEmpty()) {
                    return "Please enter a brand for the computer part.";
                }

                if (specs == null || specs.trim().isEmpty()) {
                    return "Please enter specifications for the computer part.";
                }

                addItem(new ComputerPart(id, name, quantity, price, brand, specs));
            } else {
                if (vehicle == null || vehicle.trim().isEmpty()) {
                    return "Please enter vehicle compatibility for the car part.";
                }

                addItem(new CarPart(id, name, quantity, price, vehicle));
            }

            return "SUCCESS";
        } catch (NumberFormatException ex) {
            return "Please enter valid numbers for price and quantity.";
        } catch (Exception ex) {
            return "Error adding item: " + ex.getMessage();
        }
    }

    public String generateItemId() {
        int count = inventoryList.size() + 1;
        return "ITEM-" + String.format("%04d", count);
    }

    public Item getItemById(String id) {
        for (Item item : inventoryList) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public String getItemType(Item item) {
        if (item instanceof ComputerPart) {
            return "Computer Part";
        } else if (item instanceof CarPart) {
            return "Car Part";
        } else {
            return "General Item";
        }
    }

    public String getItemDetails(Item item) {
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

    public String itemToString(Item item) {
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
}

