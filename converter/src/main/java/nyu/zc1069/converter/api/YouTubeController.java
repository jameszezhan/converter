package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import nyu.zc1069.converter.service.OAuthService;
import nyu.zc1069.converter.service.YouTubeService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
//@CrossOrigin(origins = "http://localhost:8081")
//@CrossOrigin(origins = "http://127.0.0.1:8081")
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
    public String getAccessToken(
            @RequestParam("state") String state,
            @RequestParam("code") String code,
            @RequestParam("scope") String scope
    ){
        HttpResponse<String> accessToken = youTubeService.getAccessToken(state, code);
        System.out.println(accessToken.getBody());
        return "You can close this window now";
    }

    @RequestMapping("playlist")
    @GetMapping
    public String GetPlaylists(@RequestParam("state") String state){
        String response = youTubeService.getPlaylists(state);
        System.out.println(response);
        return response;
    }

    @RequestMapping("tracks")
    @PostMapping
    public String GetTracks(
            @RequestParam("state") String state,
            @RequestBody ArrayList<String> playlistIds
        ){
        String response = youTubeService.getTracks(state, playlistIds);
        System.out.println(response);
        return response;
    }

    @RequestMapping("verify")
    @PostMapping
    public Boolean VerifyLogIn(
            @RequestParam("state") String state
    ){
        String token = youTubeService.attemptGetAccessToken(state);
        if(token.equals("")){
            return false;
        }
        return true;
    }
}
