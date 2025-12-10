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


