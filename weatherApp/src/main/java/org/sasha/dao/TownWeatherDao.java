package org.sasha.dao;

import org.sasha.Model.TownWeather;
import org.sasha.config.DBConfig;
import org.sasha.dto.TownWeatherDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TownWeatherDao {

    public void save(TownWeatherDto weather) {
        String sql = "INSERT INTO town_weather (name, temp, feels_like, cloud) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, weather.getName());
            preparedStatement.setString(2, weather.getTemp());
            preparedStatement.setString(3, weather.getFeelsLikeTemp());
            preparedStatement.setString(4, weather.getCloud());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Optional<TownWeather> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM town_weather WHERE id = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String temp = resultSet.getString("temp");
                String feelsLike = resultSet.getString("feels_like");
                String cloud = resultSet.getString("cloud");
                TownWeather townWeather = new TownWeather(id, name, temp, feelsLike, cloud);
                return Optional.of(townWeather);
            }
            return Optional.empty();
        }
    }

    public List<TownWeather> findAll() throws SQLException {
        String sql = "SELECT * FROM town_weather";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TownWeather> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String temp = resultSet.getString("temp");
                String feelsLike = resultSet.getString("feels_like");
                String cloud = resultSet.getString("cloud");
                TownWeather townWeather = new TownWeather(id, name, temp, feelsLike, cloud);
                result.add(townWeather);
            }
            return result;
        }
    }

    public void deleteById(Long id) throws SQLException {
        if (findById(id).isPresent()) {
            String sql = "DELETE FROM town_weather WHERE id = ?";
            try (Connection connection = DBConfig.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }
}
