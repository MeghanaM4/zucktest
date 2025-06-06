//things to add:
//fix stupid scanner problem
//fix ?s with em dashes
//split up long monologues (>2 sentences) (looking at you justin timberlake)
//you get points for how many letters you match with SecondHalf
//hard mode includes punctuation
//remove lines from irrelevant characters (?)

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static File dialogueFile = new File("C:\\Users\\megha\\zucktest\\dialogue.txt");
    public static String linesPath = "C:\\Users\\megha\\zucktest\\lines.txt";

    public static void main(String[] args) throws IOException {
        boolean play = true;
        int points = 0;

        while (play) {
            Scanner dook = new Scanner(System.in);
            String randLine = randomLine();
            String firstHalf = getFirstHalf(randLine);
            String cans = getSecondHalf(randLine).trim(); // cans = correct answer

            // if the firstHalf has <4 words then add a word from the second half to the
            // first
            // I was going to put this in getFirstHalf() but you need the second half to do
            // this properly
            // and it's all very sad
            if (countSpaces(firstHalf) < 3) {
                String extraWord = "";
                for (int i = 0; i < cans.length(); i++) {
                    while (cans.charAt(i) != ' ') {
                        extraWord += cans.charAt(i);
                    }
                }
                cans = cans.substring(extraWord.length()); // okay this feels cool
                extraWord += " " + firstHalf;
                firstHalf = extraWord; // god this is depressing

            }

            // make it all lowercase and remove all punctuation
            String cansFiltered = cans.toLowerCase().replaceAll("[\\.!,\\?:;\\-']", "");

            System.out.println(firstHalf);
            String pans = dook.nextLine(); // player answer (pans)
            // make it all lowercase and remove all punctuation
            String pansFiltered = pans.toLowerCase().replaceAll("[\\.!,\\?:;\\-']", "");

            if (pansFiltered.equalsIgnoreCase(cansFiltered)) {
                System.out.println("Congrats, you need to get outside more.");
                points += matcher(pans, cans);
                System.out.println("Your score is " + points);
            } else if (pans.equalsIgnoreCase("quit")) {
                dook.close();
                System.out.print("Your score was " + points);
                play = false;
            } else {
                System.out.println("Answer: " + cans);
                points += matcher(pans, cans);
                System.out.println("Your score is " + points);
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

        for (int i = 0; i < mid; i++) {
            firstHalfBuilder.append(words[i]).append(" ");
        }

        String firstHalf = firstHalfBuilder.toString().trim();

        return firstHalf;
    }

    public static String getSecondHalf(String line) throws IOException {
        String[] words = line.split(" ");
        int mid = words.length / 2;

        StringBuilder secondHalfBuilder = new StringBuilder();

        for (int i = mid; i < words.length; i++) {
            secondHalfBuilder.append(words[i]).append(" ");

        }

        String secondHalf = secondHalfBuilder.toString().trim();
        return secondHalf;
    }

    public static int matcher(String pans, String cans) {
        // Normalize: lowercase + remove punctuation
        pans = pans.toLowerCase().replaceAll("[^a-z0-9 ]", "");
        cans = cans.toLowerCase().replaceAll("[^a-z0-9 ]", "");

        // Split into words
        String[] pansWords = pans.split("\\s+");
        String[] cansWords = cans.split("\\s+");

        // Add words from string1 to a set
        Set<String> wordSet = new HashSet<>(Arrays.asList(pansWords));

        int count = 0;
        for (String word : cansWords) {
            if (wordSet.contains(word)) {
                count++;
                wordSet.remove(word); // Optional: prevent double-counting
            }
        }

        return count;

    }
}
