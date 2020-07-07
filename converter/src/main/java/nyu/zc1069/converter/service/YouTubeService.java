package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nyu.zc1069.converter.model.Basetrack;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class YouTubeService extends OAuthService {
    public YouTubeService(String platform) {
        super(platform);
    }

    public HttpResponse<String> getPlaylists(String uuid){
        HttpResponse<String> response = null;
        String accessToken = getTokenMap().get(uuid);
        try{
            response = Unirest.get("https://www.googleapis.com/youtube/v3/playlists")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json")
                    .queryString("part", "snippet")
                    .queryString("mine", "true")
                    .queryString("maxResults", "20")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return response;
    }

    public JSONObject getTracks(String uuid, ArrayList<String> playlistIds){
        JSONObject tracks = new JSONObject();
        for(String playlistId: playlistIds){
            tracks.put(playlistId, getTracksFromId(uuid, playlistId).getBody());
        }
        return tracks;
    }

    public HttpResponse<JsonNode> getTracksFromId(String uuid, String playlistId){
        HttpResponse<JsonNode> response = null;
        String accessToken = getTokenMap().get(uuid);
        System.out.println(accessToken);
        try{
            response = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json")
                    .queryString("part", "snippet")
                    .queryString("maxResults", "500")
                    .queryString("playlistId", playlistId)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return response;
    }


}
