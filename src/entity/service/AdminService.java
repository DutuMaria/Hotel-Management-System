package entity.service;

import entity.room.RoomStatus;
import entity.room.RoomType;
import entity.user.Admin;
import entity.user.User;

import java.util.Scanner;

public class AdminService implements ServiceInterface{
    private static AdminService adminService;

    private AdminService(){}

    public static AdminService getAdminServiceInstance(){
        if (adminService == null)
            adminService = new AdminService();
        return adminService;
    }

    @Override
    public void logIn() {
        Admin admin = Admin.getAdminInstance();
        int nrOfAttempts = 5;
        while (nrOfAttempts > 0){
            System.out.println("\t Username: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            System.out.println("\t Password: ");
            String password = scanner.nextLine();

            if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())){
                showFunctionalities(admin);
                return;
            }
            nrOfAttempts--;
            if (nrOfAttempts == 0){
                System.out.println("Username or Password incorrect! Try again later!");
            } else{
                System.out.println("Username or Password incorrect! Try again! You have " + nrOfAttempts + " attempts left.");
            }
        }
    }

    @Override
    public void showFunctionalities(User user) {
        int option = 0;
        while (option != 9){
            System.out.println("\n\t-------------------- Admin Functionalities ---------------------\n");
            System.out.println("\t Choose a functionality (1/2/3/4/5/6/7/8/9):");
            System.out.println("\t 1. View all bookings.");
            System.out.println("\t 2. View all customers.");
            System.out.println("\t 3. View all rooms.");
            System.out.println("\t 4. Add customer.");
            System.out.println("\t 5. Add room.");
            System.out.println("\t 6. Delete room.");
            System.out.println("\t 7. Change room status.");
            System.out.println("\t 8. Change room type.");
            System.out.println("\t 9. Exit.\n");

            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();

            switch (option){
                case (1):
                    ((Admin) user).viewAllBookings();
                    break;
                case (2):
                    ((Admin) user).viewAllCustomers();
                    break;
                case (3):
                    ((Admin) user).viewAllRooms();
                    break;
                case (4):
                    ((Admin) user).addCustomer();
                    break;
                case (5):
                    ((Admin) user).addRoom();
                    break;
                case (6):
                    int roomNr;
                    System.out.println("\t Enter room number: ");
                    roomNr = scanner.nextInt();
                    ((Admin) user).deleteRoom(roomNr);
                    break;
                case (7):
                    System.out.println("\t Enter room number: ");
                    roomNr = scanner.nextInt();
                    int roomStatus;
                    System.out.println("\t If you want to make the room UNAVAILABLE => type 0");
                    System.out.println("\t If you want to make the room AVAILABLE => type 1 ");
                    roomStatus = scanner.nextInt();
                    if (roomStatus == 0){
                        ((Admin) user).changeRoomStatus(roomNr, RoomStatus.UNAVAILABLE);
                    }
                    if (roomStatus == 1){
                        ((Admin) user).changeRoomStatus(roomNr, RoomStatus.AVAILABLE);
                    }
                    break;
                case (8):
                    System.out.println("\t Enter room number: ");
                    roomNr = scanner.nextInt();
                    int roomType;
                    System.out.println("\t If you want to make the room Single => type 1");
                    System.out.println("\t If you want to make the room Double => type 2");
                    roomType = scanner.nextInt();
                    if (roomType == 1){
                        ((Admin) user).changeRoomType(roomNr, RoomType.SINGLE);
                    }
                    if (roomType == 2){
                        ((Admin) user).changeRoomType(roomNr, RoomType.DOUBLE);
                    }
                    break;
            }
        }
    }
}

