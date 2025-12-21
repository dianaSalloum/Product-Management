import java.util.*;


public class FactoryController {
    private List<Item> itemList;
    private List<Product> productList;
    private List<Mission> missionList;
    private List<ProductLine> productLineList;
    private Inventory inventory;

    public FactoryController() {
        this.productLineList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.missionList = new ArrayList<>();
        this.productList = new ArrayList<>();
        this.inventory = new Inventory(itemList, productList);
    }

    //Adding new productLine
    public void addProductLine(String lineName) {
        ProductLine productLine = new ProductLine(lineName, inventory);
        productLine.setLineState(LineState.STOP);
        productLineList.add(productLine);
    }
    //changing the product line state
public void changeLineState(ProductLine productLine,LineState lineState){
        productLine.setLineState(lineState);
}
    //show the accomplish level for specific product line
    public double showSpecificAccomplishLevel(ProductLine productLine) {
        Queue<Mission> missions = productLine.getMissions();
        if (missions.isEmpty())
            return 0;
        else {
            double productLineAccomplishLevel = 0;
            for (Mission mission : missions)
                productLineAccomplishLevel += mission.getAccomplishLevel();
            int numberOfMissions = missions.size();
            return (productLineAccomplishLevel / numberOfMissions);
        }
    }
    //show the accomplish level for all product lines
    public Map<ProductLine,Double> showAllAccomlishLevels(){
        Map<ProductLine,Double>map=new HashMap<>();
        for(ProductLine productLine:productLineList){
            map.put(productLine,showSpecificAccomplishLevel(productLine));
        }
        return map;
    }


}


