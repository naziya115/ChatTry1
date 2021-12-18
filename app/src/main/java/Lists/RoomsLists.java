package Lists;


import java.util.ArrayList;

import Models.Room;


public class RoomsLists {
    private static final ArrayList<Room> rooms = new ArrayList<>();

    public static void init() {
        rooms.clear();

    }

    private RoomsLists() {
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }
    public static void addRoom(Room room) {
        rooms.add(room);
    }

}
