package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import nyu.zc1069.converter.service.OAuthService;
import nyu.zc1069.converter.service.YouTubeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("api/v1/youtube")
@RestController
public class YouTubeController {
    private final YouTubeService youTubeService;

    public YouTubeController() {
        this.youTubeService = new YouTubeService("GOOGLE");
    }

    @PostMapping
    public String fetchAuthorizationUrl(){
        String url = youTubeService.generateAuthorizationCodeUrl();
        return url;
    }

    @GetMapping
    public void getAccessToken(
            @RequestParam("state") String state,
            @RequestParam("code") String code,
            @RequestParam("scope") String scope
    ){
        HttpResponse<String> accessToken = youTubeService.getAccessToken(state, code);
        System.out.println(accessToken.getBody());
    }

    @RequestMapping("playlist")
    @GetMapping
    public String GetPlaylists(@RequestParam("state") String state){
        HttpResponse<String> playlists = youTubeService.getPlaylists(state);

        System.out.println(playlists.getStatus());
        System.out.println(playlists.getBody());

        return playlists.getBody();
    }

    @RequestMapping("tracks")
    @GetMapping
    public ArrayList<String> GetTracks(
            @RequestParam("state") String state,
            @RequestBody ArrayList<String> playlistIds
        ){
        ArrayList<String> response = new ArrayList<String>();

        ArrayList<HttpResponse<String>> playlists = youTubeService.getTracks(state, playlistIds);

        for(HttpResponse<String> playlist : playlists){
            response.add(playlist.getBody());
        }
        return response;
    }
}