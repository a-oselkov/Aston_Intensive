package org.sasha.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sasha.model.Location;
import org.sasha.config.DBConfig;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.utils.HibernateUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationDao {

    private final SessionFactory sessionFactory;
    private Transaction transaction;

    public LocationDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public long save(LocationDto dto) {
        if (findByRegion(dto.getRegion()).isEmpty()) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();

                Location location = new Location();
                location.setRegion(dto.getRegion());
                location.setCountry(dto.getCountry());
                location.setLocTime(dto.getLocaltime());

                session.persist(location);
                transaction.commit();

                return location.getId();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } else {
            //обновляем время
        }
        return 1L;
    }

    public Optional<Location> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Location location = session.get(Location.class, id);
            transaction.commit();
            return Optional.ofNullable(location);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Location> findByRegion(String region) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Location> query = session.createQuery("from Location " +
                    "where region =:region", Location.class);
            query.setParameter("region", region);
            transaction.commit();
            return query.uniqueResultOptional();
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
            Location location = findById(id).get();
            session.remove(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Location> query = session.createQuery("from Location",
                    Location.class);
            locations = query.getResultList();
            transaction.commit();
            return locations;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return locations;
    }
    //    public void save(LocationDto dto) {
//        if (findByRegion(dto.getRegion()).isEmpty()) {
//            String sql = "INSERT INTO location (region, country) VALUES (?, ?)";
//            try (
//                    Connection connection = DBConfig.getConnection();
//                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                preparedStatement.setString(1, dto.getRegion());
//                preparedStatement.setString(2, dto.getCountry());
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        } //else {
//            Location location = findByRegion(dto.getRegion()).get();
//            location.setLocaltime(dto.getLocaltime());
//            String sql = "UPDATE location SET local_time = ?";
//            try (
//                    Connection connection = DBConfig.getConnection();
//                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                preparedStatement.setString(1, dto.getLocaltime());
////                preparedStatement.setString(2, dto.getRegion());
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

//    public Optional<Location> findById(Long id) {
//        String sql = "SELECT * FROM location WHERE id = ?";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String region = resultSet.getString("region");
//                String country = resultSet.getString("country");
//
//                Location location = new Location(id, region, country);
//                return Optional.of(location);
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Optional<Location> findByRegion(String name) {
//        String sql = "SELECT * FROM location WHERE region = ?";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, name);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String region = resultSet.getString("region");
//                String country = resultSet.getString("country");
//
//                Location location = new Location(id, region, country);
//                return Optional.of(location);
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Location> findAll() {
//        String sql = "SELECT * FROM location";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<Location> result = new ArrayList<>();
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String region = resultSet.getString("region");
//                String country = resultSet.getString("country");
//
//                Location location = new Location(id, region, country);
//                result.add(location);
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void deleteById(Long id) {
//        String sql = "DELETE FROM location WHERE id = ?";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            if (findById(id).isPresent()) {
//                preparedStatement.setLong(1, id);
//                preparedStatement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

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
