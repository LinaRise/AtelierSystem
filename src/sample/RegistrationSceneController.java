package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import sample.AnimationAndDecor.AnimationAndDecor;
import sample.AnimationAndDecor.Shake;

import java.io.IOException;
import java.util.Optional;

public class RegistrationSceneController {
    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonRegistrate;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Hyperlink linkLogin;

    @FXML
    private AnchorPane regScene;



    //����� ��������� ����� �����������
    void registrationSceneCall(){
        Stage regStage = new Stage();
        regStage.setTitle("�����������");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/registrationScene.fxml"));
        } catch (IOException e) {
            System.out.println("���� registrationScene.fxml �� ������ " );
        }
        Scene scene = new Scene(root);
        AnimationAndDecor.addIcon(regStage, "sample/assets/needle.png");
        regStage.setScene(scene);
        regStage.setResizable(false);
        regStage.setOnCloseRequest(event -> {
           event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("�����");
            alert.setHeaderText("����� �� �� ���������� �������� �������� � ������� �� ����������");
            alert.setContentText("�� �������?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                regStage.close();
            }
        });
        scene.getStylesheets().add(0,"sample/styles/regScene.css");

        regStage.show();
    }




//����������� �������
    private void registrateButtonClick() {
        System.out.println("�� ������ ������ ������������������");
        User a = new User(loginField.getText(), passwordField.getText());
        if (!a.checkRegistartionLogin()) {    //���� ����� ����� ����
            loginField.clear();
            passwordField.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("������!");
            alert.setHeaderText("��������� �� ������");
            alert.setContentText("������ ����� ��� ����������.\n ����������, ������� �����");
            alert.showAndWait();

        } else {

            if (passwordField.getText().isEmpty() || loginField.getText().isEmpty()) {  //���� ������ ������ ������, ������� ��������� �� ������
                Shake regSceneAnim=new Shake(regScene);
                regSceneAnim.playAnim();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("������!");
                alert.setHeaderText("���� ������/������ �� ����� ���� ������!");
                alert.setContentText(" ����������, ������� �����/������");
                alert.showAndWait();

            } else {// ���� ��� �����, �� ������������ ������������ � ������� ��������� �� �������� �����������
                try {
                    a.registrate();
                } catch (IOException e) {
                    System.out.println("���� logPass.txt �� ������");
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("�����������");
                alert.setHeaderText("����������� ���������");
                alert.setContentText("������ ������� ���������.");
                alert.showAndWait();
                //��������� ������� ����� ����������� � ��������� ���� �����
                Stage regStage = (Stage) buttonRegistrate.getScene().getWindow();
                regStage.close();
                LoginSceneController loginSceneController=new LoginSceneController();
                loginSceneController.loginSceneCall();
            }
        }
    }




    @FXML
    void initialize() {
        // �������� ����� ��� ������� ������ ������ �� �������� ����������� ��������� ����
        buttonCancel.setOnAction(event -> {
            System.out.println("�� ������ ������ ������");
            loginField.clear();
            passwordField.clear();
        });

        loginField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                registrateButtonClick();
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                passwordField.requestFocus();
            }

        });


        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                registrateButtonClick();
            }
            if (event.getCode().equals(KeyCode.UP)) {
                loginField.requestFocus();
            }

        });

        /*��� ������� �� ������ �����������, ����������� ������������� ������
        ���� �� ��������, �� �������� ����� �����������
         */
        buttonRegistrate.setOnAction(event -> registrateButtonClick());
/*
��� ������� �� ������ ��������� �� �����, ��������� ����� ����� � ����������,
 � ����� ����������� ���������
 */
        linkLogin.setOnAction(event -> {
            Stage regStage = (Stage) linkLogin.getScene().getWindow();
            regStage.close();
            LoginSceneController loginSceneController=new LoginSceneController();
            loginSceneController.loginSceneCall();

        });
    }
}

