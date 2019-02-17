package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.AnimationAndDecor.AnimationAndDecor;
import sample.AnimationAndDecor.Shake;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class NewOrderSceneController {
    private final ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private TextField fioField;

    @FXML
    private TextArea orderField;

    @FXML
    private TextField measuresField;
    @FXML
    private ComboBox stateBox;


    @FXML
    private TextField costField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField telephoneField;

    @FXML
    private Button materialsBtn;

    @FXML
    private Button orderCatBtn;

    @FXML
    private Button menuBtn;

    @FXML
    private Hyperlink pickMaterials;
    @FXML
   private AnchorPane newOrderScene;





    //метод выводящий сцену создания нового заказа
    void newOrderSceneCall(){
        Stage stage = new Stage();
        stage.setTitle("");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/newOrderScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            scene.getStylesheets().add(0, "sample/styles/newOrderScene.css");
            AnimationAndDecor.addIcon(stage, "sample/assets/needle.png");
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Выход");
                alert.setHeaderText("Нажав ОК Вы прекратите создание заказа");
                alert.setContentText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();
                    MainMenuSceneController menuScene=new MainMenuSceneController();
                    menuScene.mainMenuSceneCall();

                }
            });
        } catch (IOException e) {
            System.out.println("Файл mainMenuScene.fxml не найден " );
            e.printStackTrace();
        }

        }






    private boolean ifInputIsValid(){


        String errorMessage = "";

        if (fioField.getText() == null || fioField.getText().length() == 0) {
            errorMessage += "Нет доступного имени заказчика!\n";
        }
        if (telephoneField.getText() == null || telephoneField.getText().length() == 0){
            errorMessage += "Нет доступного телефона заказчика!\n";
        }
        if (orderField.getText() == null || orderField.getText().length() == 0) {
            errorMessage += "Нет достпного описания заказа!\n";
        }
         if (!Material.isFileExists()){
             errorMessage += "Не выбраны материалы для заказа!\n";
         }

        if (measuresField.getText() == null || measuresField.getText().length() == 0) {
            errorMessage += "Не введены необходиме измерения!\n";
        }
        if (stateBox.getValue().toString() == null || stateBox.getValue().toString().length() == 0) {
            errorMessage += "Не введен  статус заказа!\n";
        }
        if (costField.getText() == null || measuresField.getText().length() == 0) {
            errorMessage += "Не введена сумма заказа!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Shake loginSceneAnim=new Shake(newOrderScene);
            loginSceneAnim.playAnim();
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Пожалуйста, внесите корректную информацию");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    //метод создания новго заказа
    private void saveButtonClick() {

        if (ifInputIsValid()) {
           Material material=new Material();
           User user=new User();
           String loginOfWorker=user.gettingLoggedLogin();
           String matForOrder=material.getMatForNewOrder();
           System.out.println("материалы для заказа"+matForOrder);
           Order newOrder = null;
           Client client=new Client();
            int clientId = 0;

            try {
                if (Client.fileContainsWord(fioField.getText())){
                    clientId= Integer.parseInt(Client.getClientId(fioField.getText()));
                    System.out.println("id "+clientId);
                }
                else{
                    clientId=Client.clientId();
                    client.writingNewClient(String.valueOf(clientId),fioField.getText());
                    System.out.println("id "+clientId);

                }

                System.out.println( Order.orderNum());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                newOrder = new Order(Order.orderNum(),clientId, telephoneField.getText(), orderField.getText(),
                        measuresField.getText(), stateBox.getValue().toString(), costField.getText(),loginOfWorker,matForOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert newOrder != null;
            newOrder.addNewOrder();
            clearFields();
            matForOrder=matForOrder.replace("\\","");
            System.out.println("без скобок "+matForOrder);
            String[] arrSplit=matForOrder.split(Pattern.quote(" "));
            ArrayList<String> list=material.getMaterialsFile();
            ArrayList<String> listMaterail=newOrder.saveNewOrder(list, arrSplit);

            try {
                Material.rewriteMaterialCatalog(listMaterail);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }






    //метод для ввода в  поле телефона  только цифр и звуковой сигнал при наборе неразрешенных
    public void phoneTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter=11;
        String key = keyEvent.getCharacter();
        if((!key.isEmpty() && !Character.isDigit(key.charAt(0))|| (telephoneField.getText().length()>=maxCharacter) )){
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.beep();
            keyEvent.consume();
        }
    }

    //метод для ввода в поле фио только букв и звуковой сигнал при наборе неразрешенного
    public void fioTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter=100;
        String key = keyEvent.getCharacter();

            if ((!key.isEmpty() && Character.isDigit(key.charAt(0)) || (fioField.getText().length() >= maxCharacter))) {
                Toolkit tk = Toolkit.getDefaultToolkit();
                tk.beep();
                keyEvent.consume();
            }
        }


    //метод для ввода в поле суммы только цифр и звуковой сигнал при наборе неразрешенного
    public void costTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter=15;// максимальное число символов для ввода
        String key = keyEvent.getCharacter();//получаем символ введенный с клавиатуры
        if ((!key.isEmpty() && (!Character.isDigit(key.charAt(0)) && !key.equals(".")) ||
                //условие:если введенный символ не является ни пустотой,
                (costField.getText().length() >= maxCharacter))) {
            //ни точкой, ни числом и длина вводимых значений превышает максимум, тогда
                Toolkit tk = Toolkit.getDefaultToolkit(); //вызываем объект класса Toolkit
                tk.beep();  //даем звуковой сигнал
                keyEvent.consume(); //поглощаем символ,т.е оно не вбивается в поле стоимости
        }
    }





    private void clearFields(){
        fioField.clear();
        telephoneField.clear();
        orderField.clear();
        measuresField.clear();
        stateBox.setValue("");
        costField.clear();
    }




    @FXML
    void initialize(){
        File file = new File("matForOrder.txt");
        if(file.delete()){
            System.out.println("matForOrder.txt удален");
        }else System.out.println("matForOrder.tx не существует в корневой директории");


        fioField.setOnKeyPressed(event ->{
            if (event.getCode().equals(KeyCode.DOWN)) {
            telephoneField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                measuresField.requestFocus();
            }
                });
        telephoneField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                orderField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.UP)){
                fioField.requestFocus();
            }

        });

        orderField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                measuresField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.UP)){
                telephoneField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                costField.requestFocus();
            }
        });

        measuresField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                orderField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                costField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                fioField.requestFocus();
            }
        });


        costField.setOnKeyPressed(event -> { if (event.getCode().equals(KeyCode.UP)){
               measuresField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
               orderField.requestFocus();
            }
        });



        saveButton.setOnAction(event -> saveButtonClick());

        cancelButton.setOnAction(event -> clearFields());


        list.add("Оплачено");
        list.add("Выполнено");
        list.add("Выдано клиенту");
        stateBox.setItems(list);
        stateBox.setValue("");


        menuBtn.setOnAction(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Выход");
            alert.setHeaderText("Нажав ОК Вы прекратите создание заказа");
            alert.setContentText("Вы уверены?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                MainMenuSceneController menuScene = new MainMenuSceneController();
                menuScene.mainMenuSceneCall();
                menuBtn.getScene().getWindow().hide();

            }
        });


        orderCatBtn.setOnAction(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Выход");
            alert.setHeaderText("Нажав ОК Вы прекратите создание заказа");
            alert.setContentText("Вы уверены?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                OrdersCatSceneW oredrsScene=new OrdersCatSceneW();
                oredrsScene.orderCatSceneCall();
                menuBtn.getScene().getWindow().hide();

            }
        });





        materialsBtn.setOnAction(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Выход");
            alert.setHeaderText("Нажав ОК Вы прекратите создание заказа");
            alert.setContentText("Вы уверены?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                MaterialsSceneWorkerController matScene=new MaterialsSceneWorkerController();
                matScene.materialsWorkerSceneCall();
                menuBtn.getScene().getWindow().hide();

            }
        });

        pickMaterials.setOnAction(event -> {
            PickMaterialsController pickScene=new PickMaterialsController();
            pickScene.pickSceneCall();
        });


    }





}
