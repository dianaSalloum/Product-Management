package io;

import java.io.BufferedReader;
import java.io.FileReader;

public class ErrorsReader {

    private static final String PATH = "error.txt";

    public static String errorReader() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            sb.append("No errors found.\n");
        }

        return sb.toString();
    }
}