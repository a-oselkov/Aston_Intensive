import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * Contains methods for sending various requests to third-party resources.
 */
public final class Request {

    /**
     * Sends a GET request with the passed parameters.
     * @param url the address to which the request will be sent
     * @param headerName header name
     * @param headerValue header value
     * @return Http Response holding a String type of body.
     */
    public static HttpResponse<String> get(String url, String headerName, String headerValue) {
        return Unirest.get(url)
                .header(headerName, headerValue)
                .asString();
    }

    /**
     * Sends a GET request with the passed parameters.
     * @param url the address to which the request will be sent
     * @return Http Response holding a String type of body.
     */
    public static HttpResponse<String> get(String url) {
        return Unirest.get(url)
                .asString();
    }
}
