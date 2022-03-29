package entity.room;

public class StandardRoom extends Room {
    private final static Double priceStandardRoom = 15d;

    public StandardRoom(RoomType roomType) {
        super(roomType);
    }

    @Override
    public Double getPrice() {
        if (this.roomType == RoomType.SINGLE){
            return priceStandardRoom + PRICE_SINGLE_ROOM;
        }
        return priceStandardRoom + PRICE_DOUBLE_ROOM;
    }

    @Override
    public String getType() {
        return "Standard - " + this.roomType;
    }

    @Override
    public String toString() {
        return "\nStandardRoom{" +
                "roomNumber=" + roomNumber +
                ", roomStatus=" + roomStatus +
                ", roomType=" + roomType +
                '}';
    }

}
