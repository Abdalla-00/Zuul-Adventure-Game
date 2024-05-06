import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. For each existing exit, the room
 * stores a reference to the neighboring room.
 * 
 * @author Abdalla Omar 
 * @version Januray 25, 2024
 */

public class Room {
    private String description;
    private HashMap<String, Room> exits; // stores exits of this room.
    private ArrayList<Item> items;
    protected static ArrayList<Room> rooms = new ArrayList<Room>(); // array containing all current rooms, excluding the
                                                                    // transporter room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();

        // ensuring we don't add the transporter room
        if (!(this instanceof TransporterRoom))
            rooms.add(this);
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     * You are in the kitchen.
     * Exits: north west
     * 
     * A hot red pan that weighs 3kg.
     * A dozen pair of eggs that weigh 8kg.
     * 
     * @param current item that is being held
     * @return A long description of this room including all items in them
     */
    public String getLongDescription(Item currentItem) {

        String listItems = "";

        for (Item item : items) {
            listItems = listItems + "\t" + item.getItem();
        }
        String load = "You are " + description + ".\n" + getExitString() + "\n\nItems:\n" + listItems;

        // Not carrying an item
        if (currentItem == null)
            load += "\nYou are not carrying anything";

        // carrying an item
        else if (currentItem != null)
            load += "\nYou carrying:\n" + "\t" + currentItem.getItem();

        return load;

    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Method to add item into room (array of items)
     * concatenates the item to the list items available in that specified
     * room
     * 
     * @param item that's already initialized with fields, desc & weight
     * @return None
     * 
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Checks if item with specified name exists, if so, it pops it.
     * 
     * @param name of item we are looking to delete
     * @return item if found, null otherwise.
     * 
     */

    public Item removeItem(String itemName) {

        Iterator<Item> iter = items.iterator();

        // iterating
        while (iter.hasNext()) {

            Item temp = iter.next();

            // checking if item with name exists
            if (itemName.equals(temp.getName())) {
                iter.remove();
                return temp;
            }

        }
        return null;
    }

    /**
     * Retrieves the array of all rooms, getter.
     * 
     * @return array list of rooms.
     */
    public static ArrayList<Room> getRooms() {
        return rooms;
    }
}
