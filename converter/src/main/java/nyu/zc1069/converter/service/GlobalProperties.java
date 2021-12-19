package nyu.zc1069.converter.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class GlobalProperties {
    public String GOOGLE_CLIENT_ID;
    public String GOOGLE_CLIENT_SECRET;
    public String GOOGLE_SCOPE;

    public String getGOOGLE_CLIENT_ID() {
        return GOOGLE_CLIENT_ID;
    }

    public void setGOOGLE_CLIENT_ID(String GOOGLE_CLIENT_ID) {
        this.GOOGLE_CLIENT_ID = GOOGLE_CLIENT_ID;
    }

    public String getGOOGLE_CLIENT_SECRET() {
        return GOOGLE_CLIENT_SECRET;
    }

    public void setGOOGLE_CLIENT_SECRET(String GOOGLE_CLIENT_SECRET) {
        this.GOOGLE_CLIENT_SECRET = GOOGLE_CLIENT_SECRET;
    }

    public String getGOOGLE_SCOPE() {
        return GOOGLE_SCOPE;
    }

    public void setGOOGLE_SCOPE(String GOOGLE_SCOPE) {
        this.GOOGLE_SCOPE = GOOGLE_SCOPE;
    }

    public String getGOOGLE_AUTH_URL() {
        return GOOGLE_AUTH_URL;
    }

    public void setGOOGLE_AUTH_URL(String GOOGLE_AUTH_URL) {
        this.GOOGLE_AUTH_URL = GOOGLE_AUTH_URL;
    }

    public String getGOOGLE_TOKEN_URL() {
        return GOOGLE_TOKEN_URL;
    }

    public void setGOOGLE_TOKEN_URL(String GOOGLE_TOKEN_URL) {
        this.GOOGLE_TOKEN_URL = GOOGLE_TOKEN_URL;
    }

    public String getGOOGLE_REDIRECT_URL() {
        return GOOGLE_REDIRECT_URL;
    }

    public void setGOOGLE_REDIRECT_URL(String GOOGLE_REDIRECT_URL) {
        this.GOOGLE_REDIRECT_URL = GOOGLE_REDIRECT_URL;
    }

    public String getSPOTIFY_CLIENT_ID() {
        return SPOTIFY_CLIENT_ID;
    }

    public void setSPOTIFY_CLIENT_ID(String SPOTIFY_CLIENT_ID) {
        this.SPOTIFY_CLIENT_ID = SPOTIFY_CLIENT_ID;
    }

    public String getSPOTIFY_CLIENT_SECRET() {
        return SPOTIFY_CLIENT_SECRET;
    }

    public void setSPOTIFY_CLIENT_SECRET(String SPOTIFY_CLIENT_SECRET) {
        this.SPOTIFY_CLIENT_SECRET = SPOTIFY_CLIENT_SECRET;
    }

    public String getSPOTIFY_SCOPE() {
        return SPOTIFY_SCOPE;
    }

    public void setSPOTIFY_SCOPE(String SPOTIFY_SCOPE) {
        this.SPOTIFY_SCOPE = SPOTIFY_SCOPE;
    }

    public String getSPOTIFY_AUTH_URL() {
        return SPOTIFY_AUTH_URL;
    }

    public void setSPOTIFY_AUTH_URL(String SPOTIFY_AUTH_URL) {
        this.SPOTIFY_AUTH_URL = SPOTIFY_AUTH_URL;
    }

    public String getSPOTIFY_TOKEN_URL() {
        return SPOTIFY_TOKEN_URL;
    }

    public void setSPOTIFY_TOKEN_URL(String SPOTIFY_TOKEN_URL) {
        this.SPOTIFY_TOKEN_URL = SPOTIFY_TOKEN_URL;
    }

    public String getSPOTIFY_REDIRECT_URL() {
        return SPOTIFY_REDIRECT_URL;
    }

    public void setSPOTIFY_REDIRECT_URL(String SPOTIFY_REDIRECT_URL) {
        this.SPOTIFY_REDIRECT_URL = SPOTIFY_REDIRECT_URL;
    }

    public String GOOGLE_AUTH_URL;
    public String GOOGLE_TOKEN_URL;
    public String GOOGLE_REDIRECT_URL;
    public String SPOTIFY_CLIENT_ID;
    public String SPOTIFY_CLIENT_SECRET;
    public String SPOTIFY_SCOPE;
    public String SPOTIFY_AUTH_URL;
    public String SPOTIFY_TOKEN_URL;
    public String SPOTIFY_REDIRECT_URL;


}
