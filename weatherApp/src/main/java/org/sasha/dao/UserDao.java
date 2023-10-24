package org.sasha.dao;

import org.sasha.model.CurrentWeather;
import org.sasha.model.User;
import org.sasha.config.DBConfig;
import org.sasha.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    public void save(UserDto dto) {
        String sql = "INSERT INTO users (name, region, email, pass) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, dto.getName());
            preparedStatement.setString(2, dto.getRegion());
            preparedStatement.setString(3, dto.getEmail());
            preparedStatement.setString(4, dto.getPass());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String region = resultSet.getString("region");
                String mail = resultSet.getString("email");
                String pass = resultSet.getString("pass");

                User user = new User(id, name, region, mail, pass);
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

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String region = resultSet.getString("region");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                User user = new User(id, name, region, email, pass);
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CurrentWeather> findAllCheckById(Long id) {
        String sql = "SELECT * FROM current_weather WHERE id IN " +
                "(SELECT check_id FROM user_check WHERE user_id = ?)";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CurrentWeather> result = new ArrayList<>();
            while (resultSet.next()) {
                Long checkId = resultSet.getLong("id");
                String temp = resultSet.getString("temp");
                String feelsLike = resultSet.getString("feels_like");
                String cloud = resultSet.getString("cloud");
                Long locId = resultSet.getLong("location_id");

                CurrentWeather currentWeather= new CurrentWeather(checkId, temp, feelsLike, cloud, locId);
                result.add(currentWeather);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String region = resultSet.getString("region");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");

                User user = new User(id, name, region, email, pass);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (findById(id).isPresent()) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
