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

import java.util.ArrayList;

public class YouTubeService extends OAuthService {
    public YouTubeService(String platform) {
        super(platform);
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
                String playlistPlatform = "youtube";
                String playlistType = "playlist";
                ArrayList<String> playlistTypeArtists = new ArrayList<>();
                Basetrack playlistBaseTrack = this.constructBaseTrack(
                        playlistId,
                        playlistTitle,
                        playlistPlatform,
                        playlistType,
                        playlistTypeArtists
                );
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String playlistJson = ow.writeValueAsString(playlistBaseTrack);
                body.put(playlistId, playlistJson);
            }

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
            allTracks.addAll(getTracksFromId(uuid, playlistId));
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
                    .queryString("part", "snippet")
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
                String trackTitle = snippet.getString("title");
                String playlistPlatform = "youtube";
                String playlistType = "playlist";
                ArrayList<String> playlistTypeArtists = new ArrayList<>();
                Basetrack playlistBaseTrack = this.constructBaseTrack(
                        trackId,
                        trackTitle,
                        playlistPlatform,
                        playlistType,
                        playlistTypeArtists
                );
                tracksFromPlaylist.add(playlistBaseTrack);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return tracksFromPlaylist;
    }


}
