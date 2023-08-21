package edu.escuelaing.arep.app1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetter {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=99c04438";


    public static String getMovie(String title) throws IOException {

        String siteUrl = GET_URL + title + API_KEY;
        URL obj = new URL(siteUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // The following invocation perform the connection implicitly before getting the
        // code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result

            System.out.println(response.toString());
            String movieData = "[" + response.toString() + "]";
            return movieData;
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return "GET DONE";
    }
}
