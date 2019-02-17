package sample;

import javafx.application.Application;
import javafx.application.HostServices;
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

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class LoginSceneController extends Application {


    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonCancel;
    @FXML
    private Hyperlink linkRegistrate;

    @FXML
    private AnchorPane loginStage;

    @FXML
    private Button helpBtn;



    //метод отвечающий за отображение сцены Входа
    void loginSceneCall(){
        Stage stage = new Stage();  //создаем новый объект класса Stage
        stage.setTitle("Вход в систему"); //задаем навзание окна вверху строки
        try {
            //определяем ссылку на fxml файл
            Parent root = FXMLLoader.load(getClass().getResource("fxml/loginScene.fxml"));
            Scene scene = new Scene(root); //созаем новый объект класса Scene и предаем ему ссылку на fxml файл
            stage.setScene(scene); //устанваливаем для  stage scene
            AnimationAndDecor.addIcon(stage,"sample/assets/needle.png");//добавляем иконку приложения
            stage.setResizable(false);//запрещаем изменение размеров сцены
            scene.getStylesheets().add(0, "sample/styles/loginScene.css");//применяем css стиль к сцене
            stage.show();//выводим stage на экран
            stage.setOnCloseRequest(event -> { //при нажати  на кнопку закрытия
                event.consume();  //поглощает событие
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //создание окна подтверждения
                alert.setTitle("Выход");//задание загаловка
                alert.setHeaderText("Нажав ОК Вы выйдете из приложения");//задание окна
                alert.setContentText("Вы уверены?"); //задание основного текста(вопроса) окна
                //вызов окна подтверждения на экран и присваивание выбранного ответа переменной result
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {//если  было нажато ОК, тогда
                    stage.close(); //закрытие сцены
                }
            });
        } catch (IOException e) {//обрработка ошибки
            System.out.println("Файл  loginScene.fxml не найден " );//вывод сообщения об ошибке
            e.printStackTrace();
        }
    }





    /*вход в приложение, сверка введенных данных
    если все верно выводит сцену Основного меню,
    иначе выводит сообщение об ошибке
     */
    private boolean signingIn() {
        System.out.print("вы нажали кнопку войти");
        boolean isMainSceneOpen=false;
         User user = new User(loginField.getText(), passwordField.getText());
        try {
            if (user.checkData()&&user.isAdmin()) {
                user.writingLogin();
                AdminSceneController controller=new AdminSceneController();
                controller.adminSceneCall();
                Stage stage=(Stage) buttonLogin.getScene().getWindow();
                stage.close();


            }
           else if (user.checkData()&&!user.isAdmin()){
                user.writingLogin();
                Stage stage=(Stage) buttonLogin.getScene().getWindow();
                stage.close();
                //создаем новую сцену с выбором дальнейшего действия
                MainMenuSceneController mainMenu=new MainMenuSceneController();
                mainMenu.mainMenuSceneCall();
                isMainSceneOpen=true;


            }else {
                Shake loginSceneAnim=new Shake(loginStage);
                loginSceneAnim.playAnim();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Введенные данные не совпали");
                alert.setContentText("Пожалуйста, проверьте введенные логин и пароль");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  isMainSceneOpen;
    }

/*
метод который открывает сцену регистрации при нажатии на ссылку "Зарегистрироваться"
*/

    private void registrationIn() {
        Stage stage = (Stage) linkRegistrate.getScene().getWindow();
        stage.close();
        RegistrationSceneController regScene = new RegistrationSceneController();
        regScene.registrationSceneCall();
        linkRegistrate.getScene().getWindow().hide();


    }






    @FXML
    void initialize() {
        buttonCancel.setOnAction(event -> {  //очистка полей логина и пароля при нажатии на кнопку "Отмена"
            loginField.clear();
            passwordField.clear();
        });


        buttonLogin.setOnAction(event ->{
        if (signingIn()) {
            Stage loginStage = (Stage) buttonLogin.getScene().getWindow();
            loginStage.close();
        }
        });

        loginField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
               if( signingIn()){
                    Stage loginStage = (Stage) buttonLogin.getScene().getWindow();
                    loginStage.close();
                }
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                passwordField.requestFocus();
            }

        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                signingIn();
            }
            if (event.getCode().equals(KeyCode.UP)) {
                loginField.requestFocus();
            }

        });

//вызов метода для регистрации в приложении
        linkRegistrate.setOnAction(event -> registrationIn());
        helpBtn.setOnAction(event -> { File file = new File("Instruction.docx");
            HostServices hostServices = getHostServices();
            hostServices.showDocument(file.getAbsolutePath());});
    }


    @Override
    public void start(Stage primaryStage) {

    }
}

