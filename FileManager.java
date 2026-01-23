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
                pw.println(item.toString());
            }

            pw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public ArrayList<Item> loadInventory() {
        ArrayList<Item> inventoryList = new ArrayList<Item>();

        try {
            FileReader fr = new FileReader("inventory.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 6) {
                    inventoryList.add(
                        new ComputerPart(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2]),
                            Double.parseDouble(data[3]),
                            data[4],
                            data[5]
                        )
                    );
                }
                else if (data.length == 5) {
                    inventoryList.add(
                        new CarPart(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2]),
                            Double.parseDouble(data[3]),
                            data[4]
                        )
                    );
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("inventory.txt not found.");
        }

        return inventoryList;
    }
}

