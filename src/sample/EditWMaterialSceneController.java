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


    //������ �� ��������� ��������� ����� �  �������������� ��� ����������
    private static  void restrictEdit(TextField textField){
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        textField.setMouseTransparent(true);

    }

//������������� ����� ����� ��� ����� ���� � �����������
    public void setEditWMStage(Stage editWMStage) {
        this.editWMStage = editWMStage;
        if (!User.isLoggedAdmin()) {
            EditWMaterialSceneController.restrictEdit(matField);
            EditWMaterialSceneController.restrictEdit(colorField);
            EditWMaterialSceneController.restrictEdit(amountField);
        }
    }
//����������� ��������� ����� �������� ������� ������
    public void setMaterial(Material material) {
        this.material = material;
        matField.setText(material.getMaterial());
        colorField.setText(material.getColor());
       amountField.setText(material.getAmount());
        extraField.setText(material.getExtra());
    }

//�����, ������������ true, ���� ������������ ������� OK, � ������ ������ false
    public boolean isOkClicked() {
        return okClicked;
    }

    public boolean isUnique(){
        return unique;
    }


    //�����, ������� ���������� true, ���� ������, ��������� � ��������� ����, ����������
    private boolean isInputValid() {
        String errorMessage = "";

        if (extraField.getText() == null || extraField.getText().length() == 0 ) {
            errorMessage += "��� ���������� �����������!\n";
        }
        if (amountField.getText() == null || amountField.getText().length() == 0 ) {
            errorMessage += "��� ���������� ����������!\n";
        }
        if (colorField.getText() == null || colorField.getText().length() == 0 ) {
            errorMessage += "��� ���������� �����!\n";
        }
        if (matField.getText() == null || matField.getText().length() == 0 ) {
            errorMessage += "��� ���������� ���������!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // ���������� ��������� �� ������.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editWMStage);
            alert.setTitle("������ ");
            alert.setHeaderText("����������, ������� ���������� ����������");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

//��� ����� � ���������� ������  ���� � �����
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
                    System.out.println("���� material.txt �� ������ ��� ������ ������ handleOk" + e);
                    e.printStackTrace();
                }
            }else { Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("��������");
                alert.setHeaderText("������ �������� ��� ������� �� ������");
                alert.setContentText("���� �� ��� ������ ��� ���������, �� ����� �������. ���� ���� ������� ���������� " +
                        "������ ���������, �� ��������� �� ���� ��������� ");
                alert.showAndWait();
                unique=false;
            }
            okClicked = true;
            editWMStage.close();
        }
    }
//�����, ����������� ����� �������������� ��������� ��� ���������
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
