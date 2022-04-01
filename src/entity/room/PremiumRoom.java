package entity.room;

public class PremiumRoom extends Room{
    private final static Double pricePremiumRoom = 15d;

    public PremiumRoom(RoomType roomType) {
        super(roomType);
    }

    @Override
    public Double getPrice() {
        if (this.roomType == RoomType.SINGLE){
            return pricePremiumRoom + RoomType.SINGLE.getPrice();
        }
        return pricePremiumRoom + RoomType.DOUBLE.getPrice();
    }

    @Override
    public String getType() {
        return "Premium - " + this.roomType;
    }

    @Override
    public String toString() {
        return "\nPremiumRoom{" +
                "roomNumber=" + roomNumber +
                ", roomStatus=" + roomStatus +
                ", roomType=" + roomType +
                '}';
    }

}

