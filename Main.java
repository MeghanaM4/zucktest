// a script to isolate all the dialogue of the social network

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String scriptPath = "C:\\Users\\megha\\zucktest\\socialnetwork.txt";
        File file = new File(scriptPath);
        try (Scanner davidfinch = new Scanner(file);
                FileWriter sorkin = new FileWriter("C:\\Users\\megha\\zucktest\\dialogue.txt", true)) {

            String[] characters = { "MARK", "EDUARDO", "CHRIS", "DUSTIN", "SEAN", "JENNY", "ALICE", "DIVYA", "TYLER",
                    "CAMERON", "SY", "GRETCHEN", "ERICA", "MARYLIN", "LARRY" };

            while (davidfinch.hasNextLine()) {
                String line = davidfinch.nextLine();
                String nextLine = davidfinch.nextLine();
                for (String character : characters) {
                    // if the line has only MARK and the next line has (beat) then save the next
                    // next line
                    if (line.trim().equals(character) && nextLine.contains("(")) {
                        String nextNextLine = davidfinch.nextLine();
                        System.out.print("1 " + nextNextLine);
                        sorkin.write(checkCompleteSentence(nextNextLine, sorkin, davidfinch) + "\n");

                        // if the line has MARK (V.O.) save the next line
                    } else if (line.trim().equals(character + " (V.O.)")) {
                        System.out.print("2 " + nextLine);
                        sorkin.write(checkCompleteSentence(nextLine, sorkin, davidfinch) + "\n");

                        // if the line has MARK and the next line doesn't have (beat), save the next
                        // line
                    } else if (line.trim().equals(character) && !(nextLine.contains("("))) {
                        System.out.print("3 " + nextLine);
                        sorkin.write(checkCompleteSentence(nextLine, sorkin, davidfinch) + "\n");
                    }
                }
            }

        }

    }

    // get the complete sentence/paragraph block the speaker is saying given the starting line of that block
    public static String checkCompleteSentence(String startLine, FileWriter sorkin, Scanner davidfinch)
            throws IOException {
        StringBuilder fullBlock = new StringBuilder();
        fullBlock.append(startLine);

        while (!startLine.trim().matches(".*[.?!:]$")) {
            if (!davidfinch.hasNextLine()) {
                break; 
            }
            String line = davidfinch.nextLine();
            fullBlock.append(line);
        }
        return fullBlock.toString().trim();
    }

}
