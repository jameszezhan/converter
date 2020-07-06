package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import nyu.zc1069.converter.service.OAuthService;
import nyu.zc1069.converter.service.SpotifyService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public void getAccessToken(
            @RequestParam("state") String state,
            @RequestParam("code") String code
    ){
        HttpResponse<String> accessToken = spotifyService.getAccessToken(state, code);
        System.out.println(accessToken.getBody());
    }

    @RequestMapping("search")
    @GetMapping
    public ArrayList<String> searchTracks(
            @RequestBody ArrayList<String> trackTitles
        ){
        ArrayList<String> response = new ArrayList<String>();
        ArrayList<HttpResponse<String>> tracksOptions = spotifyService.searchTracks(trackTitles);
        for(HttpResponse<String> trackOptions: tracksOptions){
            response.add(trackOptions.getBody());
        }
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
