package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nyu.zc1069.converter.model.Basetrack;
import org.json.JSONObject;
import org.springframework.context.NoSuchMessageException;

import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class OAuthService {
    private HashMap<String, String> clientInfo;
    private HashMap<String, String> tokenMap = new HashMap<String, String>();
    private HashMap<String, String> verifierMap = new HashMap<String, String>();

    public OAuthService(String platform)  {
        // read properties from config file
        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("Application.properties");
            properties.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientInfo = new HashMap<>(){{
            put("CLIENT_ID", properties.getProperty(platform + "_CLIENT_ID"));
            put("CLIENT_SECRET", properties.getProperty(platform + "_CLIENT_SECRET"));
            put("SCOPE", properties.getProperty(platform + "_SCOPE"));
            put("AUTH_URL", properties.getProperty(platform + "_AUTH_URL"));
            put("TOKEN_URL", properties.getProperty(platform + "_TOKEN_URL"));
            put("REDIRECT_URL", properties.getProperty(platform + "_REDIRECT_URL"));

        }};

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
            ArrayList<String> artists,
            String description
    ){
        String artistsStr = "";
        if(artists != null){
            int bound = Math.min(artists.size(), 3);
            for(int i = 0; i < bound; i ++){
                if (artists.get(i) != null){
                    artistsStr = artistsStr + " " + artists.get(i);
                }else{
                    break;
                }
            }
        }
        String displayTitle = artistsStr.length() > 0 ? "<strong>" + name + "</strong> by " + artistsStr : name;
        return new Basetrack(id, name, platform, type, artists, displayTitle, description);
    }

    public String constructApiReturnContent(JSONObject body){
        return body.toString();
    }

    public String attemptGetAccessToken(String uuid){
        if (this.tokenMap.containsKey(uuid)){
            return this.tokenMap.get(uuid);
        } else{
            return "";
        }
    }
}
