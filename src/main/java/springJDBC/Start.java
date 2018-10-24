package springJDBC;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Start extends Application {

    public Start() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        URL url = null;
        try{
            url = this.getClass().getClassLoader().getResource("Start.fxml");
            System.out.println(url);
            root =(Parent) FXMLLoader.load(url);
            }
            catch (Exception ex){
                System.out.println( "Exception on FXMLLoader.load()" );
                System.out.println( "  * url: " + url );
                System.out.println( "  * " + ex );
                System.out.println( "    ----------------------------------------\n" );
                throw ex;

        }
        primaryStage.setTitle("MP3 reader");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);

    }



}
