package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import nyu.zc1069.converter.service.OAuthService;
import nyu.zc1069.converter.service.SpotifyService;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam("code") String code,
            @RequestParam("scope") String scope
    ){
        HttpResponse<String> accessToken = spotifyService.getAccessToken(state, code, this.spotifyService.getCodeVerifier());
    }
}
