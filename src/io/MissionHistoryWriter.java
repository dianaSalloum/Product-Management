
package io;

import model.Mission;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class MissionHistoryWriter {

    private static final String FILE_NAME = "missions_history.txt";

    public static void logMission(Mission mission) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {

            fw.write(
                    "Time: " + LocalDateTime.now() +
                            " | MissionId: " + mission.getMissionId() +
                            " | Product: " + mission.getAquiredProduct().getProductName() +
                            " | OrderedQty: " + mission.getOrderedQuantity() +
                            " | DoneQty: " + mission.getDoneProducts() +
                            " | State: " + mission.getState() +
                            "\n"
            );

        } catch (IOException e) {
            ErrorLogger.logError(e);
        }
    }
}
