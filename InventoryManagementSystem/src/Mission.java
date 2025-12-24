
import java.time.LocalDate;

public class Mission {
//automatically change the id
/*creates a static variable and make it behave as a counter,
so every time we create an object it will increase using the synchronized method below.
 */
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

    //constructor
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

    //getters
    public int getMissionId() {
        return id;
    }

    public Product getAquiredProduct() {
        return acquiredProduct;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public State getState() {
        return state;
    }

    public double getAccomplishLevel() {
        return accomplishLevel;
    }

    //setters
    public void setState(State state) {
        this.state = state;
    }
    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    //methods

    //Links with the thread in a ProductLine class to update automatically
    //when we are doing a mission it will upgrade the done product when we reach the ordered quantity
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
    //toString
    @Override
    public String toString() {
        return "Mission{" +
                "missionId=" + id+
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