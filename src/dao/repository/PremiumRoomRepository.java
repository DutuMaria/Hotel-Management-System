package dao.repository;

import dao.configuration.DatabaseConfiguration;
import entity.room.RoomType;

import java.sql.*;

public class PremiumRoomRepository {
    private static PremiumRoomRepository premiumRoomRepository;

    private PremiumRoomRepository() {}

    public static PremiumRoomRepository getPremiumRoomRepository(){
        if(premiumRoomRepository == null){
            premiumRoomRepository = new PremiumRoomRepository();
        }
        return premiumRoomRepository;
    }
    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS premiumRooms" +
                "(id int PRIMARY KEY AUTO_INCREMENT, roomNumber int, roomType enum('SINGLE', 'DOUBLE'))";
//        roomStatus enum('AVAILABLE', 'UNAVAILABLE') // vezi daca mai pui coloana asta in tabel, problema -> in functie de data

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // CREATE - INSERT, READ - SELECT, UPDATE, DELETE

    //INSERT
    public void insertPremiumRoom(Integer roomNumber, RoomType roomType) {
        String insertPremiumRoomSql = "INSERT INTO premiumRooms(roomNumber, roomType) VALUES(?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertPremiumRoomSql);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setString(2, roomType.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // SELECT
    public void selectPremiumRooms() {
        String selectPremiumRoomsSql = "SELECT * FROM premiumRooms";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectPremiumRoomsSql);
            System.out.println("Premium rooms: ");
            while (resultSet.next()) {
                // id => 1
                System.out.println("\tRoom number: " + resultSet.getInt(2));
                System.out.println("\tRoom type: " + resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT
    public String selectPremiumRoomByRoomNumber(int roomNumber) {
        String selectPremiumRoomByRoomNumberSql = "SELECT roomType FROM premiumRooms WHERE roomNumber=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectPremiumRoomByRoomNumberSql);
            preparedStatement.setInt(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public void updatePremiumRoomType(int roomNumber, RoomType roomType) {
        String updatePremiumRoomTypeSql = "UPDATE premiumRooms SET roomType=? WHERE roomNumber=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updatePremiumRoomTypeSql);
            preparedStatement.setString(1, roomType.name());
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletePremiumRoom(int roomNumber) {
        String deletePremiumRoomSql = "DELETE FROM premiumRooms WHERE roomNumber=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deletePremiumRoomSql);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
