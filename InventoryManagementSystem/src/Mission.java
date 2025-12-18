import java.time.LocalDate;

public class Mission {
    private int missionId;
    private Product acquiredProduct;
    private int orderedQuantity;
    private String client;
    private LocalDate startingDate;
    private LocalDate  deliveryDate;
    private State state;
    private ProductLine productLine;
    private int accomplishLevel;
    private int doneProducts;
    //CONSTRUCTOR
    public Mission(int missionId,Product acquiredProduct,int orderedQuantity,String client,LocalDate startingDate,LocalDate deliveryDate,ProductLine productLine){
        this.acquiredProduct=acquiredProduct;
        this.missionId=missionId;
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
    public int getMissionId() {
        return missionId;
    }

    public Product getAquiredProduct() {
        return acquiredProduct;
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
    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public void setAcquiredProduct(Product acquiredProduct) {
        this.acquiredProduct = acquiredProduct;
    }

    public void setOrderedQuantity(int orderedQuantity) throws AllExceptions.InvalidNumberException {
        if(orderedQuantity>=0)
        this.orderedQuantity = orderedQuantity;
        else{
            this.orderedQuantity=0;
            throw new AllExceptions.InvalidNumberException("ORDERED QUANTITY CANNOT BE NEGATIVE!");
        }
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }
//default delivery date is LocalDate.now()
    public void setDeliveryDate(LocalDate deliveryDate) throws AllExceptions.InvalidDateException {
        if(deliveryDate.isAfter(this.startingDate))
        this.deliveryDate = deliveryDate;
        else{
            this.deliveryDate=LocalDate.now();
            throw new AllExceptions.InvalidDateException("DELIVERY DATE CANNOT BE BEFORE STARTING DATE!");
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "missionId=" + missionId +
                ", acquiredProduct=" + acquiredProduct +
                ", orderedQuantity=" + orderedQuantity +
                ", client='" + client + '\'' +
                ", startingDate=" + startingDate +
                ", deliveryDate=" + deliveryDate +
                ", state=" + state +
                ", productLine=" + productLine +
                ", accomplishLevel=" + accomplishLevel +
                ", doneProducts=" + doneProducts +
                '}';
    }
}
