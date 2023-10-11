import kong.unirest.HttpResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private static final Path TEST_JSON = Paths.get("src/test/resources/test.txt");
    private static String data;

    @BeforeAll
    static void readResult() throws IOException {
        data = Files.readString(TEST_JSON);
    }


    @Test
    public void testRequest () {
        String dataFromApi;

        dataFromApi = Request.get(WeatherServlet.WEATHER_API_URL,
                WeatherServlet.HEADER_NAME, WeatherServlet.HEADER_VALUE).getBody();

        assertThat(dataFromApi).contains("region");
        assertThat(dataFromApi).contains("temp_c");
        assertThat(dataFromApi).contains("forecast");
    }

    @Test
    public void testParseRegionInfo () {
        WeatherDataParser parser = new WeatherDataParser(data);
        String regionInfo;

        regionInfo = parser.parseRegionInfo();

        assertThat(regionInfo).isNotNull();
        assertThat(regionInfo).isEqualTo("Novgorod");
    }

    @Test
    public void testParseCountryInfo () {
        WeatherDataParser parser = new WeatherDataParser(data);
        String countryInfo;

        countryInfo = parser.parseCountryInfo();

        assertThat(countryInfo).isNotNull();
        assertThat(countryInfo).isEqualTo("Russia");
    }

    @Test
    public void testParseCurrentWeatherData () {
        WeatherDataParser parser = new WeatherDataParser(data);
        String currentWeather;

        currentWeather = parser.parseCurrentWeatherData();

        assertThat(currentWeather).isNotNull();
        assertThat(currentWeather).isEqualTo("0");
    }

    @Test
    public void testParseWeatherForThreeDays () {
        WeatherDataParser parser = new WeatherDataParser(data);
        Map<String, Map<String, String>> weatherForThreeDays;

        weatherForThreeDays = parser.parseWeatherForThreeDays();

        assertThat(weatherForThreeDays).isNotNull();
        assertThat(weatherForThreeDays.size()).isEqualTo(3);
        assertThat(weatherForThreeDays.get("2023-10-11").size()).isEqualTo(3);
        assertThat(weatherForThreeDays.get("2023-10-13").size()).isEqualTo(3);
        assertThat(weatherForThreeDays.get("2023-10-12").get("min")).isEqualTo("6.7");

    }

    @Test
    public void testParsePerHourWeather () {
        WeatherDataParser parser = new WeatherDataParser(data);
        Map<String, String> perHourWeather = new LinkedHashMap<>();

        perHourWeather = parser.parsePerHourWeather();

        assertThat(perHourWeather).isNotNull();
        assertThat(perHourWeather.size()).isEqualTo(24);
        assertThat(perHourWeather.get("00-00")).isEqualTo("2.2");
        assertThat(perHourWeather.get("00-23")).isEqualTo("10.4");

    }








//    private static Tomcat app;
//
//    @BeforeAll
//    public static void beforeAll() throws LifecycleException {
//        app = App.getApp(0);
//        app.start();
//        app.getServer().await();
//    }
//
//    @Test
//    void testCheckUrl() throws IOException, LifecycleException {
//
//        Path path = Paths.get("test.html").toAbsolutePath().normalize();
//        String testHtml = Files.readString(path);
//
//        Request.get(WeatherServlet.WEATHER_API_URL, WeatherServlet.HEADER_NAME, WeatherServlet.HEADER_VALUE).getBody();
//
//        MockWebServer mockServer = new MockWebServer();
//        MockResponse mockedResponse = new MockResponse()
//                .setBody(Request.get(WeatherServlet.WEATHER_API_URL, WeatherServlet.HEADER_NAME, WeatherServlet.HEADER_VALUE).getBody());
//
//        mockServer.enqueue(mockedResponse);
//        mockServer.start();
//
//        String urlName = mockServer.url("/").toString().replaceAll("/$", "");
//
//        HttpResponse<String> request = Request.get(urlName);
//        assertEquals(200, request.getStatus());

//        mockServer.shutdown();
//    }
//
//    @AfterAll
//    public static void afterAll() throws LifecycleException {
//        app.stop();
//    }

}