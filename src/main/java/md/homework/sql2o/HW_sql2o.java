package md.homework.sql2o;

import lombok.SneakyThrows;
import md.homework.jdbc.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

public class HW_sql2o {
    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5438/postgres", "postgres", "postgres");

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
    public static List<User> getUser(int id) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("select * from users where user_id=:user_id")
                    .addParameter("user_id", id)
                    .executeAndFetch(User.class);
        }
    }

    @SneakyThrows
    public static void insertUser(User user) {
        try (Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("insert into users (user_id, name) values(:user_id, :name)")
                    .bind(user)  // можно использовать, если поля класса совпадают с полями таблицы
                    .executeUpdate();
            connection.commit();
        }
    }

    @SneakyThrows
    public static void updateUserName(int id, String newName) {
        try (Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("update users set name=:name where user_id=:user_id")
                    .addParameter("name", newName)
                    .addParameter("user_id", id)
                    .executeUpdate();
            connection.commit();
        }
    }

    @SneakyThrows
    public static void deleteUser(int id) {
        try (Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("delete from users where user_id=:user_id")
                    .addParameter("user_id", id)
                    .executeUpdate();
            connection.commit();
        }
    }
}