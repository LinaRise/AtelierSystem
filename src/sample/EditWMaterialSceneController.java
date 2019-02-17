package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class EditWMaterialSceneController{
    @FXML
    private TextField matField;

    @FXML
    private TextField colorField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField extraField;

    @FXML
    private Button buttonOk;

    @FXML
    private Button buttonCancel;

    private Stage editWMStage;
    private Material material;
    private boolean okClicked=false;
    private boolean unique=false;


    //запрет на изменение некоторых полей в  редкатрованиии для работников
    private static  void restrictEdit(TextField textField){
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        textField.setMouseTransparent(true);

    }

//устанавливаем новую сцену для этого окна в контроллере
    public void setEditWMStage(Stage editWMStage) {
        this.editWMStage = editWMStage;
        if (!User.isLoggedAdmin()) {
            EditWMaterialSceneController.restrictEdit(matField);
            EditWMaterialSceneController.restrictEdit(colorField);
            EditWMaterialSceneController.restrictEdit(amountField);
        }
    }
//присваиваем тесктовым полям значения объекта класса
    public void setMaterial(Material material) {
        this.material = material;
        matField.setText(material.getMaterial());
        colorField.setText(material.getColor());
       amountField.setText(material.getAmount());
        extraField.setText(material.getExtra());
    }

//метод, возвращающий true, если пользователь кликнул OK, в другом случае false
    public boolean isOkClicked() {
        return okClicked;
    }

    public boolean isUnique(){
        return unique;
    }


    //метод, который возвращает true, если данные, введенные в текстовые поля, корректные
    private boolean isInputValid() {
        String errorMessage = "";

        if (extraField.getText() == null || extraField.getText().length() == 0 ) {
            errorMessage += "Нет доступного комментария!\n";
        }
        if (amountField.getText() == null || amountField.getText().length() == 0 ) {
            errorMessage += "Нет доступного количества!\n";
        }
        if (colorField.getText() == null || colorField.getText().length() == 0 ) {
            errorMessage += "Нет доступного цвета!\n";
        }
        if (matField.getText() == null || matField.getText().length() == 0 ) {
            errorMessage += "Нет доступного материала!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editWMStage);
            alert.setTitle("Ошибка ");
            alert.setHeaderText("Пожалуйста, внесите корректную информацию");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

//для ввода в количество только  цифр и точки
    public void amountTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter=6;
        String key = keyEvent.getCharacter();
        if((!key.isEmpty() && (!Character.isDigit(key.charAt(0)) && !key.equals(".")))){
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.beep();
            keyEvent.consume();
        }
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            material.setMaterial(matField.getText().trim());
            material.setColor(colorField.getText().trim());
            material.setAmount(amountField.getText().trim());
            material.setExtra(extraField.getText().trim());
            if (Material.isUniqueMaterial(material)) {
                try {
                    Material.addMaterial(material);
                } catch (IOException e) {
                    System.out.println("Файл material.txt не найден при вызове метода handleOk" + e);
                    e.printStackTrace();
                }
            }else { Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("Данный материал уже имеется на складе");
                alert.setContentText("Если он был выбран для изменения, он будет изменен. Если было выбрано добавление " +
                        "нового материала, то изменения не были добавлены ");
                alert.showAndWait();
                unique=false;
            }
            okClicked = true;
            editWMStage.close();
        }
    }
//метод, закрывающий сцену редактирования материала для работника
    @FXML
    private void handleCancel() {
        editWMStage.close();
    }



    @FXML
    void initialize() {
        buttonOk.setOnAction(event -> handleOk());
        buttonCancel.setOnAction(event -> handleCancel());

        matField.setOnKeyPressed(event ->{
            if (event.getCode().equals(KeyCode.DOWN)) {
                colorField.requestFocus();
            }
        });

        colorField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                amountField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.UP)){
                matField.requestFocus();
            }
        });

        amountField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                extraField.requestFocus();
            }
            if (event.getCode().equals(KeyCode.UP)){
               colorField.requestFocus();
            }
        });

        extraField.setOnKeyPressed(event -> {

            if (event.getCode().equals(KeyCode.UP)){
                amountField.requestFocus();
            }
        });

    }
}
