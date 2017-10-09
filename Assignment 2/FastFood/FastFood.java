// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 2
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.util.*;

/**
 * The FastFood game involves customers who generate orders, and the player
 * who has to fulfill the orders by assembling the right collection of food items.
 * The goal of the game is to make as much money as possible before
 * the player gets too far behind the customers and is forced to give up.
 * <p>
 * The game presents the player with a queue of orders in a fast food outlet.
 * The player has to fullfill the customer orders by adding the correct items to
 * the order at the head of the queue.
 * When the order is ready, the player can deliver the order, which will
 * take it off the queue, and will add the price of the order to the balance.
 * Whenever the player adds an item to the order that doesn't belong in the
 * order, the price of the item is subtracted from the balance.
 * The player can practice by generating orders using the Practice button.
 * Once the game is started, the orders are generated automatically.
 *
 *
 *
 *
 * What ive changed: Alot, ive added the map to map items to prices. and altered the ORder class to run off two lists to extend it
 * from the intial core. added items: pizza and Fizzy to extend the game. the game generates any length of list with the possiblity for duplicates
 * 3 levels of difficulty have been implemented to test diffrent types of people added key bindings to make things easier (Not that it really does. poor choice
 * of keys, but the idea is there.
 * all methods are linked to the Order class
 */

public class FastFood {

    private Queue<Order> orders;
    private double balance;
    public static Map<String, Double> prices;


    public FastFood() {

        prices = new HashMap<String, Double>();

        prices.put("Fish", 2.50);
        prices.put("Chips", 1.50);
        prices.put("Burger", 5.00);
        prices.put("Pizza", 3.50);
        prices.put("Fizzy", 2.00);
        orders = new ArrayDeque<Order>();
        UI.setKeyListener(this::doKey);
        UI.addButton("Practice Order", () -> {
            generateOrder();
            drawOrders();
        });
        UI.addButton("Add Fish (Press F)", () -> {   //Buttons added to the interface ro complete the game, but also have keybindings.
            addItem("Fish");
            drawOrders();
        });
        UI.addButton("Add Chips (Press C)", () -> {
            addItem("Chips");
            drawOrders();
        });
        UI.addButton("Add Burger (Press B)", () -> {
            addItem("Burger");
            drawOrders();
        });
        UI.addButton("Add Pizza (Press P)", () -> {
            addItem("Pizza");
            drawOrders();
        });
        UI.addButton("Add Fizzy (Press F)", () -> {
            addItem("Fizzy");
            drawOrders();
        });
        UI.addButton("Deliver Order (Press D)", () -> {
            deliverOrder();
            drawOrders();
        });
        UI.addButton("Start Game-Easy(Press E)", () -> {
            startGame(7500);
            drawOrders();
        });
        UI.addButton("Start Game-Normal(Press N)", () -> {
            startGame(5000);
            drawOrders();
        });
        UI.addButton("Start Game-Normal(Press H)", () -> {
            startGame(2500);
            drawOrders();
        });
        UI.addButton("Quit", UI::quit);
        drawOrders();
        this.run();

    }


    /**
     * Creates a new order and puts it on the queue to be processed
     */
    public void generateOrder() {
        orders.offer(new Order());
    }

    /**
     * As long as there is an order in the queue, adds the specified
     * item to the order at the head of the queue,
     * If adding the item fails (i.e., it isn't one of the items
     * that are wanted by the order) then the price
     * of the item is deducted from the current balance.
     *
     * Checks if the orders queue is empty, then peeks to check value, and fill the vlaue, then the if stament, means that if an item attempts
     * to be added that is not supposed to be you lose that value of money from the current balence. if the item needs to be added it is ignored
     */
    public void addItem(String item) {
        if (!orders.isEmpty()) {
            Order o = orders.peek();
            boolean order = o.addItemToOrder(item);
            if (!(order)) {
                balance -= prices.get(item);
            }
        }
    }

    /**
     * As long as there is an order at the front of the queue and it is ready,
     * take the first order off the queue, compute the price of the order,
     * and update the total balance by adding the order price.
     * If there is not a ready order on the queue, it prints a warning message.
     *
     *
     * again sets up a order variable. then checks if o is not null. in essence code could be shortened, checks if is ready then added a value to the alence
     * if not it states the order is not ready.
     */

    public void deliverOrder() {
        Order o = orders.peek();
        if (o != null) {
            if (o.isReady()) {
                balance += o.getPrice();
                orders.poll();
            }
            else {UI.println("Order not ready!!");}
        }
    }

    /**
     * Draws the queue of orders on the Graphics pane.
     * Also draws the current balance in the top left corner
     *
     * draws the orders on the graphics pane and draws the current balance on the graphics pane
     */
    public void drawOrders() {
        UI.clearGraphics();
        UI.drawString("$" + Double.toString(balance), 20, 20);
        int y = 5;
        for (Order order : orders) {
            order.draw(y += 40);
        }
    }


    // In the game version, the orders must be automatically generated.
    // The methods below will reset the queue and the current balance,
    // and will then set the gameRunning field to true. This will make
    // the run method start generating orders.
    // The run method is called from the main method, and therefore is in the main
    // thread, which executes concurrently with all the GUI buttons.
    // run  does nothing until the gameRunning field is set to be true
    // Once the gameRunning field is true, then it will generate orders automatically,
    // every timeBetweenOrders milliseconds. It will also makde the games speed up
    // gradually, by steadily reducing the timeBetweenOrders.
    // You do not need to write these methods code.

    private boolean gameRunning = false;
    private long timeBetweenOrders = 5000;

    private void startGame(int s) {
        UI.clearGraphics();
        UI.clearText();
        orders.clear();
        balance = 0;
        timeBetweenOrders = s;
        gameRunning = true;
    }

    public void run() {
        long timeBetweenSpeedups = 2000;
        long timeNextOrder = 0;
        long timeNextSpeedup = 0;

        while (true) { // forever...
            UI.sleep(100); // Wait at least 100 milliseconds between actions.
            long now = System.currentTimeMillis();

            if (!gameRunning)
                continue;  // if gameRunning is false, then don't generate orders

            // add another order, if the time has come
            if (now >= timeNextOrder) {
                timeNextOrder = now + timeBetweenOrders;
                generateOrder();
                drawOrders();
            }

            // speed up order generation, if the time has come
            if (now >= timeNextSpeedup) {
                if (timeBetweenOrders > 200)    // as long as maximum speed has not been reached...
                    timeBetweenOrders -= 100;  // reduce interval

                timeNextSpeedup = now + timeBetweenSpeedups;
            }

            if (orders.size() > 20) {
                UI.println("Oh no! You have too many orders waiting! Game over...");
                orders.clear();
                gameRunning = false;
                break;
            }
        }
    }

    public void doKey(String key) {
        if (key.equals("f")) {
            addItem("Fish");
            drawOrders();
        }
        if (key.equals("c")) {
            addItem("Chips");
            drawOrders();
        }
        if (key.equals("b")) {
            addItem("Burger");
            drawOrders();
        }
        if (key.equals("d")) {
            deliverOrder();
            drawOrders();
        }
        if (key.equals("p")) {
            addItem("Pizza");
            drawOrders();
        }
        if (key.equals("f")) {
            addItem("Fizzy");
            drawOrders();
        }
        if (key.equals("n")) {
            startGame(5000);
            drawOrders();
        }
        if (key.equals("h")) {
            startGame(2500);
            drawOrders();
        }
        if (key.equals("e")) {
            startGame(7500);
            drawOrders();
        }
    }

    public static void main(String args[]) {
        FastFood ff = new FastFood();
    }
}
