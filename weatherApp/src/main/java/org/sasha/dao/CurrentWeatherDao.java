package org.sasha.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sasha.Model.Location;
import org.sasha.Model.TownWeather;
import org.sasha.config.DBConfig;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


//@AllArgsConstructor
//@NoArgsConstructor
public class CurrentWeatherDao {
    public void save(CurrentDto dto, LocationDto location) {
        String sql = "INSERT INTO current_weather (location_id, temp, feels_like, cloud) " +
                "SELECT location.id, ?, ?, ? FROM location WHERE region = ?";
        try (
                Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, dto.getTemp_c());
            preparedStatement.setString(2, dto.getFeelslike_c());
            preparedStatement.setString(3, dto.getCloud());
            preparedStatement.setString(4, location.getRegion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public Optional<TownWeather> findById(Long id) {
//        String sql = "SELECT * FROM town_weather WHERE id = ?";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String temp = resultSet.getString("temp");
//                String feelsLike = resultSet.getString("feels_like");
//                String cloud = resultSet.getString("cloud");
//                TownWeather townWeather = new TownWeather(id, name, temp, feelsLike, cloud);
//                return Optional.of(townWeather);
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<TownWeather> findAll() {
//        String sql = "SELECT * FROM town_weather";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<TownWeather> result = new ArrayList<>();
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String name = resultSet.getString("name");
//                String temp = resultSet.getString("temp");
//                String feelsLike = resultSet.getString("feels_like");
//                String cloud = resultSet.getString("cloud");
//                TownWeather townWeather = new TownWeather(id, name, temp, feelsLike, cloud);
//                result.add(townWeather);
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void deleteById(Long id) {
//
//        String sql = "DELETE FROM town_weather WHERE id = ?";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            if (findById(id).isPresent()) {
//                preparedStatement.setLong(1, id);
//                preparedStatement.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void deleteAll() {
//        String sql = "TRUNCATE town_weather";
//        try (
//                Connection connection = DBConfig.getConnection();
//                Statement statement = connection.createStatement();) {
//            statement.execute(sql);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
