package entity.review;

import java.time.LocalDate;
import java.util.Optional;

public class Review {
    public static Integer count = 0;
    private Integer id;
    private int stars;
    private int service;
    private int rooms;
    private int cleanliness;
    private int sleepQuality;
    private String description;
    private LocalDate date;

    public Review(int stars, int service, int rooms, int cleanliness, int sleepQuality, String description) {
        count++;
//        this.id = count;
        this.stars = stars;
        this.service = service;
        this.rooms = rooms;
        this.cleanliness = cleanliness;
        this.sleepQuality = sleepQuality;
        this.description = description;
        this.date = LocalDate.now();;
    }

    public Integer getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness) {
        this.cleanliness = cleanliness;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(int sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", stars=" + stars +
                ", service=" + service +
                ", rooms=" + rooms +
                ", cleanliness=" + cleanliness +
                ", sleepQuality=" + sleepQuality +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
