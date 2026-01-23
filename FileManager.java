import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import ArfanPart.Item;
import ArfanPart.ComputerPart;
import ArfanPart.CarPart;

public class FileManager {

    public void saveInventory(ArrayList<Item> inventoryList) {
        try {
            FileWriter fw = new FileWriter("inventory.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (Item item : inventoryList) {
                pw.println(item.toFileFormat());
            }

            pw.close();
            fw.close();

            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public ArrayList<Item> loadInventory() {
        ArrayList<Item> inventoryList = new ArrayList<Item>();

        try {
            FileReader fr = new FileReader("inventory.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 6) {
                    if (data[data.length-1].trim().equals("COMPUTER_PART")) {
                        inventoryList.add(
                            new ComputerPart(
                                data[0].trim(),
                                data[1].trim(),
                                Integer.parseInt(data[2].trim()),
                                Double.parseDouble(data[3].trim()),
                                data[4].trim(),
                                data[5].trim()
                            )
                        );
                    } else if (data[data.length-1].trim().equals("CAR_PART") && data.length >= 5) {
                        inventoryList.add(
                            new CarPart(
                                data[0].trim(),
                                data[1].trim(),
                                Integer.parseInt(data[2].trim()),
                                Double.parseDouble(data[3].trim()),
                                data[4].trim()
                            )
                        );
                    }
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("inventory.txt not found or error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric values from inventory file: " + e.getMessage());
        }

        return inventoryList;
    }
}

