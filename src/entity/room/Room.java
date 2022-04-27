package entity.room;

public abstract class Room{
    protected static Integer count = 0;
    protected Integer roomNumber;
    //    protected Integer floor;
    protected RoomStatus roomStatus;
    protected RoomType roomType;

    public Room(RoomType roomType) {
        count++;
        this.roomNumber = count;
        this.roomStatus = RoomStatus.AVAILABLE;
        this.roomType = roomType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public abstract Double getPrice();
    public abstract  String getType();

    @Override
    public String toString() {
        return "\nRoom{" +
                "roomNumber=" + roomNumber +
                ", roomStatus=" + roomStatus +
                ", roomType=" + roomType +
                "}";
    }
}

