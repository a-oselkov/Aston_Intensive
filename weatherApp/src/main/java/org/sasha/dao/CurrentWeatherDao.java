package org.sasha.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sasha.Model.CurrentWeather;
import org.sasha.Model.Location;
import org.sasha.Model.TownWeather;
import org.sasha.config.DBConfig;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.sasha.controller.LoginServlet.ID;


//@AllArgsConstructor
//@NoArgsConstructor
public class CurrentWeatherDao {
    public void save(CurrentDto dto, LocationDto location) {

        String sql = "INSERT INTO current_weather (location_id, temp, feels_like, cloud) " +
                "SELECT location.id, ?, ?, ? FROM location WHERE region = ?";

        long checkId = 0;

        //if (findByRegion(location.getRegion()).isEmpty()) {
            try (
                    Connection connection = DBConfig.getConnection();
                    PreparedStatement preparedStatement =
                            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                //preparedStatement.setLong(1, 1);
                preparedStatement.setString(1, dto.getTemp_c());
                preparedStatement.setString(2, dto.getFeelslike_c());
                preparedStatement.setString(3, dto.getCloud());
                preparedStatement.setString(4, location.getRegion());
                preparedStatement.executeUpdate();

                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    checkId = generatedKeys.getLong(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        //}

        sql = "INSERT INTO user_check (user_id, check_id) VALUES (?, ?)";

        try (
                Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, ID);
            preparedStatement.setLong(2, checkId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<CurrentWeather> findByRegion(String regionName) {
        String sql = "SELECT c.id, c.temp, c.feels_like, c.cloud, c.location_id " +
                "FROM current_weather c JOIN location l\n" +
                "ON c.location_id = l.id\n" +
                "WHERE l.region = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, regionName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String temp = resultSet.getString("temp");
                String feelsLike = resultSet.getString("feels_like");
                String cloud = resultSet.getString("cloud");
                Long l_id = resultSet.getLong("location_id");
                CurrentWeather weather = new CurrentWeather(id, temp, feelsLike, cloud, l_id);
                return Optional.of(weather);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
