package org.sasha.dao;

import org.sasha.Model.Location;
import org.sasha.Model.TownWeather;
import org.sasha.Model.User;
import org.sasha.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.sasha.controller.LoginServlet.ID;

public class UserDao {
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String region = resultSet.getString("region");
                String mail = resultSet.getString("email");
                String pass = resultSet.getString("pass");

                User user = new User(id, region, mail, pass);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> findLastCheck(Long id) {
        String sql = "SELECT location.region FROM location WHERE location.id = " +
                "(SELECT location_id FROM current_weather WHERE current_weather.id = " +
                "(SELECT MAX(check_id) FROM user_check WHERE user_id = ?))";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String region = resultSet.getString("region");
                return Optional.of(region);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
