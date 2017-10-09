// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 2
 * Name:
 * Username:
 * ID:
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ecs100.UI;

/**
 * This class is provided as a bad example.
 * Don't do this at home!
 * <p>
 * This class has now been completely overhauled
 */
public class Order {

    /**
     * the items that are wanted for the order
     */
    private List<String> whatsOrdered = new ArrayList<>();
    /**
     * the items that have been added and are ready in the order
     */
    private List<String> whatsAdded = new ArrayList<>();

    /**
     * the items that have been added and are ready in the order
     * Resets the two lists to empty and is the deciding factors to decide which item f food is added to the lists
     */
    public Order() {
        whatsAdded.clear();
        whatsOrdered.clear();
        int Size = (int) (Math.random() * 5) + 1;

        for (int i = 0; i < Size; i++) {
            int choice = (int) (Math.random() * 5);
            if (choice == 0) {
                whatsOrdered.add("Fish");
            } else if (choice == 1) {
                whatsOrdered.add("Chips");
            } else if (choice == 2) {
                whatsOrdered.add("Burger");
            } else if (choice == 3) {
                whatsOrdered.add("Pizza");
            } else if (choice == 4) {
                whatsOrdered.add("Fizzy");
            }
        }
    }


    /**
     * The order is ready as long as every item that is
     * wanted is also ready.
     */
    public boolean isReady() {
        return whatsOrdered.isEmpty();
    }

    /**
     * If the item is wanted but not already in the order,
     * then put it in the order and return true, to say it was successful.
     * If the item not wanted, or is already in the order,
     * then return false to say it failed.
     */
    public boolean addItemToOrder(String item) {
        boolean added;
        if (whatsOrdered.contains(item)) {
            whatsAdded.add(whatsOrdered.remove(whatsOrdered.indexOf(item)));
            UI.println("add success, wanted or added to order.");
            added = true;
        } else {
            UI.println("add failed, not wanted or already in order.");
            added = false;
        }
        return added;
    }


    /**
     * Computes and returns the price of an order.
     * [CORE]: Uses constants: 2.50 for fish, 1.50 for chips, 5.00 for burger
     * to add up the prices of each item
     * [COMPLETION]: Uses a map of prices to look up prices
     * <p>
     * Has used the map to map prices to certain items
     */
    public double getPrice() {
        double price = 0;
        if (whatsAdded.contains("Fish")) price += FastFood.prices.get("Fish");
        if (whatsAdded.contains("Chips")) price += FastFood.prices.get("Chips");
        if (whatsAdded.contains("Burger")) price += FastFood.prices.get("Burger");
        if (whatsAdded.contains("Pizza")) price += FastFood.prices.get("Pizza");
        if (whatsAdded.contains("Fizzy")) price += FastFood.prices.get("Fizzy");
        return price;
    }

    /**
     * This draw method cycles through both arrays and as the items are swapped from one array to another
     * the images change to represent this change
     *
     */

    public void draw(int y) {
        int x = 0;
        for (int i = 0; i < whatsOrdered.size(); i++) {
            String food = whatsOrdered.get(i);
            UI.drawImage(food + "-grey.png", 40 * x, y);
            x++;
        }
        for (int j = 0; j < whatsAdded.size(); j++) {
            String food = whatsAdded.get(j);
            UI.drawImage(food + ".png", 40 * x, y);
            x++;
        }
    }
}
