
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
                        !line.matches("^\\(.*\\)$") && !line.matches("^\\d{1,3} \\.$")) {
                    sorkin.write(line + "\n");
                }

                // if the line has 10 spaces (11 words) and is more than 2 sentences then split
                // the line up
                if ((countChar(line, ' ') > 10) && countChar(line, '.') > 2) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '.') {
                            String sentence = "";
                            for (int j = 0; j < i; j++) {
                                sentence += line.charAt(j);
                                sorkin.write(sentence + "\n");
                            }
                            line = line.substring(i, line.length()); // holy shoot I feel like a genius
                        }

                    }
                }
            }
        }

    }

    public static int countChar(String line, char cha) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == cha) {
                count++;
            }
        }
        return count;
    }
}