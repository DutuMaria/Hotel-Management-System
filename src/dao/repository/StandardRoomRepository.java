package dao.repository;

import dao.configuration.DatabaseConfiguration;
import entity.room.RoomType;

import java.sql.*;

public class StandardRoomRepository {
    private static StandardRoomRepository standardRoomRepository;

    private StandardRoomRepository() {}

    public static StandardRoomRepository getStandardRoomRepository(){
        if(standardRoomRepository == null){
            standardRoomRepository = new StandardRoomRepository();
        }
        return standardRoomRepository;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS standardRooms" +
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
    public void insertStandardRoom(Integer roomNumber, RoomType roomType) {
        String insertStandardRoomSql = "INSERT INTO standardRooms(roomNumber, roomType) VALUES(?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStandardRoomSql);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setString(2, roomType.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // SELECT
    public void selectStandardRooms() {
        String selectStandardRoomsSql = "SELECT * FROM standardRooms";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectStandardRoomsSql);
            System.out.println("Standard rooms: ");
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
    public String selectStandardRoomByRoomNumber(int roomNumber) {
        String selectStandardRoomsSql = "SELECT roomType FROM standardRooms WHERE roomNumber=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStandardRoomsSql);
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
    public void updateStandardRoomType(int roomNumber, RoomType roomType) {
        String updateStandardRoomTypeSql = "UPDATE standardRooms SET roomType=? WHERE roomNumber=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStandardRoomTypeSql);
            preparedStatement.setString(1, roomType.name());
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteStandardRoom(int roomNumber) {
        String deleteStandardRoomSql = "DELETE FROM standardRooms WHERE roomNumber=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStandardRoomSql);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
