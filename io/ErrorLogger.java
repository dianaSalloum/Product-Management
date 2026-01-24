package io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ErrorLogger {
    //the file path that we are writing on
    private static final String LOG_FILE = "error.txt";

    public static void logError(Exception e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {

            pw.println("=================================");
            pw.println("Time: " + LocalDateTime.now());
            pw.println("Exception: " + e.getClass().getName());
            pw.println("Message: " + e.getMessage());
            pw.println("Stack Trace:");
            e.printStackTrace(pw);
            pw.println("//////");

        } catch (IOException ex) {

        }
    }
}
