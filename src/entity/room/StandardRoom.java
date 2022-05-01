package entity.room;

public class StandardRoom extends Room {
    private final static Double priceStandardRoom = 15d;

    public StandardRoom(Integer roomNumber, RoomType roomType) {
        super(roomNumber, roomType);
    }

    @Override
    public Double getPrice() {
        if (this.roomType == RoomType.SINGLE){
            return priceStandardRoom + RoomType.SINGLE.getPrice();
        }
        return priceStandardRoom + RoomType.DOUBLE.getPrice();
    }

    @Override
    public String getType() {
        return "Standard - " + this.roomType;
    }

    @Override
    public String toString() {
        return "StandardRoom{" +
                "roomNumber=" + roomNumber +
                ", roomStatus=" + roomStatus +
                ", roomType=" + roomType +
                "}\n";
    }

}
