package springJDBC.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import springJDBC.DAO.MP3DAO;
import springJDBC.DaoObject.MP3;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("mySqlDAO")
public class MySqlDAO implements MP3DAO {

    private JdbcTemplate jdbcTemplate;

@Autowired
    public void setDataSource (DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


 private Integer findThisAuthor(String name) {
    int number;
     String sqlGetAuthor = "Select authorNumber FROM authorTable WHERE author = ? ";
    try {
        number = jdbcTemplate.queryForObject(sqlGetAuthor, new Object[] {name}, Integer.class);
    return number;
    }
    catch (EmptyResultDataAccessException e)
         {
    return null;
     }
  }

    @Override
    public void insert(MP3 mp3) {
        String author = mp3.getAuthor();
        String authorReplaceName = mp3.replaceName(mp3);
        String sqlAddAuthor = "INSERT INTO authorTable (author) VALUES (?)";

        Integer number = findThisAuthor(author);
       if (number == null) {
           number = findThisAuthor(authorReplaceName);
       }
       if (number == null) {
           jdbcTemplate.update(sqlAddAuthor, new Object[] {author});
       }
       String sqlGetAuthor = "Select authorNumber FROM authorTable WHERE author = ? ";
        try {
            number = jdbcTemplate.queryForObject(sqlGetAuthor, new Object[]{author}, Integer.class);
        }
        catch(EmptyResultDataAccessException e)
        {number = jdbcTemplate.queryForObject(sqlGetAuthor, new Object[]{authorReplaceName}, Integer.class);}
        String sql = "INSERT INTO SongTable (author, songName, songLength) VALUES (?, ?, ?)";
       jdbcTemplate.update(sql, new Object[] {number, mp3.getSongName(), mp3.getSongLength()});
        System.out.println(number + " " + mp3.getSongName() + " " + mp3.getSongLength());
    }

    public void delete(String id) {
    String sql = "DELETE FROM songTable WHERE id = ?";
    int idInt = Integer.parseInt(id);
        System.out.println(jdbcTemplate.update(sql, idInt));
    }

    @Override
    public List<MP3> getMP3ByTag(String tag) {
    String sql = "SELECT SongTable.id, SongTable.songName, SongTable.songLength, AuthorTable.author FROM  SongTable inner JOIN AuthorTable ON SongTable.author = AuthorTable.authorNumber WHERE AuthorTable.author LIKE ? OR SongTable.SongName LIKE ?";
    final List<MP3> result = jdbcTemplate.query(sql, new Object[]{tag, tag}, new RowMapper<MP3>() {
        @Override
        public MP3 mapRow(ResultSet resultSet, int i) throws SQLException {
            return new MP3(resultSet.getString("SongTable.id"), resultSet.getString("AuthorTable.author"), resultSet.getString("SongTable.songName"), resultSet.getString("SongTable.songLength"));
        }
    });
        return result;
    }

    @Override
    public List<MP3> showAllBase() {
        String sql = "SELECT SongTable.id, SongTable.songName, SongTable.songLength, AuthorTable.author FROM SongTable INNER JOIN AuthorTable ON SongTable.author = AuthorTable.authorNumber ORDER BY SongTable.id";
    final List<MP3> result = jdbcTemplate.query(sql, new RowMapper<MP3>() {
        @Override
        public MP3 mapRow(ResultSet resultSet, int i) throws SQLException {
            return new MP3(resultSet.getString("SongTable.id"), resultSet.getString("AuthorTable.author"), resultSet.getString("SongTable.songName"), resultSet.getString("SongTable.songLength"));
        }
    });
    return result;
}


}
