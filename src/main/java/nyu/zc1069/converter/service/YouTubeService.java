package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nyu.zc1069.converter.model.Basetrack;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;

import java.util.ArrayList;
import java.util.HashMap;

public class YouTubeService extends OAuthService {
    private static HashMap<String, String> clientInfo = getClientInfo();
    private static HashMap<String, String> tokenMap = getTokenMap();

    public YouTubeService(String platform) {
        super(platform);
    }

    public static HttpResponse<String> getPlaylists(String uuid){
        HttpResponse<String> response = null;
        String acessToken = "ya29.a0AfH6SMAGUDulm3Up_rJDbtvVfrAFQuDurMAOXnA1-v4DgRUotMECL5RjJBRraSusABnBUTQLMyV4uezvVgpwdPF7nBNMjC-7rs7eVrS9UzhIV-rAQN9jKsipz119J6OaavI-0cLNqpmqwJ8Cw8iu00XONUK4WeWF9C4";
        try{
            response = Unirest.get("https://www.googleapis.com/youtube/v3/playlists")
                    .header("Authorization", "Bearer " + acessToken)
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

    public static ArrayList<HttpResponse<String>> getTracks(String uuid, ArrayList<String> playlistIds){
        ArrayList<HttpResponse<String>> tracks = new ArrayList<HttpResponse<String>>();
        for(String playlistId: playlistIds){
            tracks.add(getTracksFromId(uuid, playlistId));
        }
        return tracks;
    }

    public static HttpResponse<String> getTracksFromId(String uuid, String playlistId){
        HttpResponse<String> response = null;
        String acessToken = "ya29.a0AfH6SMAGUDulm3Up_rJDbtvVfrAFQuDurMAOXnA1-v4DgRUotMECL5RjJBRraSusABnBUTQLMyV4uezvVgpwdPF7nBNMjC-7rs7eVrS9UzhIV-rAQN9jKsipz119J6OaavI-0cLNqpmqwJ8Cw8iu00XONUK4WeWF9C4";
        try{
            response = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                    .header("Authorization", "Bearer " + acessToken)
                    .header("Accept", "application/json")
                    .queryString("part", "snippet, contentDetails")
                    .queryString("maxResults", "500")
                    .queryString("playlistId", playlistId)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return response;
    }


}
