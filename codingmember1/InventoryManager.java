package codingmember1;

import java.util.ArrayList;
import ArfanPart.Item;

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

}

