package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

public class OAuthService {

    private static String GOOGLE_CLIENT_ID = "321071089338-6hlb9h02kql4g1op8nsbkbfpf46d28h7.apps.googleusercontent.com";
    private static String GOOGLE_CLIENT_SECRET = "GHdRHDIq9W3vk3jpYSDOQ5_I";
    private static String SPOTIFY_CLIENT_ID;
    private static String SPOTIFY_CLIENT_SECRET;
    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String SPOTIFY_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String REDIRECT_URL = "http://localhost:8080/api/v1/authorize";
    private String codeChallenge, codeVerifier;



    public OAuthService()  {

//        Properties properties = new Properties();
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream("application.properties");
//            properties.load(in);
//            in.close();
//            GOOGLE_CLIENT_ID = properties.getProperty("GOOGLE_CLIENT_ID");
//            GOOGLE_CLIENT_SECRET = properties.getProperty("GOOGLE_CLIENT_SECRET");
//            SPOTIFY_CLIENT_SECRET = properties.getProperty("SPOTIFY_CLIENT_SECRET");
//            SPOTIFY_CLIENT_SECRET = properties.getProperty("SPOTIFY_CLIENT_SECRET");

            generateAndSetCodes();

//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
    }

    public String generateAuthorizationCodeUrl(){
        String url = "";
        url = GOOGLE_AUTH_URL
                + "?client_id=" + GOOGLE_CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URL
                + "&response_type=code"
                + "&scope=https://www.googleapis.com/auth/youtube"
                + "&code_challenge=" + this.codeChallenge
                + "&code_challenge_method=S256"
                + "&state=123456789";
        return url;
    }

    /** This function generates code challenge and code verifier*/
    public void generateAndSetCodes(){
        try{
            SecureRandom sr = new SecureRandom();
            byte[] code = new byte[32];
            sr.nextBytes(code);
            this.codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);

            byte[] bytes = this.codeVerifier.getBytes("US-ASCII");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            byte[] diagest = md.digest();

            this.codeChallenge = org.apache.tomcat.util.codec.binary.Base64.encodeBase64URLSafeString(diagest);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static HttpResponse<String> getAccessToken(String code, String verifier)  {
        HttpResponse<String> response = null;
        try{
             response = Unirest.post("https://oauth2.googleapis.com/token")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body(
                            "grant_type=authorization_code"
                                    +"&client_id="+GOOGLE_CLIENT_ID
                                    +"&client_secret="+GOOGLE_CLIENT_SECRET
                                    +"&code_verifier="+verifier
                                    +"&code="+code
                                    +"&redirect_uri="+REDIRECT_URL
                    ).asString();
        } catch (UnirestException e){
            e.printStackTrace();
            System.exit(1);
        }
        return response;

    }

    public static String generateUUID(){
        return "asdbasdf";
    }

    public String getCodeVerifier() {
        return codeVerifier;
    }
}
