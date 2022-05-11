package dao.repository;

import dao.configuration.DatabaseConfiguration;
import entity.user.UserDocument;

import java.sql.*;

public class CustomerRepository {
    private static CustomerRepository customerRepository;

    private CustomerRepository() {}

    public static CustomerRepository getCustomerRepository(){
        if(customerRepository == null){
            customerRepository = new CustomerRepository();
        }
        return customerRepository;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS customers" +
                "(id int PRIMARY KEY AUTO_INCREMENT, firstName varchar(30), lastName varchar(30), userDocument enum('ID', 'PASSPORT'), address varchar(50), telephone varchar(30), username varchar(30) , password varchar(30), email varchar(30))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //INSERT
    public void insertCustomer(String firstName, String lastName, UserDocument userDocument, String address, String telephone, String username, String password, String email) {
        String insertCustomerSql = "INSERT INTO customers(firstName, lastName, userDocument, address, telephone, username, password, email) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerSql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userDocument.name());
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT
    public void selectCustomers() {
        String selectCustomersSql = "SELECT * FROM customers";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectCustomersSql);
            System.out.println("Customers: ");
            while (resultSet.next()) {
                // id => 1
                System.out.println("\tFirst name: " + resultSet.getString(2));
                System.out.println("\tLast name: " + resultSet.getString(3));
                System.out.println("\tDocument: " + resultSet.getString(4));
                System.out.println("\tAddress: " + resultSet.getString(5));
                System.out.println("\tTelephone: " + resultSet.getString(6));
                System.out.println("\tUsername: " + resultSet.getString(7));
                System.out.println("\tPassword: " + resultSet.getString(8));
                System.out.println("\tEmail: " + resultSet.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateCustomerPassword(String username, String newPassword) {
        String updateCustomerPasswordSql = "UPDATE customers SET password=? WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateCustomerPasswordSql);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateCustomerUsername(String username, String newUsername) {
        String updateCustomerUsernameSql = "UPDATE customers SET username=? WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateCustomerUsernameSql);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // DELETE
    public void deleteCustomer(String username) {
        String deleteCustomerSql = "DELETE FROM customers WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomerSql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
