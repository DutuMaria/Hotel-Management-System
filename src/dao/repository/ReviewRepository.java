package dao.repository;

import dao.configuration.DatabaseConfiguration;

import java.sql.*;

public class ReviewRepository {

    private static ReviewRepository reviewRepository;

    private ReviewRepository() {}

    public static ReviewRepository getReviewRepository(){
        if(reviewRepository == null){
            reviewRepository = new ReviewRepository();
        }
        return reviewRepository;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS reviews" +
                "(id int PRIMARY KEY AUTO_INCREMENT, stars int, service int, rooms int, cleanliness int, sleepQuality int, description varchar(300))";

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
    public void insertReview(int stars, int service, int rooms, int cleanliness, int sleepQuality, String description) {
        String insertReviewSql = "INSERT INTO reviews(stars, service, rooms, cleanliness, sleepQuality, description) VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertReviewSql);
            preparedStatement.setInt(1, stars);
            preparedStatement.setInt(2, service);
            preparedStatement.setInt(3, rooms);
            preparedStatement.setInt(4, cleanliness);
            preparedStatement.setInt(5, sleepQuality);
            preparedStatement.setString(6, description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT
    public void selectReviews() {
        String selectReviewsSql = "SELECT * FROM reviews";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectReviewsSql);
            System.out.println("Reviews: ");
            while (resultSet.next()) {
                // id => 1
                System.out.println("\tStars: " + resultSet.getInt(2));
                System.out.println("\tService: " + resultSet.getInt(3));
                System.out.println("\tRooms: " + resultSet.getInt(4));
                System.out.println("\tCleanliness: " + resultSet.getInt(5));
                System.out.println("\tSleep quality " + resultSet.getInt(6));
                System.out.println("\tDescription: " + resultSet.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateStarsReview(int id, int stars) {
        String updateStarsReviewSql = "UPDATE reviews SET stars=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStarsReviewSql);
            preparedStatement.setInt(1, stars);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteReview(int id) {
        String deleteStarsReviewSql = "DELETE FROM reviews WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStarsReviewSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
