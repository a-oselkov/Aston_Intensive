package org.sasha.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sasha.dto.CurrentWeatherCheckDto;
import org.sasha.dto.UserDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.model.CurrentWeatherCheck;
import org.sasha.model.Location;
import org.sasha.model.User;
import org.sasha.utils.HibernateUtil;

import java.util.Optional;

import static org.sasha.controller.LoginServlet.ID;

public class CurrentWeatherCheckDao {
    private final SessionFactory sessionFactory;
    private final UserDao userDao = new UserDao();
    private final LocationDao locationDao = new LocationDao();

    public CurrentWeatherCheckDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void save(CurrentWeatherCheckDto dto) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CurrentWeatherCheck check = new CurrentWeatherCheck();
        User user = userDao.findById(dto.getUser_id()).get();
        Location location = locationDao.findById(dto.getLocation_id()).get();

        check.setTemp_c(dto.getTemp_c());
        check.setFeelsLike_c(dto.getFeelsLike_c());
        check.setCloud(dto.getCloud());
        check.setUser(user);
        check.setLocation(location);

        session.persist(check);

        location.getUsers().add(user);
        session.merge(location);
        session.flush();

        transaction.commit();


    }

//    public Optional<CurrentWeatherCheck> findLastCheckByUser(User user) {
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            Query<CurrentWeatherCheck> query = session.createQuery("select max(id) " +
//                            "from CurrentWeatherCheck where fk(user.id) =:id",
//                    CurrentWeatherCheck.class);
//            query.setParameter("id", user.getId());
//            transaction.commit();
//            return query.stream().findFirst();
//        }
//    }
//    public void save(CurrentDto dto, LocationDto location) {
//
//        String sql = "INSERT INTO current_weather (location_id, temp, feels_like, cloud) " +
//                "SELECT location.id, ?, ?, ? FROM location WHERE region = ?";
//
//        long checkId = 0;
//
//        //if (findByRegion(location.getRegion()).isEmpty()) {
//            try (
//                    Connection connection = DBConfig.getConnection();
//                    PreparedStatement preparedStatement =
//                            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//                //preparedStatement.setLong(1, 1);
//                preparedStatement.setString(1, dto.getTemp_c());
//                preparedStatement.setString(2, dto.getFeelslike_c());
//                preparedStatement.setString(3, dto.getCloud());
//                preparedStatement.setString(4, location.getRegion());
//                preparedStatement.executeUpdate();
//
//                var generatedKeys = preparedStatement.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    checkId = generatedKeys.getLong(1);
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        //}
//        userCheckDao.save(1, checkId);
//    }
//
//    public Optional<CurrentWeather> findByRegion(String regionName) {
//        String sql = "SELECT c.id, c.temp, c.feels_like, c.cloud, c.location_id " +
//                "FROM current_weather c JOIN location l\n" +
//                "ON c.location_id = l.id\n" +
//                "WHERE l.region = ?";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, regionName);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String temp = resultSet.getString("temp");
//                String feelsLike = resultSet.getString("feels_like");
//                String cloud = resultSet.getString("cloud");
//                Long l_id = resultSet.getLong("location_id");
//                CurrentWeather weather = new CurrentWeather(id, temp, feelsLike, cloud, l_id);
//                return Optional.of(weather);
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<CurrentWeather> findAll() {
//        String sql = "SELECT * FROM current_weather";
//        try (Connection connection = DBConfig.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<CurrentWeather> result = new ArrayList<>();
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String temp = resultSet.getString("temp");
//                String feelsLike = resultSet.getString("feels_like");
//                String cloud = resultSet.getString("cloud");
//                Long locId = resultSet.getLong("location_id");
//                CurrentWeather currentWeather= new CurrentWeather(id, temp, feelsLike, cloud, locId);
//                result.add(currentWeather);
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
