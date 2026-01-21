import java.util.ArrayList;

public class InventoryManager {
    // The main memory storage for the application
    private ArrayList<Item> inventoryList;

    public InventoryManager() {
        this.inventoryList = new ArrayList<>();
    }

    // METHOD 1: Add
    public void addItem(Item item) {
        // You might want to check if ID exists before adding
        inventoryList.add(item);
        System.out.println("Item added successfully.");
    }

    // METHOD 2: Remove
    public void removeItem(String id) {
        // Logic to removeIf or loop and remove
        boolean removed = inventoryList.removeIf(item -> item.getId().equals(id));
        if(removed) {
            System.out.println("Item removed.");
        } else {
            System.out.println("Item not found.");
        }
    }

    // METHOD 3: Update
    public void updateQuantity(String id, int newQuantity) {
        for (Item item : inventoryList) {
            if (item.getId().equals(id)) {
                item.setQuantity(newQuantity);
                return; // Exit once found
            }
        }
        System.out.println("Item ID not found.");
    }

    // METHOD 4: Search
    public ArrayList<Item> searchItem(String keyword) {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : inventoryList) {
            if (item.getName().contains(keyword) || item.getId().contains(keyword)) {
                results.add(item);
            }
        }
        return results;
    }
    
    // Getter for Role 4 (The Archivist) to save data
    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }
}