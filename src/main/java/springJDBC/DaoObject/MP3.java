package springJDBC.DaoObject;

import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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
        return mp3.getAuthor();
    }

    public static String getDurationWithMp3Spi(String name) throws UnsupportedAudioFileException, IOException {
        File file = new File(name);
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int mili = (int) (microseconds / 1000);
            int sec = (mili / 1000) % 60;
            int min = (mili / 1000) / 60;
            String songLong = min + ":" + sec;
            return songLong;
        } else {
            return null;
        }

    }





}
