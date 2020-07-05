package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import nyu.zc1069.converter.service.OAuthService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/spotify")
@RestController
public class SpotifyController {
    private final OAuthService oAuthService;

    public SpotifyController() {
        this.oAuthService = new OAuthService("SPOTIFY");
    }

    @PostMapping
    public String fetchAuthorizationUrl(){
        String url = oAuthService.generateAuthorizationCodeUrl();
        return url;
    }

    @GetMapping
    public void getAccessToken(
            @RequestParam("state") String state,
            @RequestParam("code") String code,
            @RequestParam("scope") String scope
    ){
        HttpResponse<String> accessToken = oAuthService.getAccessToken(state, code, this.oAuthService.getCodeVerifier());
    }
}
