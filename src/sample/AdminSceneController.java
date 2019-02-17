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



    //����� ��������� ����� �����
    void adminSceneCall(){
        Stage stage = new Stage();
        stage.setTitle("�������������");
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
                alert.setTitle("����� �� ����� ��� �������������");
                alert.setHeaderText("����� �� �� ������� �� ����������");
                alert.setContentText("�� �������?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();

                }
            });

            stage.show();
        } catch (IOException e) {
            System.out.println("����  loginScene.fxml �� ������ " );
            e.printStackTrace();
        }

    }




    //��� ������ � ����
    @FXML
    private void selectOrderTab() {
        tabPane.getSelectionModel().select(ordersTab);
    }
    //��� ������ � ����
    @FXML
    private void selectMaterialTab() {
        tabPane.getSelectionModel().select(materialsTab);

    }

//�������� tapPane ������ �������
    private void fillOrdersTab(){
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/ordersCatSceneW.fxml"));
            ordersTab.setContent(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //�������� tapPane ������ ����������
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
    alert.setTitle("� ���������");
    alert.setHeaderText("��������� \"Atelier\"\n" +
                          "������: 1.0.0\n"+
                 "����������� � 2018 ����\n" );

    alert.setContentText("�� ���� ��������, ���������� ���������� � ����������� ������ �� �����: galeeva.alm@gmail.com ");
    alert.showAndWait();

});
    }


    @Override
    public void start(Stage primaryStage) {

    }
}
