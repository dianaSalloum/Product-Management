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
    }

}
