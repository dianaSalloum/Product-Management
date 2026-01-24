package model;

import java.time.LocalDate;

public class Mission {
    private static int missionId = 1;

    private static synchronized int missionId() {
        return missionId++;
    }

    private int id;
    private Product acquiredProduct;
    private int orderedQuantity;
    private String client;
    private LocalDate startingDate;
    private LocalDate deliveryDate;
    private State state;
    private ProductLine productLine;
    private double accomplishLevel;
    private int doneProducts;

    //CONSTRUCTOR
    public Mission(Product acquiredProduct, int orderedQuantity, String client, LocalDate startingDate,
                   LocalDate deliveryDate, ProductLine productLine) {
        this.acquiredProduct = acquiredProduct;
        this.id = missionId();
        this.orderedQuantity = orderedQuantity;
        this.client = client;
        this.startingDate = startingDate;
        this.deliveryDate = deliveryDate;
        this.productLine = productLine;
        this.state = State.WAITING;
        this.accomplishLevel = 0;
        this.doneProducts = 0;
    }

    //GETTERS
    public int getMissionId() {
        return id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
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


    public void setState(State state) {
        this.state = state;
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
        return "Mission{" + "\n" +
                "missionId=" + missionId +
                ", acquiredProduct=" + acquiredProduct.getProductName() +
                ", orderedQuantity=" + orderedQuantity + "\n" +
                ", client='" + client + '\'' +
                ", startingDate=" + startingDate +
                ", deliveryDate=" + deliveryDate + "\n" +
                ", state=" + state +
                ", productLine=" + productLine.getLineName() +
                ", accomplishLevel=" + accomplishLevel +
                '}' + "\n";
    }
}