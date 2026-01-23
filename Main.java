import javax.swing.JOptionPane;
import java.util.ArrayList;
import codingmember1.InventoryManager;
import qawi.UserManager;
import qawi.User;
import ArfanPart.Item;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();

        ArrayList<Item> inventoryList = fileManager.loadInventory();

        InventoryManager inventoryManager = new InventoryManager(inventoryList);

        UserManager userManager = new UserManager();

        Menu menu = new Menu(inventoryManager, userManager, fileManager);

        menu.start();

        fileManager.saveInventory(inventoryManager.getInventoryList());

        JOptionPane.showMessageDialog(null, "Data saved successfully. Goodbye!");
    }
}