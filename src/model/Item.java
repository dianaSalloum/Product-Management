
package model;

public class Item {
    private int id ;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private int minQuantity;

    private static int itemId=1;
    private static synchronized int itemId(){
        return itemId++;
    }

    public Item (String name , String category , double price , int quantity , int minQuantity){
        this.id = itemId() ;
        this.name = name;
        this.category= category;
        this.price = price;
        this.quantity= quantity;
        this.minQuantity=minQuantity;
    }


    public int getId(){
        return id;
    }

    public String getItemName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getMinQuantity(){
        return minQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public static void setItemId(int itemId) {
        Item.itemId = itemId;
    }

    public void setQuantity(int quantity) throws AllException.InvalidQuantityException {
        if(quantity < 0){
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
        return quantity <= minQuantity;
    }

    public void setItemMinLimit(int itemMinLimit) throws AllException.InvalidNumberException {
        if(itemMinLimit>=0)
            this.minQuantity = itemMinLimit;
        else{
            this.minQuantity = 0;
            throw new AllException.InvalidNumberException("MINIMUM LIMIT CANNOT BE NEGATIVE!");
        }
    }


}

