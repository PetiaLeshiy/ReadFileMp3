package springJDBC.DAO;

import springJDBC.DaoObject.MP3;

import java.util.List;

public interface MP3DAO {
    void insert (MP3 mp3);
    void delete(String id);
    List<MP3> getMP3ByTag(String tag);
    List<MP3> showAllBase();






}
