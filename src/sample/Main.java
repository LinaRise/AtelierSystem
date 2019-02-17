package sample;

import javafx.application.Application;
import javafx.stage.Stage;




public class Main extends Application {


    @Override
    public void start(Stage stage) {
       LoginSceneController logscene=new LoginSceneController();
       logscene.loginSceneCall();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
