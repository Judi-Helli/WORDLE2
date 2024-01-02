import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Wordle {

    // Constants for number of words and maximum tries.
    private static final int WORDS_COUNT = 2317;
    private static final int MAX_TRIES = 6;

    // load dictionary from the file.
    public static void main(String[] args) {
        String[] dictionary = new String[WORDS_COUNT];
        loadDictionary(dictionary, "dict.txt");

        // take a random word from the dictionary to be the keyword.
        String keyword = dictionary[new Random().nextInt(WORDS_COUNT)];
        int tries = Math.min(args.length, MAX_TRIES);

        // a loop through the tries
        for (int i = 0; i < tries; i++) {
            String guess = args[i].toUpperCase();
            System.out.printf("Try%d (%s): ", i + 1, guess);

            // Check these various conditions.
            if (guess.length() != 5) {
                System.out.println("The length of word must be five!");
            } else if (!wordExistsInDictionary(guess, dictionary)) {
                System.out.println("The word does not exist in the dictionary!");
            } else if (guess.equals(keyword)) {
                System.out.printf("Congratulations! You guess right in %d%s shot!\n", i + 1, getOrdinalSuffix(i + 1));
                return;
            } else {

                //calling the method that gives the hints.
                compareWords(keyword, guess);
            }
        }
        // in case the tries exceed the maximum or there is no words.

        if (tries > 4) {
            System.out.println("You exceeded maximum try shot!");
            System.out.printf("You failed! The key word is %s.\n", keyword);
        } else {
            System.out.println("please Enter exactly six words as a command line arguments");
        }
    }

    // the method that loads a dictionary file into an array of strings.
    private static void loadDictionary(String[] dictionary, String filename) {

        //to catch any exceptions that might occur while executing the code
        try {

            //reads all the lines from the file and stores them in a list of strings named (lines).
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < WORDS_COUNT; i++) {
                dictionary[i] = lines.get(i).toUpperCase();
            }
        } catch (Exception e) {
            System.out.println("Error loading dictionary file: " + e.getMessage());
            System.exit(1);
        }
    }

    // check if a word exists in the dictionary.
    private static boolean wordExistsInDictionary(String word, String[] dictionary) {

        //loop that iterates over each word in the dictionary array and assign it to the variable dictword.
        for (String dictWord : dictionary) {

            //if  word is found in the dictionary:
            if (word.equals(dictWord)) {
                return true;
            }
        }
        return false;
    }

    // signify a sequential order of a number from 1-6.
    private static String getOrdinalSuffix(int number) {

        switch (number % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }

    // compare the keyword and the guess and print the hints or the results.
    private static void compareWords(String keyword, String guess) {
        System.out.println("");

        // a loop that runs 5 times for each Character.
        for (int i = 0; i < 5; i++) {
            char c = guess.charAt(i);

            //checks if the character`c is not found in the keyword.
            if (keyword.indexOf(c) == -1) {
                System.out.println((i + 1)+". letter does not exist.");

            //checks if the character is located in the same position of a character in the keyword
            } else if (keyword.charAt(i) == c) {
                System.out.println((i + 1)+". letter exists and located in right position.");

            } else {
                System.out.println((i + 1)+". letter exists but located in wrong position.");
            }
        }
    }
}