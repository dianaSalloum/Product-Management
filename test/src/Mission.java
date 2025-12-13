import java.time.LocalDate;

public class Mission {
    private int taskId;
    private Product aquiredProduct;
    private int orderedQuantity;
    private String client;
    private LocalDate startingDate;
    private LocalDate  deliveryDate;
    private State state;
    private ProductLine productLine;
    private int accomplishLevel;
    private int doneProducts;
//CONSTRUCTOR
    public Mission(int taskId,Product aquiredProduct,int orderedQuantity,String client,LocalDate startingDate,LocalDate deliveryDate,ProductLine productLine){
        this.aquiredProduct=aquiredProduct;
        this.taskId=taskId;
        this.orderedQuantity=orderedQuantity;
        this.client=client;
        this.startingDate=startingDate;
        this.deliveryDate=deliveryDate;
        this.productLine=productLine;
        this.state=State.COMPLETED;
        this.accomplishLevel=0;
        this.doneProducts=0;
    }
//GETTERS
    public int getTaskId() {
        return taskId;
    }

    public Product getAquiredProduct() {
        return aquiredProduct;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public String getClient() {
        return client;
    }

    public State getState() {
        return state;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public int getAccomplishLevel() {
        return accomplishLevel;
    }
//SETTERS

    public void setState(State state) {
        this.state = state;
    }

    //METHODS
    //CHECKING IF THE DATE IS VALID OR NOT
    public Boolean checkDate(LocalDate startingDate,LocalDate deliveryDate) throws AllException.InvalidDate {
        if(startingDate.isAfter(deliveryDate))
            throw new AllException.InvalidDate("STARTING DATE SHOULD BE BEFORE DELIVERY DATE!");
        return false;
    }
    //هاد التابع بس نعمل مهمة جديدة منستدعيه بالثريد
    public void doingAMission(Mission mission){
        mission.setState(State.IN_PROGRESS);
        mission.doneProducts++;
        mission.accomplishLevel=(mission.orderedQuantity-mission.doneProducts);

    }

    }


