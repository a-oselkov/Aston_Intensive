import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class Request {
    public static HttpResponse<String> get(String url, String headerName, String headerValue) {
        return Unirest.get(url)
                .header(headerName, headerValue)
                .asString();
    }

    public static HttpResponse<String> get(String url) {
        return Unirest.get(url)
                .asString();
    }
}
