// remove all scene description lines, CUT TOs:, (beat)s, and those weird page number lines

import java.io.*;
import java.util.Scanner;

public class removeweird {
    public static void main(String[] args) throws IOException {
        String scriptPath = "C:\\Users\\megha\\zucktest\\socialnetworkRaw.txt";
        String newpath = "C:\\Users\\megha\\zucktest\\socialnetwork.txt";
        File file = new File(scriptPath);
        try (Scanner davidfinch = new Scanner(file);
            FileWriter sorkin = new FileWriter(newpath, true)) {
                while(davidfinch.hasNextLine()) {
                    String line = davidfinch.nextLine().trim();
                    if(!line.matches(".*(INT\\.|EXT\\.|CUT TO:|CUT BACK TO:).*") && 
                       !line.matches("^\\(.*\\)$") && !line.matches("^\\d{1,3} \\.$")) {
                        sorkin.write(line + "\n");
                    }
                }
            }
    }
}
