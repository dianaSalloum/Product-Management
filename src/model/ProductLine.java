

package model;
import io.ErrorLogger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductLine implements Runnable {
    //automatically change the id
/*creates a static variable and make it behave as a counter,
so every time we create an object it will increase using the synchronized method below.
 */
    private static int lineId = 1;
    private static synchronized int Lineid() {
        return lineId++;
    }
    private int id;
    private String lineName;
    private LineState lineState;
    private Queue<Mission> missions;
    private Inventory inventory;
    private boolean isRunning = true;
    private String lastErrorMessage = "";
    private List<Mission> completedMissions=new ArrayList<>();
    private List<Mission>inProgress=new ArrayList<>();
    private List<Product>doneProductsList=new ArrayList<>();
    private List<Mission> allMissions= new ArrayList<>();
    //constructor
    public ProductLine(String lineName, Inventory inventory) {
        this.id = Lineid();
        this.lineName = lineName;
        this.missions = new LinkedList<>();
        this.inventory = inventory;
        this.lineState = LineState.STOP;
    }
    /* note:the while loop is an infinite loop that will stop when the thread is dead,
    we will see if we have a mission and our product line is activated so we can do the current mission ,
    in a try block we will try to consume the quantities from the inventory, this consuming method will throw an exception
    if we do not have enough materials (the logic is we either do the whole mission or no)
    if we consumed successfully(without exception) then we will update the progress and the accomplish level
     we will add to the inventory the quantity we produced,and put the product in a done product list
    the progress method will change the state and make it completed mission .
    so we also put our completed mission in a list and then delete the mission
    but if an exception occured which means we cannot produce cause of low stock we catch this exception
    the mission will be in waiting state till the user choose weather he wants to wait or cancel it
    so the thread will sleep till he chooses the state.
    this all will happen when the product line is active & we have a mission
    but if the product line state was STOP then nothing will happen till it activates so sleep
    and if it is in MAINTENANCE then the mission will wait (the maintenance mood can be activated weather there is
    a mission or not).
    and if all of this did not occur then sleep(do not have any mission)
     */
    @Override
    public void run() {
        while (isRunning) {

            // 1️⃣ إذا الخط مو شغال → راقب بس
            if (lineState != LineState.ACTIVE) {
                sleepThread();
                continue;
            }

            // 2️⃣ إذا ما في مهام → راقب
            Mission currentMission = missions.peek();
            if (currentMission == null) {
                sleepThread();
                continue;
            }

            // 3️⃣ إذا المهمة ملغاة → شيلها
            if (currentMission.getState() == State.CANCELED) {
                missions.poll();
                continue;
            }

            try {
                // 4️⃣ جرّب تستهلك المواد
                inventory.itemConsumption(
                        currentMission.getAquiredProduct(),
                        currentMission.getOrderedQuantity()
                );

                // 5️⃣ الإنتاج
                for (int i = 0; i < currentMission.getOrderedQuantity(); i++) {

                    // إذا انلغت أثناء التنفيذ
                    if (currentMission.getState() == State.CANCELED)
                        break;

                    sleepThread(); // محاكاة الزمن
                    currentMission.updateProgress(1);
                }

                // 6️⃣ بعد النجاح
                if (currentMission.getState() == State.COMPLETED) {
                    inventory.addProduct(
                            currentMission.getAquiredProduct(),
                            currentMission.getOrderedQuantity()
                    );

                    completedMissions.add(currentMission);
                    doneProductsList.add(currentMission.getAquiredProduct());
                }

                missions.poll(); // شيل المهمة

            } catch (AllException.LowStockException | AllException.InvalidQuantityException ex  ) {

                // 7️⃣ فشل → انتظار بدون تعليق
                currentMission.setState(State.WAITING);
                lastErrorMessage = ex.getMessage();
                ErrorLogger.logError(ex);

                // نترك المهمة بالكيو ونرجع نراقب
                sleepThread();
            } catch (AllException.InvalidNumberException ex) {
                ErrorLogger.logError(ex);
            }

        }
    }
    //getters:
    //get done products
    public synchronized List<Product> getDoneProductsList(){
        return doneProductsList;
    }
    //get in progress Missions
    public synchronized List<Mission> getInProgressMissions(){
        inProgress.clear();
        for(Mission mission:missions)
            if(mission.getState()==State.IN_PROGRESS)
                inProgress.add(mission);
        return inProgress;

    }

    public List<Mission> getAllMissions() {
        return allMissions;
    }

    //get last error message
    public synchronized String getLastErrorMessage() {
        String msg = lastErrorMessage;
        lastErrorMessage = "";
        return msg;
    }
    //get completed missions
    public synchronized List<Mission> getCompletedMissions() {
        return new ArrayList<>(completedMissions);
    }
    //get the product line id
    public synchronized int getId(){
        return id;
    }
    //get the missions
    public synchronized Queue<Mission> getMissions() {return missions;}
    //get linestate
    public LineState getLineState(){
        return lineState;
    }

    public String getLineName() {
        return lineName;
    }

    //setters:
    //setting the product line state
    public synchronized void setLineState(LineState lineState) {this.lineState = lineState;}
    //set running
    public synchronized void setRunning(boolean running) {isRunning = running;}
    //methods:
    //show all missions for a specific product in the productLine
    public synchronized List<Mission> missionsForProduct(int productId){
        List<Mission>missionList=new ArrayList<>();
        for(Mission mission:missions)
            if(mission.getAquiredProduct().getProductId()==productId)
                missionList.add(mission);
        return missionList;
    }
    //Adds a mission.
    public synchronized void addMission(Mission mission){
        missions.offer(mission);
    }
    //find a mission
    public synchronized Mission findMission(int id) throws AllException.MissionNotFoundException {
        for(Mission mission: missions)
            if(mission.getMissionId()==id)
                return mission;
        throw new AllException.MissionNotFoundException("CANNOT FIND MISSION!");
    }
    //remove specific mission
    public synchronized void removeMission(int missionId) throws AllException.MissionNotFoundException {
        Mission mission=findMission(missionId);
        if(!missions.isEmpty()) {
            missions.remove(mission);
            mission.setState(State.CANCELED);
        }
    }
    //wait if low stock
    public void sleepThread(){
        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    //toString
    @Override
    public String toString() {
        return "ProductLine{" +
                "lineId=" + id +
                ", lineName='" + lineName + '\'' +
                ", lineState=" + lineState +
                ", remaining tasks=" + missions+
                '}';
    }
}

