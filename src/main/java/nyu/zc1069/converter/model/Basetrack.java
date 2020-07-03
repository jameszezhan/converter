package nyu.zc1069.converter.model;

import java.util.ArrayList;

public class Basetrack {
    private final String id;
    private String name;
    private final String platform;
    private final String type;
    private final ArrayList<String> artists;

    public Basetrack(String id, String name, String platform, String type, ArrayList<String> artists) {
        this.id = id;
        this.name = name;
        this.platform = platform;
        this.type = type;
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getArtists() {
        return artists;
    }
}
