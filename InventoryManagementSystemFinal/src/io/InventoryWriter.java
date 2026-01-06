package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import model.Item;
import model.Product;

public class InventoryWriter {

    private static final String pathName = "inventory_out.txt";
    public static void writeInventory(Collection <Item> items){
        try(FileWriter fr = new FileWriter(pathName , true)){
            for (Item item : items ){
                fr.write(
                        item.getId() + "," +
                                item.getItemName() + "," +
                                item.getCategory() + "," +
                                item.getPrice() + "," +
                                item.getQuantity() + "," +
                                item.getMinQuantity() + "\n"
                );
            }
        }catch(IOException e){
            ErrorLogger.logError(e);
        }







    }

}