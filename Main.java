// a script to isolate all the dialogue of the social network

import java.io.*;
import java.util.Scanner;

public class Main {

    static String[] characters = { "MARK", "EDUARDO", "CHRIS", "DUSTIN", "SEAN", "JENNY", "ALICE", "DIVYA", "TYLER",
                    "CAMERON", "SY", "GRETCHEN", "ERICA", "MARYLIN", "LARRY", "AMY", "PRINCE ALBERT"};

    public static void main(String[] args) throws IOException {
        String scriptPath = "C:\\Users\\megha\\zucktest\\socialnetwork.txt";
        File file = new File(scriptPath);
        try (Scanner davidfinch = new Scanner(file);
                FileWriter sorkin = new FileWriter("C:\\Users\\megha\\zucktest\\dialogue.txt", true)) {

            while (davidfinch.hasNextLine()) {
                String line = davidfinch.nextLine().trim();

                for (String character : characters) {
                    // if the line has only MARK, or has MARK (V.O.), then save the next line
                    if ((line.equals(character + " (V.O.)")) || (line.equals(character + " (CONT'D)")) || (line.equals(character))) {

                        if (!davidfinch.hasNextLine()) break;
                        String dialogue = davidfinch.nextLine().trim();

                        // if the line after it has (beat) or something then save the nextnextline
                        if(dialogue.startsWith("(")) {
                            if (!davidfinch.hasNextLine()) break;
                            dialogue = davidfinch.nextLine().trim();
                        }

                        String block = checkCompleteSentence(dialogue, sorkin, davidfinch);
                        sorkin.write(block + "\n");

                    }
                }
            }

        }

    }

    // get the complete sentence/paragraph block the speaker is saying given the starting line of that block
    public static String checkCompleteSentence(String startLine, FileWriter sorkin, Scanner davidfinch)
            throws IOException {
        StringBuilder fullBlock = new StringBuilder();
        fullBlock.append(startLine.trim());

        while (davidfinch.hasNextLine()) {
            String nextLine = davidfinch.nextLine().trim();

            for (String character : characters) {
            if (nextLine.equals(character) || nextLine.equals(character + " (V.O.)")) {
                return fullBlock.toString().trim();
            }
        }
            fullBlock.append(" ").append(nextLine);
        }
        return fullBlock.toString().trim();
    }

}
