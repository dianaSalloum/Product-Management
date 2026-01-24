
package model;

import java.util.Map;

public class Product {

    private int id;
    private String productName;
    private Map<Item, Integer> items;

    private static int productId = 1;

    private static synchronized int productId() {
        return productId++;
    }

    public Product(String productName, Map<Item, Integer> items) {
        this.productName = productName;
        this.id = productId();
        this.items = items;
    }

    public int getProductId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + "}" + "\n";
    }
}
