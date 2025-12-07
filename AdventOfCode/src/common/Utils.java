package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    
    public static ArrayList<String> getInput(BufferedReader reader) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            input.add(line);
        }
        return input;
    }
}

