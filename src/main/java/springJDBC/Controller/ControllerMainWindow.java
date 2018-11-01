package springJDBC.Controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import springJDBC.DaoObject.MP3;
import springJDBC.Impl.MySqlDAO;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.*;

public class ControllerMainWindow {


    @FXML
    private TextField TF1;

    @FXML
    private Button BAdd;

    @FXML
    private CheckBox CH1;

    @FXML
    private TextArea TextArea;

    @FXML
    private TableView<MP3> tV;

    @FXML
    private TableColumn id;

    @FXML
    private TableColumn author;

    @FXML
    private TableColumn songName;

    @FXML
    private TableColumn songLength;

    @FXML
    private TextField TF2;

    @FXML
    private Button FindSong;
    @FXML
    private TextField tfDelete;


    private static MySqlDAO mySqlDAO;

    @FXML
    public void initialize(){
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        mySqlDAO = (MySqlDAO) context.getBean("mySqlDAO");
    }

    @FXML
    public void deleteSong(){
        String id = tfDelete.getText();
        mySqlDAO.delete(id);
        showBase();
    }

@FXML
public void addSongFolder(){
    TextArea.clear();
    String pathFolder = TF1.getText();
    if (pathFolder.isEmpty()) {
        TextArea.setText("Введите путь!");
        TF1.clear();
    }
    else{
        MP3 mp3;
        String[] nameMP3;
            File file = new File(pathFolder);
            if (file.isFile() || file.isDirectory()){
            FilenameFilter fileFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    String nameLow = name.toLowerCase();
                    if (nameLow.endsWith(".mp3") && (nameLow.indexOf("-") != -1))
                        return true;
                    else
                        return false;
                }
            };

        nameMP3 = file.list(fileFilter);
        String duration;
        if (nameMP3 != null && nameMP3.length!=0) {
                for (String name2 : nameMP3) {
                    try {
                        String path = pathFolder + "\\" + name2;
                        duration = getDurationWithMp3Spi(path);
                        TextArea.appendText(name2 +  " (" + duration   + ")" + "\n");
                        String[] authorAndSong = name2.split("-");
                        mp3 = new MP3(authorAndSong[0].trim(), authorAndSong[1].trim(), duration);
                        mySqlDAO.insert(mp3);
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }}
                else {
                TextArea.setText("MP3 no found");
            }
    }
    else{
            TextArea.setText("Введен неправильный путь!");}
    }
}


@FXML
public void Find(){
    String findLine ="%" +  TF2.getText() + "%";
    List<MP3> list = mySqlDAO.getMP3ByTag(findLine);
    ObservableList<MP3> oblIST = FXCollections.observableList(list);
    id.setCellValueFactory(new PropertyValueFactory<MP3, String>("id"));
    author.setCellValueFactory(new PropertyValueFactory<MP3,String>("author"));
    songName.setCellValueFactory(new PropertyValueFactory<MP3,String>("songName"));
    songLength.setCellValueFactory(new PropertyValueFactory<MP3,String>("songLength"));
    tV.setItems(oblIST);
      }

@FXML
public void showBase () {
    List<MP3> allBase =  mySqlDAO.showAllBase();
    ObservableList<MP3> oblIST = FXCollections.observableList(allBase);

    id.setCellValueFactory(new PropertyValueFactory<MP3, String>("id"));
    author.setCellValueFactory(new PropertyValueFactory<MP3,String>("author"));
    songName.setCellValueFactory(new PropertyValueFactory<MP3,String>("songName"));
    songLength.setCellValueFactory(new PropertyValueFactory<MP3,String>("songLength"));

    tV.autosize();
    tV.setItems(oblIST);
    TextArea.appendText(allBase.toString());
    }




    private static String getDurationWithMp3Spi(String name) throws UnsupportedAudioFileException, IOException {
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