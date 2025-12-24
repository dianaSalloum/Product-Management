public class Item {
//automatically change the id
/*creates a static variable and make it behave as a counter,
so every time we create an object it will increase using the synchronized method below.
 */
private static int itemId=1;
private static synchronized int itemId(){
        return itemId++;
    }
    private int id;
    private String itemName;
    private String type;
    private double price;
    private int quantity;
    private int minLimit;
//constructor
    /*the id will increase automatically to reduce the logical mistakes.*/
    public Item(String itemName, String type, double price, int quantity, int minLimit) {
        this.id=itemId();
        this.itemName = itemName;
        this.minLimit = minLimit;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }
//getters
    public int getItemId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinLimit() {
        return minLimit;
    }

    //setters
    public void setQuantity(int quantity) throws AllException.InvalidQuantityException {
        if(quantity < 0){
            throw new AllException.InvalidQuantityException("QUANTITY SHOULD BE GREATER THAN ZERO!");
        }
        this.quantity = quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) throws AllException.InvalidNumberException {
        if(price<0){
            this.price=0;
            throw new AllException.InvalidNumberException("PRICE CANNOT BE NEGATIVE!");}
        this.price = price;
    }

    public void setMinLimit(int minLimit) throws AllException.InvalidNumberException {
        if(minLimit<0){
            this.minLimit=0;
            throw new AllException.InvalidNumberException("MINIMUM LIMIT CANNOT BE NEGATIVE!");}
        this.minLimit = minLimit;
    }
    //toString
    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + id +
                ", itemName='" + itemName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", minLimit=" + minLimit +
                '}';
    }

}