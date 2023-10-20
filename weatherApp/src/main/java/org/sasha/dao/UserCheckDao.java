package org.sasha.dao;

import org.sasha.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.sasha.controller.LoginServlet.ID;

public class UserCheckDao {
    public void save(long userId, long checkId) {
        String sql = "INSERT INTO user_check (user_id, check_id) VALUES (?, ?)";

        try (
                Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, checkId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
