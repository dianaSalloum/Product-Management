import java.util.StringTokenizer;

public class Item {
    private int itemId;
    private String itemName;
    private String type;
    private double price;
    private int quantity;
    private int minLimit;

    public Item(int itemId, String itemName, String type,double price,int quantity,int minLimit) {
    this.itemId=itemId;
    this.itemName=itemName;
    this.minLimit=minLimit;
    this.price=price;
    this.type=type;
    this.quantity=quantity;
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
