
package io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.Item;

public class InventoryCSVReader {


    public static List<Item> readItems() {

        List<Item> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        InventoryCSVReader.class
                                .getClassLoader()
                                .getResourceAsStream("Inventory.csv")
                )
        )) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                if (line.isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");

                try {
                    String name = data[0].trim();
                    String category = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    int quantity = Integer.parseInt(data[3].trim());
                    int minQuantity = Integer.parseInt(data[4].trim());

                    Item item = new Item(name, category, price, quantity, minQuantity);
                    items.add(item);
                } catch (NumberFormatException e) {
                    ErrorLogger.logError(e);
                }
            }

        } catch (Exception e) {
            ErrorLogger.logError(e);
        }

        return items;
    }
}