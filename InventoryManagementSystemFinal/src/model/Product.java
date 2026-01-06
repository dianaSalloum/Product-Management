
package model;
import java.util.Map;

public class Product {


    private String productName;
    private Map<Item,Integer> items;

    private static int productId=1;
    private static synchronized int productId(){
        return productId++;
    }

    public Product(String productName, Map<Item,Integer> items){
        this.productName = productName;
        this.productId = productId();
        this.items = items;
    }

    public Product(int productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public int getAmountOfItemForProduct(Item item){
        return items.getOrDefault(item, 0);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", items=" + items +
                '}';
    }
}
