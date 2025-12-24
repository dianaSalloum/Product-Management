import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
//this class will work as a linker with the swing
public class FactoryController {
    private List<Item> itemList;
    private List<Product> productList;
    private List<ProductLine> productLineList;
    private Inventory inventory;
//constructor
    public FactoryController() {
        this.itemList = new ArrayList<>();
        this.productLineList = new ArrayList<>();
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
    public void changeLineState(ProductLine productLine, LineState lineState) {
        productLine.setLineState(lineState);
    }

    //show the accomplish level for specific product line
    public double showSpecificAccomplishLevel(ProductLine productLine) {
        Queue<Mission> missions = productLine.getMissions();
        if (missions.isEmpty())
            return 0;
        else {
            double productLineAccomplishLevel = 0;
            for (Mission mission : productLine.getCompletedMissions())
                productLineAccomplishLevel += mission.getAccomplishLevel();
            for (Mission mission:productLine.getInProgressMissions())
                productLineAccomplishLevel += mission.getAccomplishLevel();
            int numberOfMissions =productLine.getCompletedMissions().size()+productLine.getInProgressMissions().size();
            return (productLineAccomplishLevel / numberOfMissions);
        }
    }

    //show the accomplish level for all product lines
    public Map<ProductLine, Double> showAllAccomplishLevels() {
        Map<ProductLine, Double> map = new HashMap<>();
        for (ProductLine productLine : productLineList) {
            map.put(productLine, showSpecificAccomplishLevel(productLine));
        }
        return map;
    }

    //add an item to the inventory
    public void addItem(String itemName, String type, double price, int quantity, int minLimit) {
        Item item = new Item(itemName, type, price, quantity, minLimit);
        itemList.add(item);
    }

    //show all the items
    public List<Item> showItems() {
        return itemList;
    }

    //finding an item by id
    public Item findItem(int id) throws AllException.ItemNotFoundException {
        for (Item item : itemList)
            if (item.getItemId() == id)
                return item;
        throw new AllException.ItemNotFoundException("CANNOT FIND THE ITEM!");
    }

    //delete items
    public void removeItems(int id) throws AllException.ItemNotFoundException {
        Item item = findItem(id);
        itemList.remove(item);
    }

    //editing in items
    public void editItem(int theEditedItemId, String newItemName) throws AllException.ItemNotFoundException {
        Item item = findItem(theEditedItemId);
        item.setItemName(newItemName);
    }

    public void editItem(int theEditedItemId, double price) throws AllException.ItemNotFoundException, AllException.InvalidNumberException {
        Item item = findItem(theEditedItemId);
        item.setPrice(price);
    }

    public void editItemQuantity(int theEditedItemId, int quantity) throws AllException.ItemNotFoundException, AllException.InvalidQuantityException {
        Item item = findItem(theEditedItemId);
        item.setQuantity(quantity);
    }

    public void editItemMinLimit(int theEditedItemId, int minLimit) throws AllException.ItemNotFoundException, AllException.InvalidNumberException {
        Item item = findItem(theEditedItemId);
        item.setMinLimit(minLimit);
    }

    public void editItemType(int theEditedItemId, String type) throws AllException.ItemNotFoundException {
        Item item = findItem(theEditedItemId);
        item.setType(type);
    }

    //searching for an item by name it will return a list because it is okay if there is two or more items with the same name
    public List<Item> findItemByName(String itemName) {
        List<Item> items = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getItemName().equals(itemName))
                items.add(item);
        }
        return items;
    }

    //searching for an item by type
    public List<Item> findItemByType(String itemType) {
        List<Item> items = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getType().equals(itemType))
                items.add(item);
        }
        return items;
    }

    //find available items
    public List<Item> availableItems() {
        List<Item> availableItems = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getQuantity() > 0)
                availableItems.add(item);
        }
        return availableItems;
    }

    //find items that have minimum limit quantity
    public List<Item> lessOrEqualMinLimitItems() {
        List<Item> lessOrEqualMinLimitItems = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getQuantity() <= item.getMinLimit())
                lessOrEqualMinLimitItems.add(item);
        }
        return lessOrEqualMinLimitItems;
    }

    //find out of stock items
    public List<Item> outOfStockItems() {
        List<Item> outOfStockItems = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getQuantity() == 0)
                outOfStockItems.add(item);
        }
        return outOfStockItems;
    }

    //writing inventory state to a txt file
    public void inventoryToTxtFile(String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write("///INVENTORY STATE///\n");
        writer.write("**INVENTORY ITEMS**\n");
        for (Item item : itemList)
            writer.write(item + "\n");
        writer.write("**INVENTORY PRODUCTS**\n");
        for (Product product : productList)
            writer.write(product + "\n");
        writer.close();
    }

    //adding a mission
    public void addMission(Product product, int orderedQuantity, String client, LocalDate start, LocalDate delivery, ProductLine productLine) {
        Mission mission = new Mission(product, orderedQuantity, client, start, delivery, productLine);
        mission.setState(State.IN_PROGRESS);
        productLine.addMission(mission);
    }

    //find product line
    public ProductLine findProductLine(int id) throws AllException.ProductLineNotFoundException {
        for (ProductLine productLine : productLineList)
            if (productLine.getId() == id)
                return productLine;
        throw new AllException.ProductLineNotFoundException("CANNOT FIND PRODUCT LINE!");
    }

    //removing a mission
    public void removeMission(int lineId, int missionId) throws AllException.MissionNotFoundException, AllException.ProductLineNotFoundException {
        ProductLine productLine = findProductLine(lineId);
        productLine.removeMission(missionId);

    }

    //show all missions for a specific product line
    public Queue<Mission> showProductLineMissions(ProductLine productLine) {
        return productLine.getMissions();
    }

    //show all missions for a product
    public List<List<Mission>> showProductMissions(int productId) {
        List<List<Mission>> lists = new ArrayList<>();
        for (ProductLine productLine : productLineList)
            lists.add(productLine.missionsForProduct(productId));
        return lists;
    }

    //find mission by state
    //completed
    public List<List<Mission>> completedMissions() {
        List<List<Mission>> completedMissions = new ArrayList<>();
        for (ProductLine productLine : productLineList)
            completedMissions.add(productLine.getCompletedMissions());
        return completedMissions;
    }

    //in progress
    public List<List<Mission>> inProgressMissions() {
        List<List<Mission>> inProgressMissions = new ArrayList<>();
        for (ProductLine productLine : productLineList)
            inProgressMissions.add(productLine.getInProgressMissions());
        return inProgressMissions;
    }

    //show productLines that did a missions for a specific product
    public List<ProductLine> productLineSpecificProduct(int productId) {
        List<ProductLine> productLines = new ArrayList<>();
        for (ProductLine productLine : productLineList)
            if (productLine.missionsForProduct(productId).size() > 0)
                productLines.add(productLine);
        return productLines;
    }

    //show done products from a specific productLine
    public List<Product> specificProductLineDoneProducts(ProductLine productLine) {
        return productLine.getDoneProductsList();
    }

    //show done products from all productLines
    public List<List<Product>> allProductLinesDoneProducts() {
        List<List<Product>> lists = new ArrayList<>();
        for (ProductLine productLine : productLineList)
            lists.add(productLine.getDoneProductsList());
        return lists;
    }
    //show the most ordered product in specific duration in all product lines
    public List<Product> mostOrderedProduct(LocalDate begin, LocalDate end) throws AllException.InvalidDateException {
        if (begin.isAfter(end))
            throw new AllException.InvalidDateException("WRONG DURATION!...DEFAULT MOOD ACTIVATED");
        int max = 0;
        List<Product> maximumProduct = new ArrayList<>();
        for (ProductLine productLine : productLineList) {
            for (Mission mission : productLine.getCompletedMissions()) {
                LocalDate starting = mission.getStartingDate();
                LocalDate ending = mission.getDeliveryDate();
                if ((starting.isEqual(begin) || starting.isAfter(begin)) && (ending.isEqual(end) || ending.isBefore(end))) {
                    int q = mission.getOrderedQuantity();
                    if (q > max) {
                        max = q;
                        maximumProduct.clear();
                        maximumProduct.add(mission.getAquiredProduct());
                    } else if (max == q)
                        maximumProduct.add(mission.getAquiredProduct());
                }
            }
        }
        return maximumProduct;
    }
    //kill the thread
    public void killThread(ProductLine productLine){
        productLine.setRunning(false);
    }
}
//finished!!


