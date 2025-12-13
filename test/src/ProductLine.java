import java.util.LinkedList;
import java.util.Queue;

public class ProductLine {
    private int lineId;
    private String lineName;
    private LineState lineState;
    private Queue<Mission> missions;
    public ProductLine(int lineId, String lineName, LineState lineState, Queue<Mission> missions){
        this.lineId=lineId;
        this.lineName=lineName;
        this.lineState=lineState;
        this.missions=new LinkedList<>(missions);
    }
    //Adds an element to the queue.
    public void addMission(Mission mission){
        missions.offer(mission);
    }
    //Retrieves and removes the element at the front (head) of the queue.
    public void removeDefaultMission() throws AllException.NoMissionException {
        if(!missions.isEmpty()){
           Mission m= missions.remove();
            System.out.println("THE MISSION "+m+"HAS BEEN REMOVED SUCCESSFULLY!");
        }
        else
            throw new AllException.NoMissionException("THERE IS NO MISSIONS TO REMOVE!");
    }
    //Returns the element at the front (head) of the queue without removing it
    public Mission getDefaultMission() throws AllException.NoMissionException {
        if (!missions.isEmpty())
            return missions.peek();
            else
            throw new AllException.NoMissionException("THERE IS NO MISSIONS YET!");

    }
    public void removeSpecificMission(Mission m) throws AllException.NoMissionException {
        if(missions.contains(m)){
            missions.remove(m);
        }
        else
            throw new AllException.NoMissionException("THIS MISSION DOES NOT EXIST!");
    }
    public Mission getSpecificMission(Mission m) throws AllException.NoMissionException {
        if(missions.contains(m))
            return m;
        else
            throw new AllException.NoMissionException("THIS MISSION DOES NOT EXIST!");
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


