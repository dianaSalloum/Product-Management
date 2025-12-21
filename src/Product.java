import java.util.Map;

public class Product {
    //automatically change the id
    private static int productId=1;
    private static synchronized int productId(){
        return productId++;
    }
    private int id;
    private String productName;
    private Map<Item, Integer> items;

    //CONSTRUCTOR
    public Product(String productName, Map<Item, Integer> items) {
        this.productName = productName;
        this.id = productId();
        this.items = items;
    }

    //GETTERS
    public int getProductId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public int getAmountOfItemForProduct(Item item) {
        return items.getOrDefault(item, 0);
    }

//SETTERS

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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


