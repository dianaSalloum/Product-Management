import java.util.Map;

public class Product {
    private int productId;
    private String productName;
    private Map<Item,Integer> items;
//CONSTRUCTOR
    public Product(int productId,String productName,Map<Item,Integer>items){
        this.productName=productName;
        this.productId=productId;
        this.items=items;
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
    //changed the name ><
    public int getAmountOfItemForProduct(Item item){
        return items.getOrDefault(item,0);
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
