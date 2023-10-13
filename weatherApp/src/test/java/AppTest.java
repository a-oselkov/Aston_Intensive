import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sasha.Model.CurrentWeather;
import org.sasha.Model.DayWeather;
import org.sasha.Model.HourWeather;
import org.sasha.utils.Request;
import org.sasha.WeatherServlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sasha.utils.Parser.getCurrentWeather;
import static org.sasha.utils.Parser.getHourWeather;
import static org.sasha.utils.Parser.getRegionInfo;
import static org.sasha.utils.Parser.getWeatherForDay;

class AppTest {

    private static final Path TEST_JSON = Paths.get("src/test/resources/test.txt");
    private static final String TEST_REPORT = "src/test/resources/test_report.txt";
    private static final Path PATH = Paths.get(TEST_REPORT);
    private static String data;

    @BeforeAll
    static void readResult() throws IOException {
        data = Files.readString(TEST_JSON);
    }

    @AfterAll
    static void clearReport() throws FileNotFoundException {
        //Очистка файла
        PrintWriter wr = new PrintWriter(TEST_REPORT);
        wr.print("");
        wr.close();
    }


    @Test
    public void testRequest() {
        String dataFromApi;

        dataFromApi = Request.get(WeatherServlet.WEATHER_API_URL,
                WeatherServlet.HEADER_NAME, WeatherServlet.HEADER_VALUE).getBody();

        assertThat(dataFromApi).contains("region");
        assertThat(dataFromApi).contains("temp_c");
        assertThat(dataFromApi).contains("forecast");
    }

    @Test
    public void testParseRegionInfo() {
        String regionInfo = getRegionInfo(data);

        assertThat(regionInfo).isNotNull();
        assertThat(regionInfo).isEqualTo("Novgorod");
    }

    @Test
    public void testParseCurrentWeather () {
        CurrentWeather weather = getCurrentWeather(data);

        assertThat(weather).isNotNull();
        assertThat(weather.getTemp()).isEqualTo("7.6");
        assertThat(weather.getRegion()).isEqualTo("Novgorod");
    }

    @Test
    public void testParseDayWeather () {
        DayWeather weather = getWeatherForDay(data, 0);

        assertThat(weather).isNotNull();
        assertThat(weather.getDate()).isEqualTo("2023-10-11");
        assertThat(weather.getTempAvg()).isEqualTo("6.3");

    }

    @Test
    public void testParseHourWeather () {
        HourWeather weather = getHourWeather(data, 0);

        assertThat(weather).isNotNull();
        assertThat(weather.getTime()).contains("00:00");
        assertThat(weather.getTemp()).isEqualTo("2.2");

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