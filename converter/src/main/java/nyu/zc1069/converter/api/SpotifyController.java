package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import nyu.zc1069.converter.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//@CrossOrigin(origins = "http://127.0.0.1:8081")
//@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("api/v1/spotify")
@RestController
public class SpotifyController {
    private SpotifyService spotifyService;

    @Autowired
    public void setService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @PostMapping
    public String fetchAuthorizationUrl(){
        String url = spotifyService.generateAuthorizationCodeUrl();
        return url;
    }

    @GetMapping
    public String getAccessToken(
            @RequestParam("state") String state,
            @RequestParam("code") String code
    ){
        HttpResponse<String> accessToken = spotifyService.getAccessToken(state, code);
        System.out.println(accessToken.getBody());
        return "You can close this window now";
    }

    @RequestMapping("search")
    @PostMapping
    public String searchTracks(
            @RequestBody ArrayList<String> trackTitles
        ){
        String response = spotifyService.searchTracks(trackTitles);

        return response;
    }

    @RequestMapping("migrate")
    @PostMapping
    public String migrate(
            @RequestBody ArrayList<String> trackIds,
            @RequestParam("state") String state,
            @RequestParam("name") String playlistName
    ){
        String playlistId = spotifyService.createPlaylist(state, playlistName);
        spotifyService.addTracksToPlaylist(playlistId, state, trackIds);
        return playlistId;
    }

    @RequestMapping("verify")
    @PostMapping
    public Boolean VerifyLogIn(
            @RequestParam("state") String state
    ){
        String token = spotifyService.attemptGetAccessToken(state);
        if(token.equals("")){
            return false;
        }
        return true;
    }
}
