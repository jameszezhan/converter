package nyu.zc1069.converter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nyu.zc1069.converter.model.Basetrack;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class YouTubeService extends OAuthService {
    public YouTubeService() {
        super("GOOGLE");
    }

    @PostConstruct
    public HashMap<String, String> getClientInfo() {
        if (this.clientInfo == null){
            this.clientInfo = new HashMap<>();
            this.clientInfo.put("CLIENT_ID", env.getGOOGLE_CLIENT_ID());
            this.clientInfo.put("CLIENT_SECRET", env.getGOOGLE_CLIENT_SECRET());
            this.clientInfo.put("SCOPE", env.getGOOGLE_SCOPE());
            this.clientInfo.put("AUTH_URL", env.getGOOGLE_AUTH_URL());
            this.clientInfo.put("TOKEN_URL", env.getGOOGLE_TOKEN_URL());
            this.clientInfo.put("REDIRECT_URL", env.getGOOGLE_REDIRECT_URL());
        }
        return this.clientInfo;
    }

    public String getPlaylists(String uuid){
        HttpResponse<String> response = null;
        String output = "";
        JSONObject body = new JSONObject();
        String accessToken = getTokenMap().get(uuid);
        try{
            response = Unirest.get("https://www.googleapis.com/youtube/v3/playlists")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json")
                    .queryString("part", "snippet")
                    .queryString("mine", "true")
                    .queryString("maxResults", "20")
                    .asString();

            JSONObject resBody = new JSONObject(response.getBody());
            JSONArray items = resBody.getJSONArray("items");

            /** Construct custom body return from api returns*/
            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                System.out.println(item);
                JSONObject snippet = item.getJSONObject("snippet");
                String playlistId = item.getString("id");
                String playlistTitle = (String) snippet.getString("title");
                String playlistDescription = (String) snippet.getString("description");
                String playlistPlatform = "youtube";
                String playlistType = "playlist";
                ArrayList<String> playlistTypeArtists = new ArrayList<>();
                Basetrack playlistBaseTrack = this.constructBaseTrack(
                        playlistId,
                        playlistTitle,
                        playlistPlatform,
                        playlistType,
                        playlistTypeArtists,
                        playlistDescription
                );
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String playlistJson = ow.writeValueAsString(playlistBaseTrack);
                body.put(playlistId, playlistJson);
            }

            /** add liked video option*/
            Basetrack likedListBaseTrack = this.constructBaseTrack(
                    "liked",
                    "Liked Videos",
                    "youtube",
                    "playlist",
                    null,
                    "Liked Videos"
            );
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String playlistJson = ow.writeValueAsString(likedListBaseTrack);
            body.put("liked", playlistJson);

            output = this.constructApiReturnContent(body);
        } catch (UnirestException | JsonProcessingException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return output;
    }

    public String getTracks(String uuid, ArrayList<String> playlistIds) {
        String output = "";
        JSONObject body = new JSONObject();
        ArrayList<Basetrack> allTracks = new ArrayList<>();
        for(String playlistId: playlistIds) {
            if(playlistId.equals("liked")){
                allTracks.addAll(getLikedVideos(uuid));
            }else{
                allTracks.addAll(getTracksFromId(uuid, playlistId));
            }
        }

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            output = ow.writeValueAsString(allTracks);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            System.exit(1);
        }

        return output;
    }

    public ArrayList<Basetrack> getTracksFromId(String uuid, String playlistId){
        HttpResponse<JsonNode> response = null;
        String accessToken = getTokenMap().get(uuid);
        ArrayList<Basetrack> tracksFromPlaylist = new ArrayList<Basetrack>();
        try{
            response = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json")
                    .queryString("part", "snippet,contentDetails")
                    .queryString("maxResults", "500")
                    .queryString("playlistId", playlistId)
                    .asJson();
            JSONObject resBody = new JSONObject(response.getBody());
            JSONArray items = resBody.getJSONObject("object").getJSONArray("items");

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                System.out.println(item);
                JSONObject snippet = item.getJSONObject("snippet");

                String trackId = item.getString("id");
                String trackDescription = snippet.getString("description");
                String trackTitle = snippet.getString("title");
                String playlistPlatform = "youtube";
                String playlistType = "playlist";
                ArrayList<String> playlistTypeArtists = new ArrayList<>();
                Basetrack playlistBaseTrack = this.constructBaseTrack(
                        trackId,
                        trackTitle,
                        playlistPlatform,
                        playlistType,
                        playlistTypeArtists,
                        trackDescription
                );
                tracksFromPlaylist.add(playlistBaseTrack);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return tracksFromPlaylist;
    }

    public ArrayList<Basetrack> getLikedVideos(String uuid){
        HttpResponse<String> response = null;
        ArrayList<Basetrack> likedTracks = new ArrayList<>();
        JSONObject body = new JSONObject();
        String accessToken = getTokenMap().get(uuid);
        try {
            response = Unirest.get("https://www.googleapis.com/youtube/v3/videos")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json")
                    .queryString("part", "snippet")
                    .queryString("mine", "true")
                    .queryString("myRating", "like")
                    .queryString("maxResults", "20")
                    .asString();

            JSONObject resBody = new JSONObject(response.getBody());
            JSONArray items = resBody.getJSONArray("items");

            /** Construct custom body return from api returns*/
            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                System.out.println(item);
                JSONObject snippet = item.getJSONObject("snippet");
                String playlistId = item.getString("id");
                String playlistTitle = (String) snippet.getString("title");
                String playlistDescription = snippet.getString("description");
                String playlistPlatform = "youtube";
                String playlistType = "playlist";
                ArrayList<String> playlistTypeArtists = new ArrayList<>();
                Basetrack likedBaseTrack = this.constructBaseTrack(
                        playlistId,
                        playlistTitle,
                        playlistPlatform,
                        playlistType,
                        playlistTypeArtists,
                        playlistDescription
                );
                likedTracks.add(likedBaseTrack);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return likedTracks;
    }
}
