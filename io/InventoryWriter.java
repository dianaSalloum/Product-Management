package io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

import model.Item;
import model.Product;

public class InventoryWriter {

    private static final String pathName = "inventory_out.txt";

    public static void writeInventory(Collection<Item> items, Collection<Product> products) {
        try (PrintWriter fr = new PrintWriter(new FileWriter(pathName, true))) {
            for (Item item : items) {
                fr.write(
                        item.getId() + "," +
                                item.getItemName() + "," +
                                item.getCategory() + "," +
                                item.getPrice() + "," +
                                item.getQuantity() + "," +
                                item.getMinQuantity() + "\n"
                );
                for (Product product : products) {
                    fr.print(
                            product.getProductName() + "," +
                                    product.getProductId() + ","

                    );
                    Set<Item> set1 = product.getItems().keySet();
                    for (Item tem : set1) {
                        fr.print(tem.getItemName() + ",");
                    }
                    for (Integer value : product.getItems().values()) {
                        fr.println(value);
                    }
                }
            }
        } catch (IOException e) {
            ErrorLogger.logError(e);
        }


    }

}