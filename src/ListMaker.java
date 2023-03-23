import java.util.ArrayList;
import java.util.Scanner;

public class ListMaker {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); // input scanner
        ArrayList<String> list = new ArrayList<>();
        String menuChoice; // stores user input for the options menu

        boolean quit = false;
        SafeInput.prettyHeader("LIST MAKER"); // pretty header

        // options menu
        do {

            // checks if the list is empty (meaning this runs every time the program first starts)
            if (list.size() == 0) {
                System.out.print("\nYour list is empty!"); // print that the list is empty

                do {
                    // prompts them to add something
                    if (SafeInput.getYNConfirm(in, "Would you like to add something?")) {
                        ListMaker.add(list); // if they'd like to add, calls add method
                    } else {
                        quit(); // if they wouldn't like to add, calls quit method
                    }
                } while (list.size() == 0); // loops until the user either adds something or quits

            } else { // if the list is not empty, runs the regular main menu code

                // print main menu
                System.out.println("""
                    \nOptions:
                    A - Add an item to the list
                    D - Delete an item from the list
                    P - Print (i.e. display) the list
                    Q - Quit the program""");

                // gets choice using regex format then converts it to lowercase
                menuChoice = SafeInput.getRegExString(in, "Enter choice", "[AaDdPpQq]").toLowerCase();

                // since the input has all been simplified to lowercase, it just goes through the lowercase letters
                if (menuChoice.equals("a")) {
                    // if it's a, shows list then calls the add method
                    ListMaker.displayNumbered(list);
                    ListMaker.add(list);

                } else if (menuChoice.equals("d")) {
                    // if it's d, shows list then calls the delete method
                    ListMaker.displayNumbered(list);
                    ListMaker.delete(list);

                } else if (menuChoice.equals("p")) {
                    ListMaker.displayNumbered(list); // if it's p, calls the display method

                } else {

                    quit = true; // sets quit to true to end loop just in case the quit method fails
                    ListMaker.quit(); // only other input is q; calls quit method
                }
            }
        } while (!quit);
    }

    /**
     * Displays an ArrayList of Strings as a numbered list
     *
     * @param list ArrayList to display
     */
    private static void displayNumbered(ArrayList<String> list) {

        if (list.size() == 0) { // if the list is empty, print this:
            System.out.println("\nThis list is empty!");

        } else { // if list not empty

            // finding the longest item in the list (for the purposes of the border; not really necessary for the lab
            int listWidth = 0;
            for (int i = 0; i < list.size(); i++) { // iterates over list
                // if that String is longer than listWidth, listWidth becomes that length
                if (list.get(i).length() > listWidth) {
                    listWidth = list.get(i).length();
                }
            }

            // print top border
            System.out.print("\n══ LIST ");
            for (int i = 0; i < listWidth; i++) {
                System.out.print("═");
            }
            System.out.println("╗");

            // loops over length of list, printing the number and then the item at that index
            for (int i = 1; i <= list.size(); i++) {
                // since i = 1 at start, indices are all i - 1
                System.out.println(i + ".  " + list.get(i - 1));
            }

            // bottom of border
            for (int i = 0; i < (listWidth + 8); i++) {
                System.out.print("═");
            }
            System.out.println("╝");
        }
    }

    /**
     * Is called when the user chooses Add from the main menu.
     * Gets item to add, then adds it to the list.
     *
     * @param list list to add item to
     */
    private static void add(ArrayList<String> list) {
        Scanner in = new Scanner(System.in); // input scanner

        // gets String from user for the name of the list item to add
        System.out.println("\nWhat would you like to add to the list?: ");
        list.add(in.nextLine()); // adds it to the list
        ListMaker.displayNumbered(list); // display the updated list
    }

    /**
     * Menu for deleting items from list; collects input and carries out deletion
     *
     * @param list ArrayList of Strings to delete from
     */
    private static void delete(ArrayList<String> list) {
        Scanner in = new Scanner(System.in); // input scanner

        if (list.size() == 0) { // if the list is empty, goes back to main menu
            return;
        } else if (list.size() == 1) { // if there's only 1 item, prompts for confirmation before deleting
            if (SafeInput.getYNConfirm(in, "Only one item in list. Delete?")) {
                list.remove(0);
            }
            return;
        }
        // if list has 2 or more items:
        // gets input from user for which item to delete, then subtracts one to get the actual index
        int item = SafeInput.getRangedInt(in, "Select an item to delete, or enter 0 to cancel", 0, list.size()) - 1;

        if (item == -1) { // if they'd like to cancel
            return;

        } else { // otherwise:
            // asks for confirmation
            if (SafeInput.getYNConfirm(in, "Are you sure you'd like to delete list item " + (item + 1) + " (\"" + list.get(item) + "\")?")) {
                list.remove(item); // deletes the item at that index
                System.out.println("Item deleted.");

            } else { // if they said no, ask to delete a different item.
                System.out.println("Deletion canceled.");
            }
        }
    }

    /**
     * Is called when user chooses Quit from the main menu; asks them to confirm that they'd like to quit
     * If yes, terminates
     *
     */
    private static void quit() {
        Scanner in = new Scanner(System.in);
        // ask for confirmation to quit
        // if they say no, it just goes back to main menu and the quit() method doesn't do anything
        if (SafeInput.getYNConfirm(in, "Are you sure you'd like to quit?")) {
            System.out.println("See you next time!");
            System.exit(0);
        }
    }
}
