package org.sasha.utils;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * Contains methods for sending various requests to third-party resources.
 */
public final class Request {


    private static final String PREF = "http://api.weatherapi.com/v1/forecast.json?key=";
    private static final String KEY = "cab6006614334d85a8f181853231310";
    private static final String LOC_PART = "&q=";
    private static final String SUF = "&days=3&aqi=no&alerts=no";
    private static final String NOVGOROD = "Yarovitsy";


    /**
     * Sends a GET request with the passed parameters.
     * @param url the address to which the request will be sent
     * @return Http Response holding a String type of body.
     */
    public static HttpResponse<String> doGet(String url) {
        return Unirest.get(url)
                .asString();
    }

    public static String getApiUrl(String town) {
        if (town == null) {
            town = NOVGOROD;
        }
        return PREF + KEY + LOC_PART + town + SUF;
    }
}
