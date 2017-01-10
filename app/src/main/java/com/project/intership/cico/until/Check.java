package com.project.intership.cico.until;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by giang on 1/10/2017.
 */

public class Check {
    public boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            e.printStackTrace();
            return false;
        }
    }

}
