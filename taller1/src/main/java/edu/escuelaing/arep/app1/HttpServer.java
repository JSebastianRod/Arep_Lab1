package edu.escuelaing.arep.app1;

import java.net.*;

import java.io.*;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import edu.escuelaing.arep.app1.ServerCache;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstLine = true;
            String uriString = "";
            ServerCache cache = new ServerCache();

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("hello?name=")) {
                    String[] res = inputLine.split("name=");
                    uriString = (res[1].split("HTTP")[0]).replace(" ", "");
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if (!uriString.equals("")) {
                if (cache.containsKey(uriString)) {
                    System.out.println("Ya existe en cache");
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + "<br>"
                            + "<table border=\" 1 \"> \n "
                            + data(cache.getCache(uriString))
                            + "</table>"
                            + "<br>"
                            + "<h3>Datos tomados de nuestro cache</h3>\n";
                    cache.printMap();
                } else {
                    System.out.println("No existe en cache");
                    String answer = HttpGetter.getMovie(uriString);
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + "<br>"
                            + "<table border=\" 1 \"> \n "
                            + data(answer)
                            + "</table>"
                            + "<br>"
                            + "<h3>Datos tomados de la api http://www.omdbapi.com, agradezco su colaboracion</h3>\n";
                    cache.setCache(uriString);
                    System.out.println("Se agrego a cache");
                }
            } else {
                outputLine = getIndexResponse();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String data(String answer) {
        HashMap<String, String> hash = new HashMap<String, String>();
        JSONArray arr = new JSONArray(answer);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = arr.getJSONObject(i);
            for (String key : object.keySet()) {
                hash.put(key.toString(), object.get(key).toString());
            }
        }
        String dataTable = "<tr> \n";
        for (String key : hash.keySet()) {
            String value = hash.get(key);
            dataTable += "<tr> \n"
                    + "<td>" + key + "</td> \n"
                    + "<td>" + value + "</td> \n"
                    + "</tr> \n";
        }
        return dataTable;
    }

    public static String getIndexResponse() {
        String response = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html\n>"
                + "    <head\n>"
                + "        <title>Form Example</title\n>"
                + "        <meta charset=\"UTF-8\"\n>"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Movie Finder</h1>\n"
                + "        <h2>Source for movies</h2>\n"
                + "        <form action=\"/hello\">\n"
                + "            <label for=\"name\">Name of the movie:</label><br>\n"
                + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"The Batman\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                + "        </form>\n"
                + "        <div id=\"getrespmsg\"></div>\n"
                + "      <script>\n"
                + "            function loadGetMsg() {\n"
                + "                let nameVar = document.getElementById(\"name\").value;\n"
                + "                const xhttp = new XMLHttpRequest();\n"
                + "                xhttp.onload = function() {\n"
                + "                    document.getElementById(\"getrespmsg\").innerHTML =\n"
                + "                    this.responseText;\n"
                + "                } \n"
                + "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n"
                + "                xhttp.send();\n"
                + "            }\n"
                + "      </script>\n"
                + "    </body>\n"
                + "</html>\n";
        return response;
    }
}