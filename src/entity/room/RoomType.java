package entity.room;

public enum RoomType {
    SINGLE(30d), DOUBLE(50d);

    private final double price;

    private RoomType(double x){
        price = x;
    }

    public double getPrice() {
        return price;
    }
}
