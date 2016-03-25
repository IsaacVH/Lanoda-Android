package com.isaacvanhouten.lanoda.services;

import android.util.Log;

import com.isaacvanhouten.lanoda.BuildConfig;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by isaac on 3/20/2016.
 */
public class APIClient {
    public enum HttpMethod {
        GET,
        PUT,
        POST,
        DELETE,
    }

    private static String API_URL = BuildConfig.API_URL;

    public static String MakeAPICall(String urlPath, HttpMethod method, Map<String, String> params) {
        String urlString = API_URL + urlPath;
        HttpURLConnection conn = null;

        try {
            // Get the JSON stuff
            String urlParameters = "";
            Set<String> keys = params.keySet();
            Iterator<String> keyIter = keys.iterator();
            while(keyIter.hasNext()) {
                String key = keyIter.next();
                urlParameters += key + "=" + params.get(key);
                urlParameters += "&";
            }
            urlParameters = urlParameters.substring(0, urlParameters.length()-1);


            //Your server URL
            URL obj = new URL(urlString);
            conn = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            conn.setRequestMethod(method.name());
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //Request Parameters you want to send
            // Send post request
            //conn.setDoOutput(true);// Should be part of code only for .Net web-services else no need for PHP
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + urlString);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            StringBuilder response = new StringBuilder();
            InputStream inStream;
            if (responseCode / 100 == 2) {
                inStream = conn.getInputStream();
            } else {
                inStream = conn.getErrorStream();
            }


            BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());

            return response.toString();

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

}
