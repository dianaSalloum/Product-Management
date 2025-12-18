public class Item {
    private int itemId;
    private String itemName;
    private String itemType;
    private double itemPrice;
    private int itemQuantity;
    private int itemMinLimit;
//CONSTRUCTOR
    public Item(int itemId, String itemName, String itemType,double itemPrice,int itemQuantity,int itemMinLimit) {
        this.itemId=itemId;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.itemQuantity=itemQuantity;
        this.itemType=itemType;
        this.itemMinLimit=itemMinLimit;
    }
//GETTERS
    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getItemMinLimit() {
        return itemMinLimit;
    }
//SETTERS

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemPrice(double itemPrice) throws AllExceptions.InvalidNumberException {
        if(itemPrice>=0)
        this.itemPrice = itemPrice;
        else{
            this.itemPrice=0;
            throw new AllExceptions.InvalidNumberException("PRICE CANNOT BE NEGATIVE!");
        }
    }

    public void setItemQuantity(int itemQuantity) throws AllExceptions.InvalidNumberException {
        if(itemQuantity>=0)
        this.itemQuantity = itemQuantity;
        else {
            this.itemQuantity=0;
            throw new AllExceptions.InvalidNumberException("QUANTITY CANNOT BE NEGATIVE!");
        }
    }


    public void setItemMinLimit(int itemMinLimit) throws AllExceptions.InvalidNumberException {
        if(itemMinLimit>=0)
        this.itemMinLimit = itemMinLimit;
        else{
            this.itemMinLimit=0;
            throw new AllExceptions.InvalidNumberException("MINIMUM LIMIT CANNOT BE NEGATIVE!");
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                ", itemMinLimit=" + itemMinLimit +
                '}';
    }
}

















