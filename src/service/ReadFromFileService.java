package service;

import entity.review.Review;
import entity.room.Room;
import entity.room.RoomType;
import entity.user.Customer;
import entity.user.UserDocument;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ReadFromFileService {
    public static ReadFromFileService readFromFileService;

    private ReadFromFileService() {}

    public static ReadFromFileService getReadFromFileService() {
        if (readFromFileService == null)
            readFromFileService = new ReadFromFileService();
        return readFromFileService;
    }

    public List<Customer> readUsers() {
        List<Customer> customers = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader("src/csv/Users.csv"));
            String line = buffer.readLine(); // linia cu numele coloanelor
            //FirstName,LastName,UserDocument,Address,Telephone,Username,Password,Email
            line = buffer.readLine();

            while (line != null) {
//                System.out.println("line:  " + line);
                String[] elem = line.split(",");
//                System.out.println(Arrays.stream(elem).toList());
                String firstName = elem[0];
                String lastName = elem[1];
                String userDocum = elem[2];
                UserDocument userDocument = UserDocument.ID;
                if (userDocum.equals("PASSPORT")){
                    userDocument = UserDocument.PASSPORT;
                }
                String address = elem[3];
                String telephone = elem[4];
                String username = elem[5];
                String password = elem[6];
                String email = elem[7];
                Customer customer = new Customer(firstName, lastName, userDocument, address, telephone, username, password, email);
                customers.add(customer);
                line = buffer.readLine();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Try again!");
        }

        return customers;
    }

    public List<Review> readReviews() {
        List<Review> reviews = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader("src/csv/Reviews.csv"));
            String line = buffer.readLine(); // linia cu numele coloanelor
            //Stars,Service,Rooms,Cleanliness,SleepQuality,Description
            line = buffer.readLine();

            while (line != null) {
                char ch='"';
                int stars, service, rooms, cleanliness, sleepQuality;
                String description ;

                if (line.contains(String.valueOf(ch))) {
                    String[] elem = line.split(String.valueOf(ch));
                    String[] elemInt = elem[0].split(",");
                    stars = Integer.parseInt(elemInt[0]);
                    service = Integer.parseInt(elemInt[1]);
                    rooms = Integer.parseInt(elemInt[2]);
                    cleanliness = Integer.parseInt(elemInt[3]);
                    sleepQuality = Integer.parseInt(elemInt[4]);
                    description = elem[1];
                } else {
                    String[] elem = line.split(",");
                    stars = Integer.parseInt(elem[0]);
                    service = Integer.parseInt(elem[1]);
                    rooms = Integer.parseInt(elem[2]);
                    cleanliness = Integer.parseInt(elem[3]);
                    sleepQuality = Integer.parseInt(elem[4]);
                    description = elem[5];
                }

                Review  review = new Review(stars, service, rooms, cleanliness, sleepQuality, description);
                reviews.add(review);
                line = buffer.readLine();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Try again!");
        }

        return reviews;
    }

   public <T extends Room> List<T> readRooms(String fisier, Class<T> cls) {
        List<T> rooms = new ArrayList<>();
        T room;
       Integer roomNumber;
       String roomTypeString;
       RoomType roomType = RoomType.SINGLE;

       try {
           BufferedReader buffer = new BufferedReader(new FileReader(fisier));
           String line = buffer.readLine(); // linia cu numele coloanelor
           //RoomNumber,RoomType
           line = buffer.readLine();

           while (line != null) {
               String[] elem = line.split(",");
               roomNumber = Integer.parseInt(elem[0]);
               roomTypeString = elem[1];
               if (roomTypeString.equals("DOUBLE")){
                   roomType = RoomType.DOUBLE;
               }

               Class[] cArg = new Class[2];
               cArg[0] = Integer.class;
               cArg[1] = RoomType.class;

               room = cls.cast(cls.getDeclaredConstructor(cArg).newInstance(roomNumber, roomType));
               rooms.add(room);
               line = buffer.readLine();
           }

       } catch (Exception e) {
           e.printStackTrace();
           System.out.println("Try again!");
       }

       return rooms;
   }

}


