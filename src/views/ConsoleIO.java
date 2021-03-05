package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIO {

    /**
     * Generates a prompt that allows the user to enter any response and returns the String.
     * This method does not accept null, empty, or whitespace-only inputs.
     * This method NEVER accepts null as a valid input.
     * @param prompt - the prompt to be displayed to the user.
     * @param allowBlank - determines if blank or empty strings are valid input
     * @return the input from the user as a String
     */
    public static String promptForString(String prompt, boolean allowBlank) {
        if(prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("The prompt cannot be null, empty, or just white space. prompt = " + prompt);

        }

        String input = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean inputIsInvalid = true;

        do {
            System.out.println(prompt);

            try{
                input = br.readLine();
                inputIsInvalid = input == null || (!allowBlank && input.isBlank());

                if (inputIsInvalid) {
                    System.out.println("Your input was invalid. Please, try again.");
                }

            } catch (IOException ioe) {
                System.out.println("There was a problem and your input was not received. Please try again.");
            }
        } while (inputIsInvalid);

        return input;
    }

    /**
     * Generates a prompt that expects a numeric input representing an int value.
     * This method loops until valid input is given.
     * @param prompt - the prompt to be displayed to the user
     * @param min - the inclusive minimum boundary
     * @param max - the inclusive maximum boundary
     * @return the int value
     */
    public static int promptForInt(String prompt, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("The min cannot exceed the max. min= " + min + "; max= " + max);
        }

        int userNum = -1;
        boolean numIsInvalid = true;

        do {
            String input = promptForString(prompt, false);
            try {
                userNum = Integer.parseInt(input);
                numIsInvalid = userNum < min || userNum > max;


            } catch (NumberFormatException nfe) {
                //  No need to do anything here
            }

            if (numIsInvalid) {
                System.out.println("You must enter a whole number between" + min + " and " + max + ". Please, try again");
            }
        } while (numIsInvalid);

        return userNum;
    }

    /**
     * Generates a prompt that expects the user to enter one of two responses that will equate
     * to a boolean value. The trueString represents the case insensitive response that will equate to true.
     * The falseString acts similarly, but for a false boolean value.
     * 		Example: Assume this method is called with a trueString argument of "yes" and a falseString argument of
     * 		"no". If the enters "YES", the method returns true. If the user enters "no", the method returns false.
     * 		All other inputs are considered invalid, the user will be informed, and the prompt will repeat.
     * @param prompt - the prompt to be displayed to the user
     * @param trueString - the case insensitive value that will evaluate to true
     * @param falseString - the case insensitive value that will evaluate to false
     * @return the boolean value
     */
    public static boolean promptForBoolean(String prompt, String trueString, String falseString){
        if (trueString == null || falseString == null || trueString.equalsIgnoreCase(falseString)) {
            throw new IllegalArgumentException("The trueString and falseString cannot be null or equal: trueString= " +
                    trueString + "; falseString= " + falseString);
        }

        boolean isTrue = true;
        boolean isInvalid = true;

        do {
            String input = promptForString(prompt, true);
            isInvalid = !input.equalsIgnoreCase(trueString) && !input.equalsIgnoreCase(falseString);
            if (isInvalid) {
                System.out.println("You must enter '" + trueString + "' or '" + falseString + "'. Please try again.");
            } else {
                isTrue = input.equalsIgnoreCase(trueString);
            }
        } while (isInvalid);


        return isTrue;
    }

    /**
     * Generates a console-based menu using the Strings in options as the menu items.
     * Reserves the number 0 for the "quit" option when withQuit is true.
     * @param options - Strings representing the menu options
     * @param withQuit - adds option 0 for "quit" when true
     * @return the int of the selection made by the user
     */
    public static int promptForMenuSelection(String[] options, boolean withQuit){
        if ((options == null || options.length == 0) && !withQuit) {
            throw new IllegalArgumentException("There must be at least one option to choose.");
        }

        if (options == null) {
            options = new String[0];
        }

        int min = withQuit ? 0 : 1;
        int max = options.length;

        StringBuilder sb = new StringBuilder("Please, choose one of the following:\n\n");

        for (int i = 0; i < max; i++) {
            sb.append(i + 1).append(") ").append(options[i]).append('\n');
        }

        if (withQuit) {
            if(max > 0) {
                sb.append('\n');
            }
            sb.append("0) Exit\n");
        }

        sb.append("\nEnter the number of your choice");
        String menu = sb.toString();

        return promptForInt(menu, min, max);
    }

}