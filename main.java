
public class main {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}

/**
 * Questions
 * I changed my getLongDescription function to now have a parameter instead of
 * adjusting the room
 * I also am not using the static function for getRooms in Room.java, rather
 * made the ArrayList<Room rooms static and protected so it can be accessed by
 * the entire class.
 * 
 */