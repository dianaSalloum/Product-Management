//ما الو داعي التابع عم يعملا49
//line 8
//line21 removed id from constructor
import java.util.LinkedList;
import java.util.Queue;

public class ProductLine implements Runnable{
    //automatically changing the id
    private static int lineId=1;
private static synchronized int Lineid(){
    return lineId++;
}
    private int id;
    private String lineName;
    private LineState lineState;
    private Queue<Mission> missions;
    private Inventory inventory;
    private boolean isRunning = true;
    private String lastErrorMessage = "";

    public ProductLine(String lineName, Inventory inventory){
        this.id=Lineid();
        this.lineName = lineName;
        this.missions = new LinkedList<>();
        this.inventory = inventory;
        this.lineState=LineState.STOP;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                if(!missions.isEmpty() && lineState == LineState.ACTIVE){
                    Mission currentMission = missions.peek();
                    System.out.println("Line " + lineName + " started working on Mission: " + currentMission.getMissionId());
                    try {
                        //Requesting materials from the factory
                        inventory.itemConsumption(currentMission.getAquiredProduct(), currentMission.getOrderedQuantity());
                        //Production Process Simulation
                        for (int i = 0; i < currentMission.getOrderedQuantity(); i++) {
                            if (currentMission.getState() == State.CANCELED)
                                break;
                            Thread.sleep(1000);
                            //Update the output piece by piece (in Mission class)
                            currentMission.updateProgress(1);
                        }
                        inventory.addProduct(currentMission.getAquiredProduct(),currentMission.getOrderedQuantity());
                       // currentMission.setState(State.COMPLETED);
                        missions.poll();
                        System.out.println("Mission: " + currentMission.getMissionId() + " COMPLETED by: " + lineName);
                    }
                    catch (AllException.LowStockException|AllException.InvalidQuantityException ex) {
                        this.lastErrorMessage = "Low Stock for Mission " + currentMission.getMissionId() + ": " + ex.getMessage();
                        currentMission.setState(State.WAITING);
                        while (currentMission.getState()==State.WAITING)
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ex) {
                        System.out.println("Line " + lineName + " was interrupted.");
                        break;
                    }
                }
                //If there is no missions
                else if(!missions.isEmpty()&&lineState==LineState.STOP){
                    Thread.sleep(1000);
                }
                //سواء في مهام او لا حالة الصيانة منعالجا
                else if(lineState==LineState.MAINTENANCE){
                    if(!missions.isEmpty()){
                    Mission currentMission = missions.peek();
                    currentMission.setState(State.WAITING);
                    while (currentMission.getState()==State.WAITING)
                        Thread.sleep(1000);}
                    else
                        Thread.sleep(1000);
                }
                else
                    Thread.sleep(1000);
            }
            catch (InterruptedException ex) {
                System.out.println("Line " + lineName + " was interrupted.");
                break;
            }
        }
    }

    //Return last massage
    public String getLastErrorMessage() {
        String msg = lastErrorMessage;
        lastErrorMessage = "";
        return msg;
    }

    //Adds an element to the queue.
    public synchronized void addMission(Mission mission){
        missions.offer(mission);
    }

    public synchronized void setLineState(LineState lineState) {
        this.lineState = lineState;
    }

    public synchronized LineState getLineState() {
        return lineState;
    }

    public synchronized void stopThread() {
        this.isRunning=false;}
    //GETTERS


    public int getId() {
        return id;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Queue<Mission> getMissions() {
        return missions;
    }

    public void setMissions(Queue<Mission> missions) {
        this.missions = missions;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public synchronized void setRunning(boolean running) {
        isRunning = running;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    @Override
    public String toString() {
        return "ProductLine{" +
                "lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", lineState=" + lineState +
                ", tasks=" + missions+
                '}';
    }
}