package springJDBC.DaoObject;

public class MP3 {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String author;
    private String songName;
    private String songLength;




    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }

    public MP3() {

    }

    public MP3(String id, String author, String songName, String songLength) {
        this.author = author;
        this.songName = songName;
        this.songLength = songLength;
        this.id = id;
    }

    public MP3(String author, String songName, String songLength) {

        this.author = author;
        this.songName = songName;
        this.songLength = songLength;
    }



    public String replaceName(MP3 mp3){
        String[] name = mp3.getAuthor().split(" ");
        if (name.length == 2){
        StringBuilder newName = new StringBuilder(name[1]);
        newName.append(" ");
        newName.append(name[0]);
        return newName.toString();}
        return mp3.getAuthor();}
}
