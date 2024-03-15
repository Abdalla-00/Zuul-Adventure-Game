import java.util.Random;

/**
 * Class Transported room inherits/extends Room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" resposible for transporting you into any other room, once you try to
 * leave it,not a specific exit, rather can have exit to all rooms.
 * 
 * @author Abdalla Omar - student id: 101261926
 * @version Assignment 2
 * @see Room
 */
public class TransporterRoom extends Room {

    private Random rand; // creates random objects

    /**
     * Creates a transporter room with the given "description.
     * It initially has no exits and transports player into a ramdon
     * arbitrary room
     * 
     * @param description The room's description.
     * @see Room
     */
    public TransporterRoom(String desc) {
        super(desc);
        rand = new Random();
    }

    /**
     * returns exit random room
     * 
     * @override
     * @return some random room we are going to exit to
     */
    public Room getExit() {
        int randomIndex = rand.nextInt(rooms.size()); // Generates an index between 0 and list.size()
        Room randomRoom = rooms.get(randomIndex);

        return randomRoom;
    }

}
