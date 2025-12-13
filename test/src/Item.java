import java.util.StringTokenizer;

public class Item {
    private int itemId;
    private String itemName;
    private String type;
    private double price;
    private int quantity;
    private int minLimit;
//CONSTRUCTOR
    public Item(int itemId, String itemName, String type,double price,int quantity,int minLimit) {
    this.itemId=itemId;
    this.itemName=itemName;
    this.minLimit=minLimit;
    this.price=price;
    this.type=type;
    this.quantity=quantity;
    }
//GETTERS
    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinLimit() {
        return minLimit;
    }
//SETTERS
    public void setQuantity(int quantity) throws AllException.InvalidQuantityException {
        if(quantity<0){
            throw new AllException.InvalidQuantityException("QUANTITY SHOULD BE GREATER THAN ZERO!");
        }
            this.quantity = quantity;
    }
//METHODS
    public void addQuantity(int amount) throws AllException.InvalidQuantityException {
        if(amount>=0)
            this.quantity+=amount;
        else
            throw new AllException.InvalidQuantityException("CANNOT ADD NEGATIVE AMOUNT!");
    }
    public boolean QuantityConsumption(int amount) throws AllException.InvalidQuantityException,AllException.LowStockException{
        if (amount <= 0){
            throw new AllException.InvalidQuantityException("AMOUNT SHOULD BE GREATER THAN ZERO!");
        }
        if (quantity < amount){
            throw new AllException.LowStockException("THERE IS NOT ENOUGH QUANTITY FOR CONSUMPTION!");
        }
        this.quantity -= amount;
        return true;
    }

    public boolean CheckLowStock(){
        return quantity <= minLimit;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", minLimit=" + minLimit +
                '}';
    }
}
