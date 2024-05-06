import java.util.Stack;
// import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Abdalla Omar 
 * @version Januray 25, 2024
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room prevRoom;
    private Stack<Room> roomHistory;
    private Item currentItem;
    private int pickUps;

    /**
     * Create the game, initialise its internal map & the room history
     */
    public Game() {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<Room>();
        currentItem = null;
        pickUps = 0;

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theatre, pub, lab, office;
        TransporterRoom transporterRoom;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        outside.addItem(new Item("fir tree", 106.4, "tree"));
        outside.addItem(new Item("fir tree", 70.4, "tree"));
        outside.addItem(new Beamer("shiny metalic beamer", 30, "beamer"));

        theatre = new Room("in a lecture theatre");
        theatre.addItem(new Item("gigantic screen", 50, "screen"));

        pub = new Room("in the campus pub");
        pub.addItem(new Item("cold drink", 1, "Pepsi"));
        pub.addItem(new Item("vanilla cookie", 0.3, "cookie"));

        lab = new Room("in a computing lab");
        lab.addItem(new Item("cool computer", 20, "desktop"));
        lab.addItem(new Item("fudge cookie", 0.2, "cookie"));

        office = new Room("in the computing admin office");
        office.addItem(new Item("giant desk", 80, "desk"));
        office.addItem(new Item("Chocolate chip cookie", 0.1, "cookie"));
        office.addItem(new Beamer("swift looking beamer", 10, "beamer"));

        transporterRoom = new TransporterRoom("in the magical transporter room");
        transporterRoom.addItem(new Beamer("blue beamer", 16, "beamer"));
        transporterRoom.addItem(new Item("huge strawberry cookie", 0.7, "cookie"));

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", transporterRoom);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside; // start game outside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription(currentItem));
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look(command);
        } else if (commandWord.equals("eat")) {
            eat(command);
        } else if (commandWord.equals("back")) {
            back(command);
        } else if (commandWord.equals("stackBack")) {
            stackBack(command);
        } else if (commandWord.equals("take")) {
            take(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("fire")) {
            fire(command);
        } else if (commandWord.equals("charge")) {
            charge(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        // current room is the Transporter room
        if (command.hasSecondWord() && currentRoom instanceof TransporterRoom) {
            prevRoom = currentRoom;
            roomHistory.push(currentRoom);
            currentRoom = ((TransporterRoom) currentRoom).getExit();
            System.out.println("Successully exited the Transporter Room!\n");
            System.out.println(currentRoom.getLongDescription(currentItem));
            return;

        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            prevRoom = currentRoom;
            roomHistory.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription(currentItem));
        }

    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    /**
     * "look" was entered.
     * checks to see if we entered a valid a valid command, if not
     * always respond by "look where?"
     * 
     * @param command The command to be processed "look"
     *                prints current description of room, including items found and
     *                exits
     */
    private void look(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Look where?");
            return;
        }

        System.out.println(currentRoom.getLongDescription(currentItem));
    }

    /**
     * "eat" was entered.
     * Prints that we've already ate
     * checks to see if we entered a valid a valid command, if not
     * always respond by "Eat where?"
     * 
     * @param command The command to be processed "eat"
     * @return None
     */
    private void eat(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Eat where?");
            return;
        }
        // eating none
        else if (currentItem == null) {
            System.out.println("You must pickup a cookie first and in order to eat!");
            return;
        }

        // eating non-cookie item
        else if (currentItem.getName().equals("cookie") == false) {
            System.out.println("Can only eat a cookie!");
            System.out.println("You've also alraedy eaten and are no longer hungry.");
            return;
        }

        // eating a cookie
        else if (currentItem.getName().equals("cookie")) {

            currentRoom.removeItem("cookie");
            currentItem = null;
            pickUps = 5;
            System.out.println("You've eaten the cookie and are no longer hungry!");
            return;
        }
    }

    /**
     * "back" was entered.
     * if there is no pres room, then prevRoom var isn't initialized and is null
     * that's why we can tell if we just started the game
     * 
     * Takes you back to the previous room you're in and only the most recent prev
     * room.
     * If true prints out the current room, its items, the exits and that we've
     * gotten back.
     * 
     * @param command The command to be processed "back"
     * @return None
     * 
     */
    private void back(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Back where?");
            return;
        }

        if (prevRoom == null) {
            System.out.println("Can't go back, you just started the game");
            return;
        }
        Room temp = prevRoom;
        prevRoom = currentRoom;
        currentRoom = temp;
        roomHistory.push(temp); // updating stackback

        System.out.println("You have gone back");
        System.out.println(currentRoom.getLongDescription(currentItem));

    }

    /**
     * "stackBack" was entered.
     * if there is no prev room, then prevRoom var isn't initialized and is null
     * that's why we can tell if we just started the game.
     * Takes you back to the previous room you're in. keeps historty from whenever
     * user started their game.
     * If command successful prints out the current room, its items, the exits and
     * that we've gotten back else tells user back where.
     * 
     * @param command The command to be processed "stackBack"
     * @return None
     */
    private void stackBack(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Back where?");
            return;
        }

        if (roomHistory.isEmpty()) {
            System.out.println("Can't go back, you are where you started");
            return;
        }

        prevRoom = currentRoom; // update back
        currentRoom = roomHistory.pop();

        System.out.println("You have gone back");
        System.out.println(currentRoom.getLongDescription(currentItem));
    }

    /**
     * "Take" was entered.
     * If there's a second word (item) exist then we add it and remove it from room,
     * otherwise we let player know item doesn't exist in room.
     * If already holding an item, let player know they can only hold one item at a
     * time.
     * 
     * @param command The command to be processed
     * @return void
     */
    private void take(Command command) {

        // one word command
        if (command.hasSecondWord() == false) {
            System.out.println("Take what?");
            return;
        }

        // already holding item
        else if (currentItem != null) {
            System.out.println("Already holding an item, can only hold an item at a time!");
            return;
        }

        // taking a non-cookie with no pickups
        else if ("cookie".equals(command.getSecondWord()) == false && pickUps < 1) {
            System.out.println("Must take and eat cookie before picking up items");
            return;
        }

        // taking a non-cookie while having pickups
        else if ("cookie".equals(command.getSecondWord()) == false && pickUps > 0) {

            // getting item
            Item temp = currentRoom.removeItem(command.getSecondWord());

            // item exists
            if (temp != null) {
                currentItem = temp;
                pickUps--;
                System.out.println("You picked up: " + temp.getName());
                return;
            }

            // item does NOT exist
            else if (temp == null) {
                System.out.println(command.getSecondWord() + " is not an item in the current room!");
                return;
            }
        }

        // taking a cookie, don't need pickups
        else if ("cookie".equals(command.getSecondWord())) {

            // getting cookie
            Item temp = currentRoom.removeItem(command.getSecondWord());

            // cookie in room
            if (temp != null && currentItem == null) {
                currentItem = temp;
                pickUps--;
                System.out.println("You picked up: " + temp.getItem());
                return;
            }

            // no cookie in room
            else if (temp == null) {
                System.out.println("There is no cookie in this room to pickup!");
                return;
            }
        }
    }

    /**
     * "drop" was entered.
     * Removes current item being held, adds it to current room.
     * Let's player know the dropped their item.
     * If not holding an item, prints not holding anything.
     * 
     * @param command The command to be processed "drop"
     * @return void
     */
    private void drop(Command command) {

        // drop __item__
        if (command.hasSecondWord()) {
            System.out.println("Command to drop is a \"drop\" a single word!");
            return;
        }

        // dropping nothing
        if (currentItem == null)
            System.out.println("You have nothing to drop");

        // dropping currentItem
        else {
            System.out.println("You have dropped " + currentItem.getName());

            // update room with item
            currentRoom.addItem(currentItem);

            // player not holding item anymore
            currentItem = null;
        }
    }

    /**
     * "fire" was entered. Lookin to return back to charged room.
     * 
     * @param command The command to be processed "fire"
     * @return void
     */
    private void fire(Command command) {

        // current item is a Beamer
        if (currentItem instanceof Beamer) {

            // Beamer is charged
            if (((Beamer) currentItem).isCharged()) {

                // fire
                prevRoom = currentRoom; // update back
                roomHistory.push(currentRoom); // update stackback
                currentRoom = ((Beamer) currentItem).fireBeamer();
                System.out.println("Beamer has been fired");
                System.out.println(currentRoom.getLongDescription(currentItem));
                return;

            }
            // Beamer not charged
            else if (((Beamer) currentItem).isCharged() == false) {
                System.out.println("Must charge beamer before you can fire it!");
                return;
            }

        }
        // currentItem is NOT a Beamer
        System.out.println("Must pickup beamer and charge it in order to fire.");
    }

    /**
     * "charge" was entered.
     * Copy's current room, stores it to later transport to it.
     * 
     * @param command The command to be processed "charge"
     * @return void
     */
    private void charge(Command command) {

        // current item is a Beamer
        if (currentItem instanceof Beamer) {

            // Beamer is charged
            if (((Beamer) currentItem).isCharged()) {
                System.out.println("Beamer already charged.");
                return; // check if we can charge the beamer twice in a row
            }

            // Beamer not charged
            else if (((Beamer) currentItem).isCharged() == false) {

                // charge beamer
                ((Beamer) currentItem).setBeamer(currentRoom);
                System.out.println("Beamer is now charged!");
                return;
            }
        }
        // item not a beamer
        System.out.println("Must pickup beamer to charge it.");

    }

}
