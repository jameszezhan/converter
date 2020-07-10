package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import nyu.zc1069.converter.service.OAuthService;
import nyu.zc1069.converter.service.SpotifyService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("api/v1/spotify")
@RestController
public class SpotifyController {
    private final SpotifyService spotifyService;

    public SpotifyController() {
        this.spotifyService = new SpotifyService("SPOTIFY");
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
            @RequestParam("state") String state
    ){
        String playlistId = spotifyService.createPlaylist(state);
        spotifyService.addTracksToPlaylist(playlistId, state, trackIds);
        return playlistId;
    }
}
