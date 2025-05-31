import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String dialoguePath = "C:\\Users\\megha\\zucktest\\dialogue.txt";
        File file = new File(dialoguePath);
        Scanner finch = new Scanner(file);
        removeShort();

    }

    public static int randomLine() throws IOException {
        long linenum = Files.lines(Paths.get("C:\\Users\\megha\\zucktest\\dialogue.txt")).count();
        int rand = (int) Math.floor(Math.random() * (linenum - 1));
        return rand;

    }

    public static void removeShort() throws IOException {
        File file = new File("C:\\Users\\megha\\zucktest\\dialogue.txt");
        FileWriter cloo;
        try (Scanner bloo = new Scanner(file)) {
            cloo = new FileWriter("C:\\Users\\megha\\zucktest\\lines.txt", true);
            while(bloo.hasNextLine()) {
                String line = bloo.nextLine().trim();
                int count = 0;
                
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        count++;
                    }
                }
                
                if((count > 3)) {
                    cloo.write(line + "\n");
                }
            }
        }
        cloo.close();
    }
}
