package com.qsoft.pilotproject.accountmanager;

import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the comminication with Parse.com
 *
 * User: udinic
 * Date: 3/27/13
 * Time: 3:30 AM
 */
public class ParseComServerAuthenticate implements ServerAuthenticate{

    public static String stringToMD5(String message){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        md.update(message.getBytes());
        byte byteData[] = md.digest();
        // convert the byte to hex format method
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public String userSignIn(String user, String pass, String authType) throws Exception {

        String passMD5 = stringToMD5(pass);
        Log.d("udini", "userSignIn");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("username", user));
        postParams.add(new BasicNameValuePair("password", passMD5));
        postParams.add(new BasicNameValuePair("grant_type", "password"));
        postParams.add(new BasicNameValuePair("client_id", "123456789"));
        postParams.add(new BasicNameValuePair("type", "password"));
        postParams.add(new BasicNameValuePair("email", "thanhtran@gmail.com"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
        httpPost.setEntity(entity);

        String authtoken = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);

            if (response.getStatusLine().getStatusCode() != 200) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
            }
            if (jsonObject.has("code"))
            {
                throw new Exception( "Invalid username and password combination");
            }
            User loggedUser = new Gson().fromJson(responseString, User.class);
            authtoken = loggedUser.sessionToken;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return authtoken;
    }


    private class ParseComError implements Serializable {
        int code;
        String error;
    }
    private class User implements Serializable {

        private String firstName;
        private String lastName;
        private String username;
        private String phone;
        private String objectId;
        public String sessionToken;
        private String gravatarId;
        private String avatarUrl;


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public void setGravatarId(String gravatarId) {
            this.gravatarId = gravatarId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
