import java.util.HashMap;
import java.util.Map;

public class Product {
//automatically change the id
/*creates a static variable and make it behave as a counter,
so every time we create an object it will increase using the synchronized method below.
 */
    private static int productId=1;
    private static synchronized int productId(){
        return productId++;
    }
    private int id;
    private String productName;
    private Map<Item, Integer> items;

    //constructor
    public Product(String productName, Map<Item, Integer> items) {
        this.productName = productName;
        this.id = productId();
        this.items = new HashMap<>(items);
    }

    //getters
    public int getProductId() {
        return id;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    //toString
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + id +
                ", productName='" + productName + '\'' +
                ", items=" + items +
                '}';
    }
}


