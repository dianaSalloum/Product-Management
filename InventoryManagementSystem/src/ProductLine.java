import java.util.LinkedList;
import java.util.Queue;
//CONSTRUCTOR
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
//GETTERS

    public int getLineId() {
        return lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public LineState getLineState() {
        return lineState;
    }

    public Queue<Mission> getMissions() {
        return missions;
    }
//SETTERS

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setLineState(LineState lineState) {
        this.lineState = lineState;
    }

    @Override
    public String toString() {
        return "ProductLine{" +
                "lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", lineState=" + lineState +
                ", missions=" + missions +
                '}';
    }
}
