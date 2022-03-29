package entity.room;

import java.util.Comparator;

public class RoomTypeComparator implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        return o1.getType().compareTo(o2.getType());
        //getType returneaza String => Premium - SINGLE/Premium - DOUBLE/Standard - SINGLE/Standard - DOUBLE
    }
}

