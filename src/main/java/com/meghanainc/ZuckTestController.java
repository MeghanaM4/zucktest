package com.meghanainc;

//things to add:
//fix stupid scanner problem
//fix ?s with em dashes
//split up long monologues (>2 sentences) (looking at you justin timberlake)
//you get points for how many letters you match with SecondHalf
//hard mode includes punctuation
//remove lines from irrelevant characters (?)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")

public class ZuckTestController {
    public static File dialogueFile = new File("src\\main\\resources\\dialogue.txt");
    public static final String linesPath = "src\\main\\resources\\lines.txt";

    @GetMapping("/newquote")
    public Map<String, Object> getNewQuote() {
        try {
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
                    if (cans.charAt(i) != ' ') {
                        extraWord += cans.charAt(i);
                    } else {
                        break; 
                    }
                }
                cans = cans.substring(extraWord.length()); // okay this feels cool
                firstHalf = extraWord + " " + firstHalf;

            }

            Map<String, Object> response = new HashMap<>();
            response.put("prompt", firstHalf);
            response.put("answer", cans);
            response.put("success", true);
            return response;


            } catch (IOException e) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("error", "Failed to load quote" + e.getMessage());
                return errorResponse;
            }

        }
        
        
            @PostMapping("/checkanswer")
            public Map<String, Object> checkAnswer(@RequestBody Map<String, String> request) {

            String pans = request.get("userAnswer"); //pans (player answer)
            String cans = request.get("correctAnswer");

            // make it all lowercase and remove all punctuation
            String cansFiltered = cans.toLowerCase().replaceAll("[\\.!,\\?:;\\-']", "");

            // make it all lowercase and remove all punctuation
            String pansFiltered = pans.toLowerCase().replaceAll("[\\.!,\\?:;\\-']", "");


            boolean isCorrect = pansFiltered.equalsIgnoreCase(cansFiltered);
        int points = matcher(pansFiltered, cansFiltered);
        
        Map<String, Object> response = new HashMap<>();
        response.put("correct", isCorrect);
        response.put("points", points);
        response.put("correctAnswer", cans);
        response.put("success", true);
        return response;
    

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
        //lowercase + remove punctuation
        pans = pans.toLowerCase().replaceAll("[^a-z0-9 ]", "");
        cans = cans.toLowerCase().replaceAll("[^a-z0-9 ]", "");

        //split into words
        String[] pansWords = pans.split("\\s+");
        String[] cansWords = cans.split("\\s+");

        Set<String> wordSet = new HashSet<>(Arrays.asList(pansWords));

        int count = 0;
        for (String word : cansWords) {
            if (wordSet.contains(word)) {
                count++;
                wordSet.remove(word);
            }
        }

        return count;

    }
}