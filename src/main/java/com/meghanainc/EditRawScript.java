package com.meghanainc;

//remove all scene description lines, CUT TOs:, (beat)s, and those weird page number lines

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditRawScript {
    public static void main(String[] args) throws IOException {
        String scriptPath = "src\\main\\resources\\socialnetworkRaw.txt";
        String newpath = "src\\main\\resources\\socialnetwork.txt";
        File file = new File(scriptPath);
        try (Scanner davidfinch = new Scanner(file);
                FileWriter sorkin = new FileWriter(newpath, true)) {
                    
            while (davidfinch.hasNextLine()) {
                String line = davidfinch.nextLine().trim();
                if (!line.matches(".*(INT\\.|EXT\\.|CUT TO:|CUT BACK TO:).*") &&
                        !line.matches("^\\(.*\\)$") && !line.matches(".*\\d{1,3}\\s*\\..*") && 
                        !line.contains(")") && !line.contains("(")) {
                    sorkin.write(line + "\n");
                }
            }
        }

    }
}