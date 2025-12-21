import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String []args){
        Item i=new Item("dddd","ff",2,1,4);
        Item i2=new Item("dddd","ff",2,1,4);
        Map<Item,Integer> map=new HashMap<>();
        map.put(i,1);
        Product p1=new Product("ddd",map);
        Inventory inventory=new Inventory(List.of(i),List.of(p1));
ProductLine p2=new ProductLine("dd",inventory);
        System.out.println(p2.getId());
        ProductLine p3=new ProductLine("dd",inventory);
        ProductLine p4=new ProductLine("dd",inventory);
        ProductLine p5=new ProductLine("dd",inventory);
        System.out.println(p3.getId());
        System.out.println(p4.getId());
        System.out.println(p5.getId());
        System.out.println(p1.getProductId());
        Product p8=new Product("ddd",map);
        System.out.println(p8.getProductId());
        System.out.println(i.getItemId());
        System.out.println(i2.getItemId());

    }
}