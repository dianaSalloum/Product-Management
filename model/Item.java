
package model;

public class Item {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private int minQuantity;

    private static int itemId = 1;

    private static synchronized int itemId() {
        return itemId++;
    }

    public Item(String name, String category, double price, int quantity, int minQuantity) {
        this.id = itemId();
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
    }


    public int getId() {
        return id;
    }

    public String getItemName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public void setQuantity(int quantity) throws AllException.InvalidQuantityException {
        if (quantity < 0) {
            throw new AllException.InvalidQuantityException("QUANTITY SHOULD BE GREATER THAN ZERO!");
        }
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", minQuantity=" + minQuantity +
                '}' + "\n";
    }
}

