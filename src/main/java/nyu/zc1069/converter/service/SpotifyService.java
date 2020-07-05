package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.boot.json.JsonParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class SpotifyService extends OAuthService{
    private static HashMap<String, String> clientInfo = null;
    private static HashMap<String, String> tokenMap = getTokenMap();

    public SpotifyService(String platform) {
        super(platform);
        clientInfo = getClientInfo();
    }

    public static ArrayList<HttpResponse<String>> searchTracks(ArrayList<String> trackTitles){
        setSelfToken();
        ArrayList<HttpResponse<String>> response = new ArrayList<HttpResponse<String>>();
        for(String trackTitle:trackTitles){
            response.add(searchTrack(trackTitle));
        }
        return response;
    }

    public static HttpResponse<String> searchTrack(String trackTitle){
        HttpResponse<String> response = null;
        try{
            response = Unirest.get("https://api.spotify.com/v1/search")
                    .header("Authorization", "Bearer " + tokenMap.get("self"))
                    .header("Accept", "application/json")
                    .queryString("q", URLEncoder.encode(trackTitle, "UTF-8"))
                    .queryString("limit", "5")
                    .queryString("type", "track")
                    .asString();
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return response;
    }


    public static void setSelfToken(){
        HttpResponse<String> response = null;
        try{
            response = Unirest.post("https://accounts.spotify.com/api/token")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body(
                            "grant_type=client_credentials"
                            +"&client_id="+clientInfo.get("CLIENT_ID")
                            +"&client_secret="+clientInfo.get("CLIENT_SECRET")
                    )
                    .asString();
            JSONObject body = new JSONObject(response.getBody());
            tokenMap.put("self", (String) body.get("access_token"));
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
