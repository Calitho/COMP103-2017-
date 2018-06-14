// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 5
 * Name:
 * Username:
 * ID:
 */


import ecs100.*;

import java.util.*;

/**
 * Prints out all permuations of a string
 * The static method permute constructs all the permutations.
 * The main method gets the string, calls recPermute, and prints the result.
 */

public class Permutations {
    private int i = 0;


    /**
     * @return a List of all the permutations of a String.
     */
    public static List<String> recPermute(String string) {
        List<String> listOfPermutations = new ArrayList<>();
        addTolist("", string, listOfPermutations); //calls the addtolist method
        return listOfPermutations; //Returns the List
    }

    public static void addTolist(String before, String string, List<String> list) {
        if (string.isEmpty()) { //if the string is empty, it adds the before string
            list.add(before);
        } else {
            //runs a for loop then recursively calls the addtolist method changing the arguments.
            for (int i = 0; i < string.length(); i++) {
                addTolist(before + string.charAt(i), string.substring(0, i) + string.substring(i + 1), list);
            }
        }
    }

    // Main
    public static void main(String[] arguments) {
        UI.initialise();
        UI.setWindowSize(500, 400);
        UI.setDivider(1);
        String string = "";

        while (!string.equals("#")) {
            string = UI.askString("Enter string to permute - # to exit: ");
            if (string.length() < 11) {

                List<String> permutations = recPermute(string);

                for (String p : permutations)
                    UI.println(p);
                UI.println("---------");
            } else UI.println("Give a smaller string.");
        }
        UI.quit();
    }
}


