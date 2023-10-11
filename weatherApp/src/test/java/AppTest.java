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
//        assertThat(responsePost.getHeaders().getFirst("Location")).isEqualTo("/urls");

//        Url url = Querys.getUrlByName(urlName);
//        HttpResponse<String> respPost = Responses.responseToPost(baseUrl + "/urls/" + url.getId() + "/checks");
//        assertThat(respPost.getStatus()).isEqualTo(302);
//        assertThat(respPost.getHeaders().getFirst("Location")).isEqualTo("/urls/" + url.getId());
//
//        HttpResponse<String> responseGet = Responses.responseToGet(baseUrl + "/urls/" + url.getId());
//        assertThat(responseGet.getBody()).contains(SUCCESSFULLY_VERIFIED_MSG);
//        assertThat(responseGet.getBody()).contains("h1Test", "titleTest", "descriptionTest");
//
//        UrlCheck urlCheck = Querys.getUrlCheckByUrl(url);
//        assertThat(urlCheck).isNotNull();

//        mockServer.shutdown();
//    }
//
//    @AfterAll
//    public static void afterAll() throws LifecycleException {
//        app.stop();
//    }

}