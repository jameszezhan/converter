package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nyu.zc1069.converter.model.Basetrack;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class OAuthService {
    private HashMap<String, String> clientInfo;
    private HashMap<String, String> tokenMap = new HashMap<String, String>();
    private HashMap<String, String> verifierMap = new HashMap<String, String>();

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
                put("CLIENT_ID", "4e553333356e4435a1fcfc3a2ef30562");
                put("CLIENT_SECRET", "e256025d393d4c5c84a8b124acf9b404");
                put("SCOPE", "user-read-email playlist-modify-private");
                put("AUTH_URL", "https://accounts.spotify.com/authorize");
                put("TOKEN_URL", "https://accounts.spotify.com/api/token");
                put("REDIRECT_URL", "http://localhost:8080/api/v1/spotify");
            }};
        }
    }

    /** platformPrefix can be GOOGLE or SPOTIFY*/
    public String generateAuthorizationCodeUrl(){
        String url = "";
        UUID uuid = UUID.randomUUID();
        String codeChallenge = generateAndSetCodes(uuid.toString());
        try{
            url = clientInfo.get("AUTH_URL")
                    + "?client_id=" + clientInfo.get("CLIENT_ID")
                    + "&redirect_uri=" + clientInfo.get("REDIRECT_URL")
                    + "&response_type=code"
                    + "&scope=" + URLEncoder.encode(clientInfo.get("SCOPE"), "UTF-8")
                    + "&code_challenge=" + codeChallenge
                    + "&code_challenge_method=S256"
                    + "&state="+uuid.toString();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            System.exit(1);
        }
        return url;
    }

    /** This function generates code challenge and code verifier*/
    public String generateAndSetCodes(String uuid){
        String codeChallenge = null;
        try{
            SecureRandom sr = new SecureRandom();
            byte[] code = new byte[32];
            sr.nextBytes(code);
            String codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);

            byte[] bytes = codeVerifier.getBytes("US-ASCII");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            byte[] digest = md.digest();

            codeChallenge = org.apache.tomcat.util.codec.binary.Base64.encodeBase64URLSafeString(digest);
            verifierMap.put(uuid, codeVerifier);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e){
            e.printStackTrace();
            System.exit(1);
        }
        return codeChallenge;
    }

    public HttpResponse<String> getAccessToken(String state, String code)  {
        HttpResponse<String> response = null;

        try{
             response = Unirest.post(this.clientInfo.get("TOKEN_URL"))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body(
                            "grant_type=authorization_code"
                                    +"&client_id="+this.clientInfo.get("CLIENT_ID")
                                    +"&client_secret="+this.clientInfo.get("CLIENT_SECRET")
                                    +"&code_verifier="+this.verifierMap.get(state)
                                    +"&code="+code
                                    +"&redirect_uri="+this.clientInfo.get("REDIRECT_URL")
                    ).asString();
            JSONObject body = new JSONObject(response.getBody());
            if (body.get("access_token") != null){
                if (response.getStatus() == 200){
                    tokenMap.put(state, (String) body.get("access_token"));
                }
            }else{
                tokenMap.put(state, "body");
            }
            System.out.print(tokenMap);
        } catch (UnirestException e){
            e.printStackTrace();
            System.exit(1);
        }
        return response;
    }

    public HashMap<String, String> getClientInfo() {
        return this.clientInfo;
    }

    public HashMap<String, String> getTokenMap() {
        return this.tokenMap;
    }

    public Basetrack constructBaseTrack(
            String id,
            String name,
            String platform,
            String type,
            ArrayList<String> artists
    ){
        String displayTitle = name;
        Basetrack baseTrack = new Basetrack(id, name, platform, type, artists, name);
        return baseTrack;
    }

    public String constructApiReturnContent(JSONObject body){
        return body.toString();
    }
}
