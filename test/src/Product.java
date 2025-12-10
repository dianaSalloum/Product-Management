public class Product {
    private int productId;
    private String productName;
    private Item items;
    public Product(int productId,String productName,Item items){
        this.productName=productName;
        this.productId=productId;
        this.items=items;
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
