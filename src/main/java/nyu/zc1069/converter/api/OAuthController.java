package nyu.zc1069.converter.api;

import com.mashape.unirest.http.HttpResponse;
import nyu.zc1069.converter.service.OAuthService;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/authorize")
@RestController
public class OAuthController {
    private final OAuthService oAuthService;

    public OAuthController() {
        this.oAuthService = new OAuthService();
    }

    @PostMapping
    public String fetchAuthorizationUrl(){
        System.out.println("sdfsadfasdfasdf");
        String url = oAuthService.generateAuthorizationCodeUrl();
        return url;
    }

    @GetMapping
    public void getAccessToken(
            @RequestParam("state") String state,
            @RequestParam("code") String code,
            @RequestParam("scope") String scope
    ){
        System.out.println("sdfsadfasdfasdf");
        System.out.println(code);
        HttpResponse<String> accessToken = oAuthService.getAccessToken(code, this.oAuthService.getCodeVerifier());
        System.out.print(accessToken.getBody());
//        return accessToken;
    }
}



//http://localhost:40001/Callback?state=123456789&code=4%2F1gH0MkPMO0H1hauLujx3MXbRMG6m3VGeymLnsf2iHa_JhQzNCs1Hhznq2aVl-bLvanP8_uw8bwNjDnGD9lCzSKc&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutube#