package org.sasha.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sasha.model.CurrentWeather;
import org.sasha.model.Location;
import org.sasha.model.User;
import org.sasha.config.DBConfig;
import org.sasha.dto.UserDto;
import org.sasha.utils.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDao {

    private final SessionFactory sessionFactory;
    private Transaction transaction;

    public UserDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void save(UserDto dto) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = new User();
            user.setName(dto.getName());
            user.setRegion(dto.getRegion());
            user.setEmail(dto.getEmail());
            user.setPass(dto.getPass());

            session.persist(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            //transaction = session.beginTransaction();
            Query<User> query = session.createQuery("from User",
                    User.class);
            allUsers = query.getResultList();
            //transaction.commit();
            return allUsers;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return allUsers;
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            //transaction = session.beginTransaction();
            Query<User> query = session.createQuery("from User " +
                    "where email =:email", User.class);
            query.setParameter("email", email);
            //transaction.commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            //transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            //transaction.commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = findById(id).get();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

//    public Optional<String> findLastCheck(Long id) {
//        String sql = "SELECT location.region FROM location WHERE location.id = " +
//                "(SELECT location_id FROM current_weather WHERE current_weather.id = " +
//                "(SELECT MAX(check_id) FROM user_check WHERE user_id = ?))";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String region = resultSet.getString("region");
//                return Optional.of(region);
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<CurrentWeather> findAllCheckById(Long id) {
//        String sql = "SELECT * FROM current_weather WHERE id IN " +
//                "(SELECT check_id FROM user_check WHERE user_id = ?)";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            List<CurrentWeather> result = new ArrayList<>();
//            while (resultSet.next()) {
//                Long checkId = resultSet.getLong("id");
//                String temp = resultSet.getString("temp");
//                String feelsLike = resultSet.getString("feels_like");
//                String cloud = resultSet.getString("cloud");
//                Long locId = resultSet.getLong("location_id");
//
//                CurrentWeather currentWeather= new CurrentWeather(checkId, temp, feelsLike, cloud, locId);
//                result.add(currentWeather);
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
