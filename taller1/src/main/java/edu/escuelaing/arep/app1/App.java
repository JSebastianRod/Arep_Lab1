package edu.escuelaing.arep.app1;
import java.io.*;
import java.net.*;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        try{
            URL myURL = new URL("http://ldbn.escuelaing.edu.co:80/");
            System.out.println("Protocol " + myURL.getProtocol());
            System.out.println("Host " + myURL.getHost());
            System.out.println("Port " + myURL.getPort());
            System.out.println("Path " + myURL.getPath());
            System.out.println("Query " + myURL.getQuery());
            System.out.println("File " + myURL.getFile());
            System.out.println("Ref " + myURL.getRef());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    

}
