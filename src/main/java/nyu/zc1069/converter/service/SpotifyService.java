package nyu.zc1069.converter.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class SpotifyService extends OAuthService{
    private HashMap<String, String> clientInfo = null;
    private HashMap<String, String> tokenMap = null;
    private HashMap<String, String> userIdMap = new HashMap<String, String>();

    public SpotifyService(String platform) {
        super(platform);
        clientInfo = getClientInfo();
        tokenMap = getTokenMap();
    }

    public ArrayList<HttpResponse<String>> searchTracks(ArrayList<String> trackTitles){
        setSelfToken();
        ArrayList<HttpResponse<String>> response = new ArrayList<HttpResponse<String>>();
        for(String trackTitle:trackTitles){
            response.add(searchTrack(trackTitle));
        }
        return response;
    }

    public HttpResponse<String> searchTrack(String trackTitle){
        HttpResponse<String> response = null;
        try{
            response = Unirest.get("https://api.spotify.com/v1/search")
                    .header("Authorization", "Bearer " + this.tokenMap.get("self"))
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

    public void setSelfToken(){
        HttpResponse<String> response = null;
        try{
            response = Unirest.post("https://accounts.spotify.com/api/token")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body(
                            "grant_type=client_credentials"
                            +"&client_id="+this.clientInfo.get("CLIENT_ID")
                            +"&client_secret="+this.clientInfo.get("CLIENT_SECRET")
                    )
                    .asString();
            JSONObject body = new JSONObject(response.getBody());
            this.tokenMap.put("self", (String) body.get("access_token"));
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void fetchAndSetUserId(String uuid){
        String accessToken = tokenMap.get(uuid);
        HttpResponse<String> response = null;
        try{
            response = Unirest.get("https://api.spotify.com/v1/me")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer "+accessToken)
                    .asString();
            JSONObject body = new JSONObject(response.getBody());
            System.out.println(body);
            userIdMap.put(uuid, (String) body.get("id"));
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String createPlaylist(String uuid){
        if (userIdMap.get(uuid) == null){
            fetchAndSetUserId(uuid);
        }

        String url = "https://api.spotify.com/v1/users/" + userIdMap.get(uuid) + "/playlists";
        String accessToken = tokenMap.get(uuid);
        String playlistId = null;
        HttpResponse<String> response = null;
        try{
            JSONObject payload = new JSONObject();
            payload.put("name", "mig2");
            payload.put("public", false);
            response = Unirest.post(url)
                    .header("content-type", "application/json")
                    .header("Authorization", "Bearer "+accessToken)
                    .body(payload)
                    .asString();
            JSONObject body = new JSONObject(response.getBody());
            playlistId = (String) body.get("id");
            System.out.println(body);

        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return playlistId;
    }

    public void addTracksToPlaylist(String playlistId, String uuid, ArrayList<String> trackIds){
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String accessToken = tokenMap.get(uuid);
        HttpResponse<String> response = null;
        try{
            JSONObject payload = new JSONObject();
            payload.put("uris", trackIds);
            response = Unirest.post(url)
                    .header("content-type", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .body(payload)
                    .asString();
            JSONObject body = new JSONObject(response.getBody());
            System.out.println("\n");
            System.out.println(body);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
