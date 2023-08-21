package edu.escuelaing.arep.app1;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

import netscape.javascript.JSObject;

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
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }

            if (!uriString.equals("")) {
                String answer = HttpGetter.getMovie(uriString);
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<br>"
                        + "<table border=\" 0.5 \"> \n "
                        + data(answer)
                        + "</table>";
            } else {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + getIndexResponse();
            }
            System.out.println("URI: " + uriString);
            if (uriString.startsWith("/hello?")) {
                outputLine = getHello(uriString);
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
        HashMap<String, String> dict = new HashMap<String,String>();
        JSONArray arr = new JSONArray(answer);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = arr.getJSONObject(i);
            for (String key : object.keySet()) {
                dict.put(key.toString(), object.get(key).toString());
            }
        }
        String table = "<tr> \n";
        for (String key : dict.keySet()) {
            String value = dict.get(key);
            table += "<tr> \n"
                    + "<td>" + key + "</td> \n"
                    + "<td>" + value + "</td> \n"
                    + "</tr> \n";
        }
        return table;
    }

    public static String getHello(String uri) {
        String response = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "{ \"msg\": \"Hello Pedro\" }"
                + "<table border=\" 0.5 \"> \n "
                + data(answer)
                + "</table>";
        return response;
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
                + "        <h1>Movie finder</h1>\n"
                + "        <form action=\"/hello\">\n"
                + "            <label for=\"name\">Name:</label><br>\n"
                + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"The Batman\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                + "        </form>\n"
                + "        <div id=\"getrespmsg\"></div>\n"
                + "    <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                if (nameVar) {\n" +
                "                   console.log(\"Nombre \" + nameVar)\n" +
                "                   const xhttp = new XMLHttpRequest();\n" +
                "                   xhttp.onload = function() {\n" +
                "                       document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                       this.responseText;\n" +
                "                   }\n" +
                "                   xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                "                   xhttp.send();\n" +
                "                };\n" +
                "            }\n" +
                "        </script>\n"
                + "    </body>\n"
                + "</html>\n";
        return response;
    }
}