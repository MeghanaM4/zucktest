//things to add:
//fix ?s with splitting
//split up long monologues (>2 sentences) (looking at you justin timberlake)
//if the firstHalf has <3 words then add a word from the second half to the first
//you get points for how many letters you match with SecondHalf
//hard mode includes punctuation
//remove lines from irrelevant characters (?)

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static File dialogueFile = new File("C:\\Users\\megha\\zucktest\\dialogue.txt");
    public static String linesPath = "C:\\Users\\megha\\zucktest\\lines.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(linesPath);
        Scanner finch = new Scanner(file);
        Scanner dook = new Scanner(System.in);
        boolean play = true;
        removeShort();

        while (play) {
            String randLine = randomLine();
            String firstHalf = getFirstHalf(randLine);
            String cans = randLine.substring(firstHalf.length()).trim(); // dude I am so smart (cans = correct answer)
            // make it all lowercase and remove all punctuation
            String cansFiltered = cans.toLowerCase().replaceAll("[\\.!,\\?:;\\-']", "");

            System.out.println(firstHalf);
            String pans = dook.nextLine(); // player answer (pans)
            // make it all lowercase and remove all punctuation
            String pansFiltered = pans.toLowerCase().replaceAll("[\\.!,\\?:;\\-']", "");

            if (pansFiltered.equalsIgnoreCase(cansFiltered)) {
                System.out.println("Congrats, you need to get outside more.");
                System.out.println("");
            } else if (pans.equalsIgnoreCase("quit")) {
                dook.close();
                play = false;
            } else {
                System.out.println("Answer: " + cans + "\n");
            }
        }

    }

    public static String randomLine() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(linesPath))) {
            long numLines = Files.lines(Paths.get(linesPath)).count();
            int rand = (int) Math.floor(Math.random() * (numLines - 1));
            int currentLineNum = 1;
            String line;
            
            // is this the most efficient way to do this? I have no idea. Also I need to
            // decide between BufferedReader and FileReader and Scanner
            while ((line = reader.readLine().trim()) != null) {
                if (currentLineNum == rand) {
                    return line;
                }
                currentLineNum++;
            }
        }
        return null;

    }

    public static int countSpaces(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }

    public static String getFirstHalf(String line) throws IOException {
        String[] words = line.split(" ");
        int mid = words.length / 2;

        StringBuilder firstHalfBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i < mid) {
                firstHalfBuilder.append(words[i]).append(" ");
            }
        }

        String firstHalf = firstHalfBuilder.toString().trim();
        return firstHalf;
    }

    public static void removeShort() throws IOException {
        FileWriter cloo;
        try (Scanner bloo = new Scanner(dialogueFile)) {
            cloo = new FileWriter(linesPath, true);
            while (bloo.hasNextLine()) {
                String line = bloo.nextLine().trim();

                if ((countSpaces(line) > 3)) {
                    cloo.write(line + "\n");
                }
            }
        }
        cloo.close();
    }
}
