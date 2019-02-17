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



    //����� ���������� �� ����������� ����� �����
    void loginSceneCall(){
        Stage stage = new Stage();  //������� ����� ������ ������ Stage
        stage.setTitle("���� � �������"); //������ �������� ���� ������ ������
        try {
            //���������� ������ �� fxml ����
            Parent root = FXMLLoader.load(getClass().getResource("fxml/loginScene.fxml"));
            Scene scene = new Scene(root); //������ ����� ������ ������ Scene � ������� ��� ������ �� fxml ����
            stage.setScene(scene); //������������� ���  stage scene
            AnimationAndDecor.addIcon(stage,"sample/assets/needle.png");//��������� ������ ����������
            stage.setResizable(false);//��������� ��������� �������� �����
            scene.getStylesheets().add(0, "sample/styles/loginScene.css");//��������� css ����� � �����
            stage.show();//������� stage �� �����
            stage.setOnCloseRequest(event -> { //��� ������  �� ������ ��������
                event.consume();  //��������� �������
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //�������� ���� �������������
                alert.setTitle("�����");//������� ���������
                alert.setHeaderText("����� �� �� ������� �� ����������");//������� ����
                alert.setContentText("�� �������?"); //������� ��������� ������(�������) ����
                //����� ���� ������������� �� ����� � ������������ ���������� ������ ���������� result
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {//����  ���� ������ ��, �����
                    stage.close(); //�������� �����
                }
            });
        } catch (IOException e) {//���������� ������
            System.out.println("����  loginScene.fxml �� ������ " );//����� ��������� �� ������
            e.printStackTrace();
        }
    }





    /*���� � ����������, ������ ��������� ������
    ���� ��� ����� ������� ����� ��������� ����,
    ����� ������� ��������� �� ������
     */
    private boolean signingIn() {
        System.out.print("�� ������ ������ �����");
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
                //������� ����� ����� � ������� ����������� ��������
                MainMenuSceneController mainMenu=new MainMenuSceneController();
                mainMenu.mainMenuSceneCall();
                isMainSceneOpen=true;


            }else {
                Shake loginSceneAnim=new Shake(loginStage);
                loginSceneAnim.playAnim();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("������!");
                alert.setHeaderText("��������� ������ �� �������");
                alert.setContentText("����������, ��������� ��������� ����� � ������");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  isMainSceneOpen;
    }

/*
����� ������� ��������� ����� ����������� ��� ������� �� ������ "������������������"
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
        buttonCancel.setOnAction(event -> {  //������� ����� ������ � ������ ��� ������� �� ������ "������"
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

//����� ������ ��� ����������� � ����������
        linkRegistrate.setOnAction(event -> registrationIn());
        helpBtn.setOnAction(event -> { File file = new File("Instruction.docx");
            HostServices hostServices = getHostServices();
            hostServices.showDocument(file.getAbsolutePath());});
    }


    @Override
    public void start(Stage primaryStage) {

    }
}

