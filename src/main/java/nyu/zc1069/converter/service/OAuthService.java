package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class OAuthService {
    private static HashMap<String, String> clientInfo = null;
    private String codeChallenge, codeVerifier;
    private static HashMap<String, String> tokenMap = new HashMap<String, String>();


    public OAuthService(String platform)  {
        if (platform == "GOOGLE"){
            clientInfo = new HashMap<String, String>(){{
                put("CLIENT_ID", "321071089338-6hlb9h02kql4g1op8nsbkbfpf46d28h7.apps.googleusercontent.com");
                put("CLIENT_SECRET", "GHdRHDIq9W3vk3jpYSDOQ5_I");
                put("SCOPE", "https://www.googleapis.com/auth/youtube.readonly");
                put("AUTH_URL", "https://accounts.google.com/o/oauth2/v2/auth");
                put("TOKEN_URL", "https://oauth2.googleapis.com/token");
                put("REDIRECT_URL", "http://localhost:8080/api/v1/youtube");
            }};
        }else{
            clientInfo = new HashMap<String, String>(){{
                put("CLIENT_ID", "");
                put("CLIENT_SECRET", "");
                put("SCOPE", "");
                put("AUTH_URL", "");
                put("TOKEN_URL", "");
                put("REDIRECT_URL", "http://localhost:8080/api/v1/spotify");
            }};
        }
        generateAndSetCodes();
    }

    /** platformPrefix can be GOOGLE or SPOTIFY*/
    public String generateAuthorizationCodeUrl(){
        String url = "";
        UUID uuid = UUID.randomUUID();

        url = clientInfo.get("AUTH_URL")
                + "?client_id=" + clientInfo.get("CLIENT_ID")
                + "&redirect_uri=" + clientInfo.get("REDIRECT_URL")
                + "&response_type=code"
                + "&scope=" + clientInfo.get("SCOPE")
                + "&code_challenge=" + this.codeChallenge
                + "&code_challenge_method=S256"
                + "&state="+uuid.toString();
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

    public static HttpResponse<String> getAccessToken(String state, String code, String verifier)  {
        HttpResponse<String> response = null;

        try{
             response = Unirest.post(clientInfo.get("TOKEN_URL"))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body(
                            "grant_type=authorization_code"
                                    +"&client_id="+clientInfo.get("CLIENT_ID")
                                    +"&client_secret="+clientInfo.get("CLIENT_SECRET")
                                    +"&code_verifier="+verifier
                                    +"&code="+code
                                    +"&redirect_uri="+clientInfo.get("REDIRECT_URL")
                    ).asString();

             if (response.getStatus() == 200){
                 tokenMap.put(state, response.getBody());
             }
             System.out.print(tokenMap);
        } catch (UnirestException e){
            e.printStackTrace();
            System.exit(1);
        }
        return response;

    }

    public String getCodeVerifier() {
        return codeVerifier;
    }



}
