package asiantech.internship.summer.broadcastandservice.model;

public class Song {
    private Long id;
    private String name;
    private String artist;
    private String path;
    private long duration;

    public Song(Long id, String name, String artist,String path,long duration) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.path = path;
        this.duration = duration;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public long getDuration() {
        return duration;
    }
}

