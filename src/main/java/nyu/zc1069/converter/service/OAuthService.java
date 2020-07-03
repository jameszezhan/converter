package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

public class OAuthService {

    private String GOOGLE_CLIENT_ID;
    private String GOOGLE_CLIENT_SECRET;

    public OAuthService(String google_client_id)  {

        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("application.properties");
            properties.load(in);
            in.close();
            GOOGLE_CLIENT_ID = properties.getProperty("GOOGLE_CLIENT_ID");
            GOOGLE_CLIENT_SECRET = properties.getProperty("GOOGLE_CLIENT_SECRET");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);

        byte[] bytes = codeVerifier.getBytes("US-ASCII");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes, 0, bytes.length);
        byte[] diagest = md.digest();

        String challenge = org.apache.tomcat.util.codec.binary.Base64.encodeBase64URLSafeString(diagest);
    }

    public static String generateAuthorizationCodeUrl(String challenge){
        String url = "";
        url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "client_id=321071089338-6hlb9h02kql4g1op8nsbkbfpf46d28h7.apps.googleusercontent.com"
                + "redirect_uri=http://localhost:40001/Callback"
                + "response_type=code"
                + "scope=https://www.googleapis.com/auth/youtube"
                + "code_challenge=" + challenge
                + "code_challenge_method=S256"
                + "state=123456789";
        return url;
    }

    public static HttpResponse<String> getAccessToken(String code, String verifier) throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://oauth2.googleapis.com/token")
                .header("content-type", "application/x-www-form-urlencoded")
                .body(
                        "grant_type=authorization_code"
                                +"&client_id=321071089338-6hlb9h02kql4g1op8nsbkbfpf46d28h7.apps.googleusercontent.com"
                                +"&code_verifier="+verifier
                                +"code="+code
                                +"redirect_uri=http://localhost:40001/Callback"
                ).asString();
        return response;
    }
}
