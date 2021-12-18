package Models;

public class Room {
    //класс данных ;(
    private String roomName;

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public Room() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
