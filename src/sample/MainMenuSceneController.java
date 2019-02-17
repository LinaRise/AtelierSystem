package sample;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.AnimationAndDecor.AnimationAndDecor;

import java.io.File;
import java.io.IOException;

public class MainMenuSceneController extends Application {

    @FXML
    private ImageView materialsImg;

    @FXML
    private Button materialsButton;

    @FXML
    private ImageView newOrderImg;

    @FXML
    private Button newOrderButton;

    @FXML
    private ImageView ordersListImg;

    @FXML
    private Button ordersListButton;
    @FXML
    private Button helpBtn;





    //метод выводящий сцену Входа
    void mainMenuSceneCall(){
        Stage stage = new Stage();
        stage.setTitle("Основное меню");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/mainMenuScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            scene.getStylesheets().add(0,"sample/styles/mainMenu.css");
            AnimationAndDecor.addIcon(stage, "sample/assets/needle.png");
            stage.show();
            stage.setOnCloseRequest(event -> {
                LoginSceneController logScene=new LoginSceneController();
                logScene.loginSceneCall();
                stage.close();
            });
        } catch (IOException e) {
            System.out.println("Файл mainMenuScene.fxml не найден " );
            e.printStackTrace();
        }

        }







        @FXML
    void initialize(){
        newOrderButton.requestFocus();

        materialsButton.setOnAction(event -> {
            MaterialsSceneWorkerController matScene=new MaterialsSceneWorkerController();
            matScene.materialsWorkerSceneCall();
            materialsButton.getScene().getWindow().hide();


        });
            materialsImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                MaterialsSceneWorkerController matScene=new MaterialsSceneWorkerController();
                matScene.materialsWorkerSceneCall();
                materialsImg.getScene().getWindow().hide();
               event.consume();
            });


            newOrderButton.setOnAction(event -> {
                NewOrderSceneController newOrder=new NewOrderSceneController();
                newOrder.newOrderSceneCall();
                newOrderButton.getScene().getWindow().hide();

            });

            newOrderImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                NewOrderSceneController newOrder=new NewOrderSceneController();
                newOrder.newOrderSceneCall();
                newOrderImg.getScene().getWindow().hide();
                event.consume();
            });

            ordersListButton.setOnAction(event -> {
                OrdersCatSceneW orderScene=new OrdersCatSceneW();
                orderScene.orderCatSceneCall();
                ordersListButton.getScene().getWindow().hide();

            });

           ordersListImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
               OrdersCatSceneW orderScene=new OrdersCatSceneW();
               orderScene.orderCatSceneCall();
               ordersListButton.getScene().getWindow().hide();
               event.consume();

            });
           helpBtn.setOnAction(event -> {
               File file = new File("Instruction.docx");
               HostServices hostServices = getHostServices();
               hostServices.showDocument(file.getAbsolutePath());
           });
        }

    @Override
    public void start(Stage primaryStage) {

    }
}



