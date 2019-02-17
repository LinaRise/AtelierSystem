package sample;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.AnimationAndDecor.AnimationAndDecor;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AdminSceneController extends Application {


    @FXML
    private Tab ordersTab;

    @FXML
    private Tab materialsTab;

    @FXML
    private TabPane tabPane;
    @FXML
    private MenuItem instructionBtn;
    @FXML
    private MenuItem aboutProgramBtn;



    //метод выводящий сцену Входа
    void adminSceneCall(){
        Stage stage = new Stage();
        stage.setTitle("Администратор");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/adminScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AnimationAndDecor.addIcon(stage,"sample/assets/needle.png");
            stage.setResizable(false);
            scene.getStylesheets().add(0, "sample/styles/adminScene.css");
            stage.setOnCloseRequest(event -> {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Выход из сцена для администртора");
                alert.setHeaderText("Нажав ОК Вы выйдете из приложения");
                alert.setContentText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();

                }
            });

            stage.show();
        } catch (IOException e) {
            System.out.println("Файл  loginScene.fxml не найден " );
            e.printStackTrace();
        }

    }




    //для кнопки в меню
    @FXML
    private void selectOrderTab() {
        tabPane.getSelectionModel().select(ordersTab);
    }
    //для кнопки в меню
    @FXML
    private void selectMaterialTab() {
        tabPane.getSelectionModel().select(materialsTab);

    }

//запонает tapPane сценой заказов
    private void fillOrdersTab(){
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/ordersCatSceneW.fxml"));
            ordersTab.setContent(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //запонает tapPane сценой материалов
    private void fillMaterilsTab(){
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/matScene2.fxml"));
            materialsTab.setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void  initialize() {
        fillOrdersTab();
        fillMaterilsTab();

        instructionBtn.setOnAction(event -> {
            File file = new File("Instruction.docx");
            HostServices hostServices = getHostServices();
            hostServices.showDocument(file.getAbsolutePath());
        });
aboutProgramBtn.setOnAction(event -> {
    Alert alert= new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("О программе");
    alert.setHeaderText("Программа \"Atelier\"\n" +
                          "Версия: 1.0.0\n"+
                 "Разработано в 2018 году\n" );

    alert.setContentText("По всем вопросам, касающихся приложения и предложений писать на почту: galeeva.alm@gmail.com ");
    alert.showAndWait();

});
    }


    @Override
    public void start(Stage primaryStage) {

    }
}
