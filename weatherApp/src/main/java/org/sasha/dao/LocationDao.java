package org.sasha.dao;

import org.sasha.Model.Location;
import org.sasha.config.DBConfig;
import org.sasha.dto.WeatherDto.LocationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationDao {
    public void save(LocationDto dto) {
        String sql = "INSERT INTO location (region, country, local_time) VALUES (?, ?, ?)";
        try (
                Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, dto.getRegion());
            preparedStatement.setString(2, dto.getCountry());
            preparedStatement.setString(3, dto.getLocaltime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Location> findById(Long id) {
        String sql = "SELECT * FROM location WHERE id = ?";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String region = resultSet.getString("region");
                String country = resultSet.getString("country");
                String localtime = resultSet.getString("localtime");

                Location location = new Location(id, region, country, localtime);
                return Optional.of(location);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Location> findAll() {
        String sql = "SELECT * FROM location";
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Location> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String region = resultSet.getString("region");
                String country = resultSet.getString("country");
                String localtime = resultSet.getString("localtime");

                Location location = new Location(id, region, country, localtime);
                result.add(location);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM location WHERE id = ?";
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

    public void deleteAll() {
        String sql = "TRUNCATE location";
        try (
                Connection connection = DBConfig.getConnection();
                Statement statement = connection.createStatement();) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
