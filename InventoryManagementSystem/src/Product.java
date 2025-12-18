import java.util.Map;

public class Product {
    private int productId;
    private String productName;
    private Map<Item, Integer> items;

    //CONSTRUCTOR
    public Product(int productId, String productName, Map<Item, Integer> items) {
        this.productName = productName;
        this.productId = productId;
        this.items = items;
    }

    //GETTERS
    public int getProductId() {
        return productId;
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


