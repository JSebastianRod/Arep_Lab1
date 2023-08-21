
package edu.escuelaing.arep.app1;

import java.util.HashMap;
import java.util.Map;

import edu.escuelaing.arep.app1.HttpGetter;
import edu.escuelaing.arep.app1.HttpServer;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerCache {
    private static HashMap<String, String> cache = new HashMap<String,String>();

    public ServerCache() {

    }

    public static void setCache(String movie) throws IOException{
        cache.put(movie, HttpGetter.getMovie(movie));
    }

    public static String getCache(String movie) throws IOException{
        return cache.get(movie);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public void printMap(){
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }
}

