import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws AllExceptions.InvalidNumberException, AllExceptions.LowStockException {
Item i1=new Item(1,"FE","hadid",20,5,1);
Item i2=new Item(2,"AL","alaminum",10,7,0);
Map<Item,Integer>map=new HashMap<>();
map.put(i1,5);
map.put(i2,4);
List<Item> items=new ArrayList<>();
items.add(i1);
items.add(i2);
        System.out.println(items);
        Product p1=new Product(1,"car",map);
        Inventory inventory=new Inventory(items,List.of(p1));
        inventory.itemConsumption(p1);
        System.out.println(items);

    }
}