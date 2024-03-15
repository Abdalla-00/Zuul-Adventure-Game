
/**
 * Class item - item(s) in any room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * An "item" represents one variuos objects in the scenery of the game
 * that are found in different rooms.Each room has its own unique items
 * that speak of the environment.
 * 
 * @author Abdalla Omar - student id: 101261926
 * @version Assignment 2
 */

public class Item {
    private String description;
    private double weight;
    private String name;

    /**
     * Constructor to initiate new item, if user doesn't enter any parameter
     * defualt constructor will apply. just variable won't be initialized
     * 
     * Create an item given some description and weight.
     * 
     * @param description (The room's description)
     * @param wght        weight in kg.
     * @param nm          name of item
     */
    public Item(String desc, double wght, String nm) {
        description = desc;
        weight = wght;
        name = nm;

    }

    /**
     * @param None
     * @return (getter) returns a string concatenation of an item
     *         ex. tree: A fir tree that weighs 40kg.
     */
    public String getItem() {

        if (description == "")
            return "No items in this room.";
        return name + ": A " + description + " that weighs " + weight + "kg.\n";
    }

    /**
     * @param None
     * @return (getter) returns name of item
     */
    public String getName() {
        return name;
    }

}
