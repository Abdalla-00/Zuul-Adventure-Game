
/**
 * Class beamer extends item - a special item that can be charged & fired.
 * Transports player from current room to charged room
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * An "item" that represents one variuos objects in the scenery of the game
 * that are found in different rooms.Each room has its own unique items
 * that speak of the environment.
 * 
 * @author Abdalla Omar - student id: 101261926
 * @version Assignment 2
 * @see Item
 */

public class Beamer extends Item {

    private Room chargedRoom;

    /**
     * Constructor to initiate new Beamer, if user doesn't enter any parameter
     * defualt constructor will apply. just variable won't be initialized
     * 
     * Create a Beamer (item) given some description and weight.
     * 
     * @param description (The Beaner's description)
     * @param wght        weight in kg.
     * @param nm          name of Beamer
     * @see item
     */
    public Beamer(String desc, double wght, String nm) {
        super(desc, wght, nm);
        chargedRoom = null;

    }

    /**
     * Checks whether beamer is charged (getter)
     * 
     * @param None
     * @return boolean whether or not beamer is charged
     */
    public boolean isCharged() {
        return chargedRoom != null;
    }

    /**
     * Method to charger beamer (setter)
     * 
     * @param room we want to charge/store
     * @return None
     */
    public void setBeamer(Room room) {
        chargedRoom = room;
    }

    /**
     * Method to fire beamer (getter), null if it's not charged
     * 
     * @param None
     * @return room of where we are going/ charged room
     */
    public Room fireBeamer() {
        if (isCharged()) {
            Room temp = chargedRoom;
            chargedRoom = null;
            return temp;
        }
        return null;
    }
}
