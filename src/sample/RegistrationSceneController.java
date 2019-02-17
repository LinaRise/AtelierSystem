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



    //метод выводящий сцену Регистрации
    void registrationSceneCall(){
        Stage regStage = new Stage();
        regStage.setTitle("Регистрация");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/registrationScene.fxml"));
        } catch (IOException e) {
            System.out.println("Файл registrationScene.fxml не найден " );
        }
        Scene scene = new Scene(root);
        AnimationAndDecor.addIcon(regStage, "sample/assets/needle.png");
        regStage.setScene(scene);
        regStage.setResizable(false);
        regStage.setOnCloseRequest(event -> {
           event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Выход");
            alert.setHeaderText("Нажав ОК Вы прекратите создание аккаунта и выйдете из приложения");
            alert.setContentText("Вы уверены?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                regStage.close();
            }
        });
        scene.getStylesheets().add(0,"sample/styles/regScene.css");

        regStage.show();
    }




//регистрация клиента
    private void registrateButtonClick() {
        System.out.println("вы нажали кнопку зарегестрироваться");
        User a = new User(loginField.getText(), passwordField.getText());
        if (!a.checkRegistartionLogin()) {    //если такой логин есть
            loginField.clear();
            passwordField.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Сообщение об ошибке");
            alert.setContentText("Данный логин уже существует.\n Пожалуйста, введите новый");
            alert.showAndWait();

        } else {

            if (passwordField.getText().isEmpty() || loginField.getText().isEmpty()) {  //если строка пароля пустая, выводим сообщение об ошибке
                Shake regSceneAnim=new Shake(regScene);
                regSceneAnim.playAnim();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Поле логина/пароля не может быть пустым!");
                alert.setContentText(" Пожалуйста, введите логин/пароль");
                alert.showAndWait();

            } else {// если все верно, то регистрируем пользователя и выводим сообщение об успешной регистрации
                try {
                    a.registrate();
                } catch (IOException e) {
                    System.out.println("Файл logPass.txt не найден");
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Уведомление");
                alert.setHeaderText("Регистрация завершена");
                alert.setContentText("Данные успешно сохранены.");
                alert.showAndWait();
                //закрываем текущую сцену регистрации и открываеи окно Входа
                Stage regStage = (Stage) buttonRegistrate.getScene().getWindow();
                regStage.close();
                LoginSceneController loginSceneController=new LoginSceneController();
                loginSceneController.loginSceneCall();
            }
        }
    }




    @FXML
    void initialize() {
        // отчистка полей при нажатии кнопка отмена на странице регистрации очищаются поля
        buttonCancel.setOnAction(event -> {
            System.out.println("вы нажали кнопку отмена");
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

        /*при нажатии на кнопку регистрации, проверяется корретктность логина
        если он уникален, то вызываем метод регистрации
         */
        buttonRegistrate.setOnAction(event -> registrateButtonClick());
/*
при нажатии на кноаку Вернуться ко Входу, откроется сцена Входа в приложение,
 а сцена регистрации закроется
 */
        linkLogin.setOnAction(event -> {
            Stage regStage = (Stage) linkLogin.getScene().getWindow();
            regStage.close();
            LoginSceneController loginSceneController=new LoginSceneController();
            loginSceneController.loginSceneCall();

        });
    }
}

