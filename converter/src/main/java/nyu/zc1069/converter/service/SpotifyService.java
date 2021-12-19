package nyu.zc1069.converter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nyu.zc1069.converter.model.Basetrack;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SpotifyService extends OAuthService{
    private HashMap<String, String> clientInfo = null;
    private HashMap<String, String> tokenMap = null;
    private HashMap<String, String> userIdMap = new HashMap<String, String>();

    public SpotifyService() {
        super("spotify");
        tokenMap = getTokenMap();
    }

    @PostConstruct
    public HashMap<String, String> getClientInfo() {
        if (this.clientInfo == null){
            this.clientInfo = new HashMap<>();
            this.clientInfo.put("CLIENT_ID", env.getSPOTIFY_CLIENT_ID());
            this.clientInfo.put("CLIENT_SECRET", env.getSPOTIFY_CLIENT_SECRET());
            this.clientInfo.put("SCOPE", env.getSPOTIFY_SCOPE());
            this.clientInfo.put("AUTH_URL", env.getSPOTIFY_AUTH_URL());
            this.clientInfo.put("TOKEN_URL", env.getSPOTIFY_TOKEN_URL());
            this.clientInfo.put("REDIRECT_URL", env.getSPOTIFY_REDIRECT_URL());
        }
        return this.clientInfo;
    }

    public String searchTracks(ArrayList<String> trackTitles){
        setSelfToken();
        String output = "";
        JSONObject body = new JSONObject();
        for(String trackTitle:trackTitles){
            body.put(trackTitle, searchTrack(trackTitle));
        }
        output = this.constructApiReturnContent(body);
        return output;
    }

    public String searchTrack(String trackTitle){
        HttpResponse<String> response = null;
        String optionsJson = "";
        ArrayList<Basetrack> options = new ArrayList<Basetrack>();
        try{
            response = Unirest.get("https://api.spotify.com/v1/search")
                    .header("Authorization", "Bearer " + this.tokenMap.get("self"))
                    .header("Accept", "application/json")
                    .queryString("q", URLEncoder.encode(trackTitle, "UTF-8"))
                    .queryString("limit", "5")
                    .queryString("type", "track")
                    .asString();
            JSONObject resBody = new JSONObject(response.getBody());
            JSONArray items = resBody.getJSONObject("tracks").getJSONArray("items");

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                String id = item.getString("uri");
                String title = item.getString("name");
                String platform = "spotify";
                String type = "tracks";
                ArrayList<String> artists = new ArrayList<>();
                JSONArray artistsObj = item.getJSONArray("artists");
                for(int j = 0; j < artistsObj.length(); j++){
                    JSONObject artist = artistsObj.getJSONObject(j);
                    artists.add(artist.getString("name"));
                }
                Basetrack basetrack = this.constructBaseTrack(
                        id,
                        title,
                        platform,
                        type,
                        artists,
                        ""
                );
                options.add(basetrack);

            }
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            optionsJson = ow.writeValueAsString(options);

        } catch (UnirestException | UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return optionsJson;
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

    public String createPlaylist(String uuid, String playlistName){
        if (userIdMap.get(uuid) == null){
            fetchAndSetUserId(uuid);
        }

        String url = "https://api.spotify.com/v1/users/" + userIdMap.get(uuid) + "/playlists";
        String accessToken = tokenMap.get(uuid);
        String playlistId = null;
        HttpResponse<String> response = null;
        try{
            JSONObject payload = new JSONObject();
            payload.put("name", playlistName);
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
