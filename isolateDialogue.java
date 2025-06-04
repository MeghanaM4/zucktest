// a script to isolate all the dialogue of the social network

import java.io.*;

public class isolateDialogue {

    static String[] characters = { "MARK", "EDUARDO", "CHRIS", "DUSTIN", "SEAN", "JENNY", "ALICE", "CHRISTY", "DIVYA",
            "TYLER", "CAMERON", "SY", "GRETCHEN", "ERICA", "MARYLIN", "LARRY", "AMY", "PRINCE ALBERT", "GAGE",
            "SUMMERS" };

    public static void main(String[] args) throws IOException {
        String scriptPath = "C:\\Users\\megha\\zucktest\\socialnetwork.txt";
        String dialoguePath = "C:\\Users\\megha\\zucktest\\dialogue.txt";

        BufferedWriter sorkin;
        try (BufferedReader davidfinch = new BufferedReader(new FileReader(scriptPath))) {
            sorkin = new BufferedWriter(new FileWriter(dialoguePath, false));
            String line;

            while ((line = davidfinch.readLine()) != null) {
                line = line.trim();

                for (String character : characters) {
                    if (line.equals(character) || line.equals(character + " (V.O.)")
                            || line.equals(character + " (CONT'D)")) {

                        davidfinch.mark(10000); // mark current position
                        String next = davidfinch.readLine();

                        if (next == null)
                            break;
                        next = next.trim();

                        if (next.startsWith("(")) {
                            next = davidfinch.readLine();
                            if (next == null)
                                break;
                            next = next.trim();
                        }

                        // if the next line isn't SEAN or INT. HARVARD SQUARE
                        if (!next.matches(".*\\b[A-Z\\-]{2,}\\b.*")) {
                            String full = checkCompleteSentence(next, davidfinch);
                            sorkin.write(full + "\n");
                        }
                    }
                }
            }
        }
        sorkin.close();

    }

    // get the complete sentence/paragraph block the speaker is saying given the
    // starting line of that block
    public static String checkCompleteSentence(String startLine, BufferedReader davidfinch)
            throws IOException {
        StringBuilder fullBlock = new StringBuilder();
        fullBlock.append(startLine.trim());

        String nextLine;
        while ((nextLine = davidfinch.readLine()) != null) {
            nextLine = nextLine.trim();

            for (String character : characters) {
                if ((nextLine.equals(character) || nextLine.equals(character + " (V.O.)")
                        || nextLine.equals(character + " (CONT'D)") || nextLine.contains(character))) {
                    davidfinch.reset();
                    return fullBlock.toString().trim();
                }
            }

            // went from {2,} to {3,} to {4,} to {5,} because IQ's, SAT's, and WGET were
            // all deleted by accident lmao
            if (nextLine.matches(".*\\b[A-Z\\-]{5,}\\b.*")) {
                davidfinch.reset();
                return fullBlock.toString().trim();
            }

            fullBlock.append(" ").append(nextLine);
        }
        return fullBlock.toString().trim();

    }

}
