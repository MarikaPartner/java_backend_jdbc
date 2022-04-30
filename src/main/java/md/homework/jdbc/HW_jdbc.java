package md.homework.jdbc;


import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HW_jdbc {

    public static void main( String[] args ) {
        User user = new User(2022, "User 2022");
        insertUser(user);
        getUser(user.getUser_id()).forEach(System.out::println);
        updateUserName(user.getUser_id(), user.getName() + " changed");
        getUser(user.getUser_id()).forEach(System.out::println);
        deleteUser(user.getUser_id());
        getUser(user.getUser_id()).forEach(System.out::println);
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres", "postgres");
    }

    @SneakyThrows
    public static List<User> getUser(int id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where user_id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt("user_id"), resultSet.getString("name")));
            }
            return userList;
        }
    }

    @SneakyThrows
    public static void insertUser(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (user_id, name) values(?, ?)");
            preparedStatement.setInt(1, user.getUser_id());
            preparedStatement.setString(2, user.getName());
            preparedStatement.executeUpdate();
        }
    }


    @SneakyThrows
    public static void updateUserName(int id, String newName) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name=? where user_id=?");
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public static void deleteUser(int id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where user_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
