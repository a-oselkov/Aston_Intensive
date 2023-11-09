import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sasha.controller.UsersServlet;
import org.sasha.dao.UserDao;
import org.sasha.dao.WeatherDao;
import org.sasha.dto.UserDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.model.User;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTimestampSourceEnum.DB;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sasha.App.getApp;
import static org.sasha.App.getPort;


class AppTest {

    private static final Path TEST_JSON = Paths.get("src/test/resources/test.txt");
    private static final String TEST_REPORT = "src/test/resources/test_report.txt";
    private static final Path PATH = Paths.get(TEST_REPORT);
    private static String data;


    private UsersServlet usersServlet;
    private WeatherDao weatherDao = new WeatherDao();
    private static UserDao userDao = new UserDao();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private static Tomcat app;
    private static String baseUrl;

    @BeforeEach
    public void beforeEach() {
        usersServlet =  new UsersServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @BeforeAll
    static void readResult() throws IOException {
        data = Files.readString(TEST_JSON);
    }

    @BeforeAll
    public static void init() throws LifecycleException {
        int port = getPort();
        app = getApp(port);
        app.start();

        UserDto userDef = new UserDto("Default", "Moscow", "default", "123");
        userDao.save(userDef);

        UserDto user = new UserDto("Ivan", "Novgorod", "1@1", "123");
        userDao.save(user);

        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void getAllUser() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("show");
        usersServlet.doGet(request, response);

        verify(request, times(1)).getPathInfo();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void showUserListTest() {
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/users")
                .asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("Ivan");
    }

    @Test
    void showUserTest() {
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/users?id=1")
                .asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("Moscow");
    }

    @Test
    public void dataFromApiTest() {
        WeatherDto dto;
        dto = weatherDao.parseDataFromApi(data);

        assertThat(dto.getLocation().getRegion()).isEqualTo("Novgorod");
        assertThat(dto.getCurrent().getTemp_c()).isEqualTo("7.6");
        assertThat(dto.getCurrent().getCloud()).isEqualTo("100");
    }
//REPO тесты
    @Test
    public void findUserByEmailTest() {
        User user = userDao.findByEmail("1@1").get();

        assertThat(user.getEmail()).isEqualTo("1@1");
        assertThat(user.getRegion()).isEqualTo("Novgorod");
    }


//    @Test
//    public void testWriteWeatherDataInFile() throws IOException {
//        WeatherDataParser parser = new WeatherDataParser(data);
//        WeatherData weatherData = new WeatherData();
//        WeatherWriter writer = new WeatherWriter(weatherData, TEST_REPORT);
//
//        weatherData.fillWeatherData(parser);
//        writer.writeWeatherDataInFile();
//
//        String fileData = Files.readString(PATH);
//
//        assertThat(fileData).isNotEmpty();
//        assertThat(fileData).contains("Russia");
//        assertThat(fileData).contains("2023-10-11");
//        assertThat(fileData).contains("00-23");
}