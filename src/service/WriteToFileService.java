package service;

import entity.review.Review;
import entity.room.Room;
import entity.user.Customer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class WriteToFileService {
    public static WriteToFileService writeToFileService;

    private WriteToFileService() {}

    public static WriteToFileService getWriteToFileService() {
        if (writeToFileService == null)
            writeToFileService = new WriteToFileService();
        return writeToFileService;
    }

    public void writeUser(Customer customer) {
        try (FileWriter fileWriter = new FileWriter("src/csv/Users.csv", true)) {

            //FirstName,LastName,UserDocument,Address,Telephone,Username,Password,Email
            fileWriter.append("\n")
                    .append(customer.getFirstName())
                    .append(",")
                    .append(customer.getLastName())
                    .append(",")
                    .append(customer.getUserDocument().toString())
                    .append(",")
                    .append(customer.getAddress())
                    .append(",")
                    .append(customer.getTelephone())
                    .append(",")
                    .append(customer.getUsername())
                    .append(",")
                    .append(customer.getPassword())
                    .append(",")
                    .append(customer.getEmail());
            fileWriter.flush();

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("\n\tException: " + e.getMessage());
        }
    }

    public void writeReview(Review review) {
        try (FileWriter fileWriter = new FileWriter("src/csv/Reviews.csv", true)) {

            //Stars,Service,Rooms,Cleanliness,SleepQuality,Description
            fileWriter.append("\n")
                    .append(String.valueOf(review.getStars()))
                    .append(",")
                    .append(String.valueOf(review.getService()))
                    .append(",")
                    .append(String.valueOf(review.getRooms()))
                    .append(",")
                    .append(String.valueOf(review.getCleanliness()))
                    .append(",")
                    .append(String.valueOf(review.getSleepQuality()))
                    .append(",")
                    .append(review.getDescription());
            fileWriter.flush();

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("\n\tException: " + e.getMessage());
        }
    }

    public <T extends Room> void writeRoom(T room, String fisier) {
        try (FileWriter fileWriter = new FileWriter(fisier, true)) {

            Method mRoomNumber = room.getClass().getMethod("getRoomNumber");
            Method mRoomType = room.getClass().getMethod("getRoomType");

            //RoomNumber,RoomType
            fileWriter.append("\n")
                    .append(String.valueOf(mRoomNumber.invoke(room)))
                    .append(",")
                    .append(String.valueOf(mRoomType.invoke(room)));
            fileWriter.flush();

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("\n\tException: " + e.getMessage());
        }
    }

}
