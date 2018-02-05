package com.kingkingyyk.uhunter.utility;

import com.kingkingyyk.uhunter.uhunt.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class Utility {

    private static String readStringFromURL(String url) throws Exception {
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestProperty("Connection", "close");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        StringBuilder sb=new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String s;
            while ((s=br.readLine())!=null) {
                sb.append(s);
                sb.append("\n");
            }
        } finally { conn.disconnect();}
        return sb.toString().trim();
    }

    public static int username2id (String baseURL, String username) {
        try {
            return Integer.parseInt(Utility.readStringFromURL(baseURL+"/api/uname2uid/"+username));
        } catch (Exception e) {}
        return -1;
    }

    public static User getUserById (String baseURL, int id) {
        try {
            return User.fromJSONObject(new JSONArray(Utility.readStringFromURL(baseURL+"/api/ranklist/"+id+"/0/0")).getJSONObject(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
