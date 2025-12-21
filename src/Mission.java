
import java.time.LocalDate;

public class Mission {
//automatically change the id
    private static int missionId=1;
    private static synchronized int missionId(){
        return missionId++;
    }
    private int id;
    private Product acquiredProduct;
    private int orderedQuantity;
    private String client;
    private LocalDate startingDate;
    private LocalDate  deliveryDate;
    private State state;
    private ProductLine productLine;
    private double accomplishLevel;
    private int doneProducts = 0;

    //CONSTRUCTOR
    public Mission(Product acquiredProduct,int orderedQuantity,String client,LocalDate startingDate,
                   LocalDate deliveryDate,ProductLine productLine){
        this.acquiredProduct=acquiredProduct;
        this.id=missionId();
        this.orderedQuantity=orderedQuantity;
        this.client=client;
        this.startingDate=startingDate;
        this.deliveryDate=deliveryDate;
        this.productLine=productLine;
        this.state=State.IN_PROGRESS;
        this.accomplishLevel=0;
        this.doneProducts=0;
    }

    //GETTERS
    public int getMissionId() {
        return id;
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

    public double getAccomplishLevel() {
        return accomplishLevel;
    }

    public int getDoneProducts() {
        return doneProducts;
    }

    //SETTERS
    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public void setAcquiredProduct(Product acquiredProduct) {
        this.acquiredProduct = acquiredProduct;
    }

    public void setOrderedQuantity(int orderedQuantity) throws AllException.InvalidQuantityException {
        if(orderedQuantity>=0)
            this.orderedQuantity = orderedQuantity;
        else{
            this.orderedQuantity = 0;
            throw new AllException.InvalidQuantityException("ORDERED QUANTITY CANNOT BE NEGATIVE!");
        }
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    //default delivery date is LocalDate.now()
    public void setDeliveryDate(LocalDate deliveryDate) throws AllException.InvalidDateException {
        if(deliveryDate.isAfter(this.startingDate))
            this.deliveryDate = deliveryDate;
        else{
            this.deliveryDate=LocalDate.now();
            throw new AllException.InvalidDateException("DELIVERY DATE CANNOT BE BEFORE STARTING DATE!");
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    //Links with the thread in a ProductLine class to update automatically
    public synchronized void updateProgress(int amount) {
        if (this.doneProducts + amount <= this.orderedQuantity) {
            this.doneProducts += amount;

            //Automatically calculate the completion percentageً
            this.accomplishLevel = (((double) this.doneProducts * 100.0) / this.orderedQuantity);

            //Once the quantity is complete, we change the status of the task
            if (this.doneProducts == this.orderedQuantity) {
                this.state = State.COMPLETED;
            }
        }
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