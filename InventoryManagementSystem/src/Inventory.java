//this class has one job which is managing the amount of products and items ,
//we either produce a product so we consume the items
//or we add a product which will also consume the items and add it to the products
//in simple words the inventory just manages the amount of the things inside it which are items and products
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventory {
    List<Item> allItems = new ArrayList<>();
    List<Product> allProducts = new ArrayList<>();

    public Inventory(List<Item> allItems, List<Product> allProducts) {
        this.allItems = allItems;
        this.allProducts = allProducts;
    }
//these two methods are private because of encapsulation ,no one should use them but the inventory itself
    //check if the quantity we need from an item is available or not
    private boolean checkItemQuantity(Item item, int quantity) {
        if(item.getItemQuantity() >= quantity)
            return true;
        else
            return false;
    }

    /*check if all items are enough or not,
    if it is true then we can produce the product,
    if not then we do not have enough quantity so we cannot make the product*/
    private boolean checkMap(Product p) {
        Map<Item, Integer> itemIntegerMap = p.getItems();
        for (Map.Entry<Item, Integer> entry : itemIntegerMap.entrySet()) {
            Item key = entry.getKey();
            Integer value = entry.getValue();
            if (!checkItemQuantity(key, value))
                return false;
        }
        return true;
    }

    //this will change the amount of item when we can produce the product
    //consuming the Items
     public void itemConsumption(Product product) throws AllExceptions.LowStockException, AllExceptions.InvalidNumberException {
        Map<Item, Integer> itemIntegerMap = product.getItems();
        synchronized (this) {
            if (checkMap(product)) {
                for (Map.Entry<Item, Integer> entry1 : itemIntegerMap.entrySet()) {
                    Item key1 = entry1.getKey();
                    Integer value1 = entry1.getValue();
                    int newQuantity = key1.getItemQuantity() - value1;
                    key1.setItemQuantity(newQuantity);
                }
            }
            //else: send notification to cancel or wait till there are enough
            else {
                throw new AllExceptions.LowStockException("LOW IN STOCK,ITEMS ARE NOT ENOUGH!");
            }
        }
    }
    //adding a product to the inventory
    public synchronized void addProduct(Product product){
        allProducts.add(product);
    }
    //adding an item to the inventory
    public synchronized void addItem(Item item){
        allItems.add(item);
    }

}
