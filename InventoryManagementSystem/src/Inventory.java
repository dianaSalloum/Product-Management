import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//this class is responsible for managing the amounts of items and products
//it is like a real life inventory just used for controlling the amounts
public class Inventory {
    List<Item> allItems = new ArrayList<>();
    List<Product> allProducts = new ArrayList<>();
    //constructor
    public Inventory(List<Item> allItems, List<Product> allProducts) {
        this.allItems = allItems;
        this.allProducts = allProducts;
    }

    //these two methods are private because of encapsulation ,no one should use them but the inventory itself.

       /*checks if the quantity we need from an item is available or not ,if we have
       the required amount then it will return true else it is false
        */
    private boolean checkItemQuantity(Item item, int quantity) {
        if (item.getQuantity() >= quantity)
            return true;
        else
            return false;
    }

    /*check if all items are enough or not,
    if it is true then we can produce the product,
    if not then we do not have enough quantity so we cannot make the product*/
    private boolean checkMap(Product p) {
        Map<Item, Integer> itemIntegerMap = p.getItems();
        //entry set for the looping in the map
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
    public void itemConsumption(Product product, int taskQuantity) throws AllException.LowStockException, AllException.InvalidQuantityException {
        Map<Item, Integer> itemIntegerMap = product.getItems();
        synchronized (this) {//avoiding two or more threads to check at the same time
            //if we can produce one product let us see if we can for all the required amount
            if (checkMap(product)) {
                for (Map.Entry<Item, Integer> entry1 : itemIntegerMap.entrySet()) {
                    Item key1 = entry1.getKey();
                    Integer value1 = entry1.getValue();
                    //if it is enough for one product,we multiply it by the quantity we want
                    int newQuantity = key1.getQuantity() - (value1 * taskQuantity);
                    key1.setQuantity(newQuantity);
                    //Alert when reaching the minimum
                    if (key1.getQuantity() <= key1.getMinLimit())
                        System.out.println("WARNING: " + key1.getItemName() + " is below minimum level!");
                }
            }
            //else: send notification to cancel or wait till there are enough
            else {
                throw new AllException.LowStockException("LOW IN STOCK,ITEMS ARE NOT ENOUGH FOR ONE PRODUCT!");
            }
        }
    }

    //adding a product to the inventory
    public synchronized void addProduct(Product product, int quantityAdded) {
        for (int i = 0; i < quantityAdded; i++)
            allProducts.add(product);
    }
    //toString
    @Override
    public String toString() {
        return "Inventory{" +
                "allItems=" + allItems +
                ", allProducts=" + allProducts +
                '}';
    }
}